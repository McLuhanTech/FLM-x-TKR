package com.akd.common.result.err;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * user rule:
 * service errorEnum save to this errorInfoEnum
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorInfoEnum implements ErrorInterface {
    SUCCESS("000000","成功"),
    PARAM_ERR("999998","参数校验异常"),
    FAILED("999999","系统异常"),


    KDM_NOT_FOUND("009404","秘钥暂不存在"),
    KDM_FORMAT_ERR("009405","秘钥格式不正确"),
    REQUEST_TOO_MANY("009100","请求过于频繁"),

    TOKEN_INVALID("001001","无效token"),
    FLM_VERIFY_NOT_PASS("001002","FLM校验未通过"),
    FLM_TOKEN_ERROR("001003","请勿重复生成token"),
    FLM_NOT_FOUND("001004","暂无该影院信息"),
    ;
    private String retCode;

    private String retMessage;

    public static ErrorInfoEnum getByCode(String code){
        for (ErrorInfoEnum errorInfoEnum : ErrorInfoEnum.values()) {
            if (code.equals(errorInfoEnum.getRetCode())){
                return errorInfoEnum;
            }
        }
        return null;
    }


}
