package com.akd.entity.request;

import lombok.Data;

import java.util.Date;

/**
 * @Copyright neil <neilren@outlook.com>
 * @Author:
 * @Date: 2019/10/30
 * @Description:
 */
@Data
public class TmsRequest {

    private String token;

    private Date requestTime;
}
