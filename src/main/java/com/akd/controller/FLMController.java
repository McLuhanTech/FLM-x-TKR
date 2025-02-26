package com.akd.controller;


import com.akd.common.result.ResultBody;
import com.akd.common.result.err.ErrorInfoEnum;
import com.akd.controller.base.BaseController;
import com.akd.entity.CinemaInfo;
import com.akd.entity.FlmData;
import com.akd.entity.request.FlmInfoRequest;
import com.akd.entity.request.TmsRequest;
import com.akd.entity.response.FlmInfoResponse;
import com.akd.mapper.CinemaInfoMapper;
import com.akd.service.FLMTMSService;
import com.akd.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Api(tags = "flm相关接口")
@Slf4j
@RestController
public class FLMController  extends BaseController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private FLMTMSService flmtmsService;
    @Resource
    private CinemaInfoMapper cinemaInfoMapper;


    @ApiOperation(value = "token生成接口")
    @PostMapping(value = "/v1/createToken")
    public ResultBody createToken(@ModelAttribute CinemaInfo webCinemaInfo){
        String cinemaAddress = webCinemaInfo.getCinemaAddress();
        String cinemaCode = webCinemaInfo.getCinemaCode();
        String conactName = webCinemaInfo.getConactName();
        String conactPhone = webCinemaInfo.getConactPhone();
        String cinemaName = webCinemaInfo.getCinemaName();
        CinemaInfo dbInfo = cinemaInfoMapper.selectByCinemaCode(webCinemaInfo.getCinemaCode());
        if(dbInfo == null){
            if(StringUtils.isEmpty(cinemaAddress) || StringUtils.isEmpty(cinemaCode) ||
                    StringUtils.isEmpty(conactName) || StringUtils.isEmpty(conactPhone) || StringUtils.isEmpty(cinemaName)){
                return new ResultBody(ErrorInfoEnum.PARAM_ERR);
            }
        }else {
            if(StringUtils.isEmpty(cinemaCode)){
                return new ResultBody(ErrorInfoEnum.PARAM_ERR);
            }
        }

        String token = tokenService.createToken(webCinemaInfo, dbInfo);
        if(token == null){
            return new ResultBody(ErrorInfoEnum.FLM_TOKEN_ERROR);
        }
        return  new ResultBody(token);
    }
    @ApiOperation(value = "新增影院设备信息接口")
    @PostMapping(value = "/v1/flm/add")
    public ResultBody addInformation(@ModelAttribute FlmInfoRequest flmInfoRequest){
        String token = flmInfoRequest.getToken();
        String flm = flmInfoRequest.getFlm();

        if(StringUtils.isEmpty(token) ||  StringUtils.isEmpty(flm) ){
            return new ResultBody(ErrorInfoEnum.PARAM_ERR);
        }
        if(!tokenService.verifyToken(token)){
            return new ResultBody(ErrorInfoEnum.TOKEN_INVALID);
        }
        return flmtmsService.pushInformation(token,flm, "add");
    }

    @ApiOperation(value = "修改影院设备信息接口")
    @PostMapping(value = "/v1/flm/update")
    public ResultBody updateInformation(@ModelAttribute FlmInfoRequest flmInfoRequest){
        String token = flmInfoRequest.getToken();
        String flm = flmInfoRequest.getFlm();

        if(StringUtils.isEmpty(token) ||  StringUtils.isEmpty(flm) ){
            return new ResultBody(ErrorInfoEnum.PARAM_ERR);
        }
        if(!tokenService.verifyToken(token)){
            return new ResultBody(ErrorInfoEnum.TOKEN_INVALID);
        }
        return flmtmsService.pushInformation(token,flm, "update");
    }

    @ApiOperation(value = " 获取影院设备信息接口")
    @PostMapping(value = "/v1/flm/getInformation")
    public ResultBody getInformation(@ModelAttribute TmsRequest tmsRequest){
        String token = tmsRequest.getToken();
        if(StringUtils.isEmpty(token)){
            return new ResultBody(ErrorInfoEnum.PARAM_ERR);
        }
        if(!tokenService.verifyToken(token)){
            return new ResultBody(ErrorInfoEnum.TOKEN_INVALID);
        }
        CinemaInfo cinemaInfo = tokenService.getCinemaInfoByToken(token);
        if(cinemaInfo == null){
            return new ResultBody(ErrorInfoEnum.TOKEN_INVALID);
        }
        FlmData information = flmtmsService.getInformation(token,cinemaInfo.getId());
        if(information == null){
            return new ResultBody(ErrorInfoEnum.FLM_NOT_FOUND);
        }
        FlmInfoResponse flmInfoResponse = new FlmInfoResponse();
        flmInfoResponse.setToken(token);
        flmInfoResponse.setFlm(information.getFlm());
        return new ResultBody(flmInfoResponse);
    }

}
