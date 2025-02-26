package com.akd.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FlmInfo {

    //待上报
    public static final long UNREPORT = 1;

    //已上报
    public static final long REPORTED = 2;

    private Long id;

    private Long cinemaInfoId;

    private Long flmDataId;

    private Long isReport;

    private String type;

    private String flmMd5;

    private String messageId;

    private Date createTime;

    private Date updateTime;

}