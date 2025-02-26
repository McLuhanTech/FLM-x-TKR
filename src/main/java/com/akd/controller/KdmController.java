package com.akd.controller;

import com.akd.common.Constants;
import com.akd.entity.CinemaInfo;
import com.akd.service.KdmDownloadService;
import com.akd.service.SimpleRedisService;
import com.akd.common.result.ResultBody;
import com.akd.common.result.err.ErrorInfoEnum;
import com.akd.controller.base.BaseController;
import com.akd.entity.KdmInfo;
import com.akd.entity.request.KdmQuerRequest;
import com.akd.entity.response.KdmQuerRsponse;
import com.akd.service.KDMInfoService;
import com.akd.service.TokenService;
import com.akd.utils.FileUtil;
import com.akd.utils.NetworkUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;


@Api(tags = "密钥相关接口")
@Slf4j
@RestController
public class KdmController extends BaseController {

    @Autowired
    private KdmDownloadService kdmDownloadService;

    @Autowired
    private KDMInfoService kdmInfoService;

    @Autowired
    private TokenService tokenService;
    
    @Value("${KDM_VISITS_KEY_REDIS_FREQUENCY}")
    private int kdmVisitKeyRedisFrequency;

    @Value("${KDM_VISITS_FREQUENCY}")
    private int kdmVisitsFrequency;

    @Autowired
    protected SimpleRedisService simpleRedisService;

    @ApiOperation(value = "密钥查询接口")
    @PostMapping(value = "/v1/kdm/query")
    public ResultBody query(@ModelAttribute KdmQuerRequest kdmQuerRequest){
        String redisKeyIp = null;
        try {
            redisKeyIp = Constants.getRedisKeyIp(NetworkUtils.getIpAddress(getRequest()));
        } catch (Exception e) {
            log.info("获取本机IP地址:{},异常",redisKeyIp, e);
        }
        String str = null;
        if(!simpleRedisService.hasKey(redisKeyIp)){
            simpleRedisService.setStringExpire(redisKeyIp, "1", kdmVisitKeyRedisFrequency);
        }else{
            str = simpleRedisService.getString(redisKeyIp);
            simpleRedisService.setStringExpire(redisKeyIp, String.valueOf(Integer.parseInt(str) + 1), kdmVisitKeyRedisFrequency);
        }

        if(!StringUtils.isEmpty(str) && Integer.parseInt(str) > kdmVisitsFrequency){
            return new ResultBody(ErrorInfoEnum.REQUEST_TOO_MANY);
        }

        log.info("KdmController.query req {}", JSONObject.toJSONString(kdmQuerRequest));
        String uuid = kdmQuerRequest.getUuid();
        String certificate = kdmQuerRequest.getCertificate();
        String token = kdmQuerRequest.getToken();
        if(StringUtils.isEmpty(uuid) || StringUtils.isEmpty(certificate) || StringUtils.isEmpty(token)){
            return  new ResultBody(ErrorInfoEnum.PARAM_ERR);

        }
        if(!tokenService.verifyTokenAndStatus(token)){
            return new ResultBody(ErrorInfoEnum.TOKEN_INVALID);
        }
        KdmQuerRsponse kdmQuerRsponse = new KdmQuerRsponse();
        String redisKdmKey = Constants.getRedisKeyQuery(uuid, certificate);
        String redisKdmValue = null;
        try {
            redisKdmValue = simpleRedisService.getString(redisKdmKey);
        } catch (Exception e) {
            log.error("KdmController.redisUtil.get key {},error",redisKdmKey, e);
        }

        if(redisKdmValue == null){
            KdmInfo kdmInfo = kdmInfoService.getKDMByUuidAndCertificate(uuid, certificate);
            if(kdmInfo == null){
                return  new ResultBody(ErrorInfoEnum.KDM_NOT_FOUND);
            }else {
                String path = kdmInfo.getPath();
                String xmlStr = FileUtil.readToString(path);
                if(StringUtils.isEmpty(xmlStr)){
                    return  new ResultBody(ErrorInfoEnum.KDM_FORMAT_ERR);
                }
                kdmQuerRsponse.setContent(xmlStr);
                kdmQuerRsponse.setTitle(new File(path).getName());

                try {
                    simpleRedisService.setStringExpire(redisKdmKey, JSON.toJSONString(kdmQuerRsponse), 15*24*3600);
                } catch (Exception e) {
                    log.error("KdmController.redisUtil.set key {},error",redisKdmKey, e);
                }
            }
            log.info("KdmController.query res {}", JSONObject.toJSONString(kdmInfo));
        }else {
            kdmQuerRsponse = JSONObject.parseObject(redisKdmValue, KdmQuerRsponse.class);
        }
        return new ResultBody(kdmQuerRsponse);
    }

/*    @ApiOperation(value = "密钥下载接口")
    @RequestMapping(value = "/v1/kdm/download")
    public void download(HttpServletRequest request, HttpServletResponse response, @ModelAttribute KdmQuerRequest kdmQuerRequest){
        try {
            String uuid = kdmQuerRequest.getUuid();
            String certificate = kdmQuerRequest.getCertificate();

            kdmDownloadService.kdmDownload(request,response,uuid,certificate);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }*/

}
