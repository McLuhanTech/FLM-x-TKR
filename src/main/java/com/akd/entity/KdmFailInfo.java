package com.akd.entity;

import lombok.Data;

import java.util.Date;

@Data
public class KdmFailInfo {
    private Integer id;

    private String source;

    private String host;

    private String path;

    private String filename;

    private Date createTime;

    private Date updateTime;


}