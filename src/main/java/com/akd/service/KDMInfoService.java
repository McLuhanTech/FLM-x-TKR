package com.akd.service;


import com.akd.entity.KdmInfo;
import com.akd.mapper.KdmInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class KDMInfoService {

    @Resource
    private KdmInfoMapper kdmInfoMapper;

    public KdmInfo getKDMByUuidAndCertificate(String uuid, String certificate){
        return kdmInfoMapper.selectByUuidAndCertificate(uuid,certificate);
    }

    public int storageKDMInfo(KdmInfo record){
        return kdmInfoMapper.insertSelective(record);
    }

    public KdmInfo selectKDMInfoByUuid(String uuid){
        return kdmInfoMapper.selectByUuid(uuid);
    }
}
