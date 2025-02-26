package com.akd.service;

import com.akd.common.Constants;
import com.akd.common.result.ResultBody;
import com.akd.common.result.err.ErrorInfoEnum;
import com.akd.entity.CinemaInfo;
import com.akd.entity.FlmData;
import com.akd.entity.FlmInfo;
import com.akd.entity.FlmParsingAuditorium;
import com.akd.entity.flmXml.*;
import com.akd.mapper.FlmDataMapper;
import com.akd.mapper.FlmInfoMapper;
import com.akd.mapper.FlmParsingAuditoriumMapper;
import com.akd.utils.MD5Utils;
import com.akd.utils.XMLUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class FLMTMSService {

    @Value("${FLM_REDIS_EXPIRE}")
    private int FLM_REDIS_EXPIRE;

    @Autowired
    protected SimpleRedisService simpleRedisService;

    @Autowired
    protected TokenService tokenService;

    @Autowired
    protected FlmInfoMapper flmInfoMapper;

    @Autowired
    protected FlmDataMapper flmDataMapper;

    @Autowired
    protected FlmParsingAuditoriumMapper flmParsingAuditoriumMapper;

    public ResultBody pushInformation(String token, String flm, String type){


        CinemaInfo cinemaInfo = tokenService.getCinemaInfoByToken(token);
        if (cinemaInfo == null){
            return new ResultBody(ErrorInfoEnum.TOKEN_INVALID);
        }

        Map<String, Object> resMap = validateFlm(flm, cinemaInfo);
        List<String> errorList = (List<String>)resMap.get("errorList");
        if(!CollectionUtils.isEmpty(errorList)){
            return new ResultBody(ErrorInfoEnum.FLM_VERIFY_NOT_PASS, errorList);
        }


        Runnable runnable = () ->{
            List<FlmParsingAuditorium> deviceList = (List<FlmParsingAuditorium>)resMap.get("deviceList");
            long flmDataId = saveFlmData(cinemaInfo.getId(), flm);
            if(flmDataId <= 0 ){
                log.info("影院:{},专资编码:{},保存Flm_Data失败", cinemaInfo.getCinemaName(), cinemaInfo.getCinemaCode());
                return;
            }

            long flmInfoId = saveFlmInfo(cinemaInfo, flm, type, flmDataId);
            if(flmInfoId <= 0 ){
                log.info("影院:{},专资编码:{},flmDataId:{},保存Flm_Info失败", cinemaInfo.getCinemaName(), cinemaInfo.getCinemaCode(), flmDataId);
                return;
            }
            simpleRedisService.delString(Constants.getRedisFlmKey(token));

            saveDeviceList(deviceList, cinemaInfo.getId(), flmInfoId);
        };

        Thread thread = new Thread(runnable);
        thread.start();


        return new ResultBody(ErrorInfoEnum.SUCCESS);
    }

    private void saveDeviceList(List<FlmParsingAuditorium> deviceList, long cinemaInfoId, long flmInfoId) {
        if(!CollectionUtils.isEmpty(deviceList)){
            FlmParsingAuditorium flmParsingAuditoriumSearch = new FlmParsingAuditorium();
            String messageId = getDbLatestMessageIdByCinemaInfoId(cinemaInfoId);
            if(StringUtils.isEmpty(messageId)){
                for(FlmParsingAuditorium flmParsingAuditorium : deviceList){
                    flmParsingAuditorium.setFlmInfoId(flmInfoId);
                    flmParsingAuditorium.setPostType("add");
                    flmParsingAuditoriumMapper.insertFlmParsingAuditorium(flmParsingAuditorium);
                }
            }else {
                flmParsingAuditoriumSearch.setMessageId(messageId);
                List<FlmParsingAuditorium> flmParsingAuditoriums = flmParsingAuditoriumMapper.selectFlmParsingAuditoriumList(flmParsingAuditoriumSearch);

                //新增或修改
                for(FlmParsingAuditorium flmParsingAuditorium : deviceList){
                    flmParsingAuditorium.setFlmInfoId(flmInfoId);
                    boolean exist = false;
                    for(FlmParsingAuditorium flmParsingAuditoriumDb : flmParsingAuditoriums) {
                        if(flmParsingAuditorium.getAuditoriumNumber().equals(flmParsingAuditoriumDb.getAuditoriumNumber())){
                            exist = true;
                            break;
                        }
                    }

                    if(!exist){
                        flmParsingAuditorium.setPostType("add");
                    }else {
                        flmParsingAuditorium.setPostType("update");
                    }
                    flmParsingAuditoriumMapper.insertFlmParsingAuditorium(flmParsingAuditorium);
                }

                //删除
                for(FlmParsingAuditorium flmParsingAuditoriumDb : flmParsingAuditoriums){
                    boolean exist = false;
                    for(FlmParsingAuditorium flmParsingAuditorium : deviceList) {
                        if(flmParsingAuditorium.getAuditoriumNumber().equals(flmParsingAuditoriumDb.getAuditoriumNumber())){
                            exist = true;
                            break;
                        }
                    }

                    if(!exist){
                        flmParsingAuditoriumDb.setPostType("delete");
                        flmParsingAuditoriumMapper.updateFlmParsingAuditorium(flmParsingAuditoriumDb);
                    }
                }
            }

        }
    }

    private String getDbLatestMessageIdByCinemaInfoId(long cinemaInfoId) {
        FlmParsingAuditorium flmParsingAuditorium =  flmParsingAuditoriumMapper.getDbLatestMessageIdByCinemaInfoId(cinemaInfoId);
        if(flmParsingAuditorium == null){
            return null;
        }
        return flmParsingAuditorium.getMessageId();
    }

    private String getMessageId(String flm) {
        try {
            FlmXmlRoot flmXml = (FlmXmlRoot) XMLUtil.convertXmlStrToObject(FlmXmlRoot.class, flm);
            if(flmXml != null && !StringUtils.isEmpty(flmXml.getMessageId())){
                return  flmXml.getMessageId();
            }
        }catch (Exception e){
            log.error("getMessageId exception:", e);
        }
        return null;
    }

    private Map<String, java.lang.Object> validateFlm(String flm, CinemaInfo cinemaInfo) {
        List<String> list = new ArrayList<>();
        List<String> resList = new ArrayList<>();
        List<FlmParsingAuditorium> flmParsingAuditoriums = new ArrayList<>();
        Map<String, java.lang.Object> resMap = new HashMap<>();
        resMap.put("errorList", resList);
        resMap.put("deviceList", flmParsingAuditoriums);
        try {
            FlmXmlRoot flmXml = (FlmXmlRoot)XMLUtil.convertXmlStrToObject(FlmXmlRoot.class, flm);
            String messageId = flmXml.getMessageId();
            FlmInfo flmInfo = flmInfoMapper.selectByMessageId(messageId);
            if(flmInfo != null){
                resList.add("消息:" + messageId + "已经存在");
                return resMap;
            }
            valdateStr(list, flmXml.getMessageId(),"MessageId");
            valdateStr(list, flmXml.getAnnotationText(),"AnnotationText");
            valdateStr(list, flmXml.getIssueDate(),"IssueDate");


            String cinemaName = cinemaInfo.getCinemaName();
            if(valdateObject(list, flmXml.getFacilityInfo(),"FacilityInfo")){
                valdateStr(list, flmXml.getFacilityInfo().getFacilityID(),"FacilityID");
                valdateStr(list, flmXml.getFacilityInfo().getFacilityName(),"FacilityName");
                cinemaName = flmXml.getFacilityInfo().getFacilityName();
//                valdateStr(list, flmXml.getFacilityInfo().getCircuit(),"Circuit");
                valdateStr(list, flmXml.getFacilityInfo().getFLMPartial(),"FLMPartial");
                if(valdateObject(list, flmXml.getFacilityInfo().getAddressList(),"AddressList")){
                    if(valdateList(list, flmXml.getFacilityInfo().getAddressList().getAddress(), "AddressList.Address")){
                        for(Address address : flmXml.getFacilityInfo().getAddressList().getAddress()){
                            valdateStr(list, address.getAddressee(),"Addressee");
                            valdateStr(list, address.getStreetAddress(),"StreetAddress");
                            valdateStr(list, address.getCity(),"City");
                            valdateStr(list, address.getProvince(),"Province");
                            valdateStr(list, address.getCountry(),"Country");
                        }

                    }
                }
                if(valdateObject(list, flmXml.getFacilityInfo().getContactList(),"ContactList")){
                    if(valdateList(list, flmXml.getFacilityInfo().getContactList().getContact(), "ContactList.Contact")){
                        for(Contact contact : flmXml.getFacilityInfo().getContactList().getContact()){
                            valdateStr(list, contact.getName(),"Name");
                        }
                    }
                }
            }

            if(valdateObject(list, flmXml.getAuditoriumList(),"AuditoriumList")){
                if(valdateList(list, flmXml.getAuditoriumList().getAuditorium(),"Auditorium")){
                    for(Auditorium auditorium : flmXml.getAuditoriumList().getAuditorium()){
                        String auditoriumNumber = auditorium.getAuditoriumNumber();
                        String auditoriumName = auditorium.getAuditoriumName();
                        valdateStr(list, auditorium.getAuditoriumNumber(), "AuditoriumNumber");
                        valdateStr(list, auditorium.getAuditoriumName(), "AuditoriumName");
                        valdateObject(list, auditorium.getDeviceGroupList(), "DeviceGroupList");
                        if(auditorium.getDigital3DSystem() != null){
                            valdateStr(list, auditorium.getDigital3DSystem().getIsActive(),"Digital3DSystem.IsActive");
                        }
                        if(valdateList(list, auditorium.getDeviceGroupList().getDeviceGroup(), "DeviceGroup")){
                            for(DeviceGroup deviceGroup : auditorium.getDeviceGroupList().getDeviceGroup()){
                                if(valdateList(list, deviceGroup.getDevice(), "Device")){
                                    for(Device device : deviceGroup.getDevice()){
                                        String deviceTypeID = device.getDeviceTypeID();
                                        String deviceIdentifier = device.getDeviceIdentifier();
                                        String manufacturerName = device.getManufacturerName();
                                        String modelNumber = device.getModelNumber();
                                        String isActive = device.getIsActive();
                                        if("PLY".equals(deviceTypeID)) {
                                            FlmParsingAuditorium flmParsingAuditorium = new FlmParsingAuditorium();
                                            flmParsingAuditorium.setCinemaInfoId(cinemaInfo.getId());
                                            flmParsingAuditorium.setAuditoriumNumber(auditoriumNumber);
                                            flmParsingAuditorium.setAuditoriumName(auditoriumName);
                                            flmParsingAuditorium.setCinemaInfoId(cinemaInfo.getId());
                                            flmParsingAuditorium.setCinemaName(cinemaName);
                                            flmParsingAuditorium.setDeviceIdentifier(deviceIdentifier);
                                            flmParsingAuditorium.setManufacturerName(manufacturerName);
                                            flmParsingAuditorium.setModelNumber(modelNumber);
                                            flmParsingAuditorium.setIsActive("true".equals(isActive) ? "可用" : "不可用");
                                            flmParsingAuditorium.setMessageId(messageId);
                                            flmParsingAuditorium.setCreateTime(new Date());
                                            flmParsingAuditorium.setUpdateTime(new Date());
                                            flmParsingAuditoriums.add(flmParsingAuditorium);
                                        }
                                        valdateStr(list, deviceTypeID,"DeviceTypeID");
                                        valdateStr(list, deviceIdentifier,"DeviceIdentifier");
                                        valdateStr(list, manufacturerName,"ManufacturerName");
                                        valdateStr(list, modelNumber,"ModelNumber");
                                        valdateStr(list, isActive,"Device.IsActive");

                                        if(device.getIPAddressList() != null && !CollectionUtils.isEmpty(device.getIPAddressList().getIPAddress())){
                                            for(IPAddress ipAddress : device.getIPAddressList().getIPAddress()){
                                                valdateStr(list, ipAddress.getAddress(),"IPAddress.Address");
                                            }
                                        }
                                        if(device.getKeyInfoList() != null && !CollectionUtils.isEmpty(device.getKeyInfoList().getKeyInfo())){
                                            for(KeyInfo keyInfo : device.getKeyInfoList().getKeyInfo()){
                                                if(keyInfo.getX509Data() != null){
                                                    valdateStr(list, keyInfo.getX509Data().getX509Certificate(),"X509Certificate");
                                                    valdateStr(list, keyInfo.getX509Data().getX509SubjectName(),"X509SubjectName");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }





            for(String str : list){
                resList.add("字段:" + str + "不能为空");
            }

        }catch (Exception e){
            log.error("validateFlm exception:", e);
            resList.add("请检查FLM XML文件格式");
        }

        return resMap;
    }

    public long saveFlmData(long cinemaInfoId, String flm){
        FlmData flmData = new FlmData();
        flmData.setCinemaInfoId(cinemaInfoId);
        flmData.setFlm(flm);
        flmDataMapper.insertFlmData(flmData);
        return flmData.getId();
    }

    public long saveFlmInfo(CinemaInfo cinemaInfo, String flm, String type, long flmDataId){
        FlmInfo flmInfo = new FlmInfo();
        flmInfo.setCreateTime(new Date());
        flmInfo.setUpdateTime(new Date());
//        flmInfo.setFlmMd5(MD5Utils.md5(flm));
        flmInfo.setFlmMd5("--");
        flmInfo.setMessageId(getMessageId(flm));
        flmInfo.setType(type);
        flmInfo.setCinemaInfoId(cinemaInfo.getId());
        flmInfo.setFlmDataId(flmDataId);
        flmInfo.setIsReport(FlmInfo.UNREPORT);
        flmInfoMapper.insert(flmInfo);

        return flmInfo.getId();
    }

    private boolean valdateObject(List<String> list, java.lang.Object value, String name) {
        if(value == null){
            list.add(name);
            return false;
        }
        return true;
    }

    private boolean valdateStr(List<String> list, String value, String name) {
        if(StringUtils.isEmpty(value)){
            list.add(name);
            return false;
        }
        return true;
    }

    private boolean valdateList(List<String> list, List value, String name) {
        if(CollectionUtils.isEmpty(value)){
            list.add(name);
            return false;
        }
        return true;
    }

    public FlmData getInformation(String token, Long cinemaInfoId){
        String flm = simpleRedisService.getString(Constants.getRedisFlmKey(token));
        if(StringUtils.isEmpty(flm)){
            FlmInfo flmInfo = flmInfoMapper.selectByCinemaInfoId(cinemaInfoId);
            if(flmInfo != null){
                FlmData flmData = flmDataMapper.selectFlmDataById(flmInfo.getFlmDataId().intValue());
                if(flmData != null){
                    simpleRedisService.setStringExpire(Constants.getRedisFlmKey(token),JSON.toJSONString(flmData),FLM_REDIS_EXPIRE);
                    return flmData;
                }
            }
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(flm);
        FlmData redisFlmdata = JSONObject.toJavaObject(jsonObject, FlmData.class);
        return  redisFlmdata;
    }
}
