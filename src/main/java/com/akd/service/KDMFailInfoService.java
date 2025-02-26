package com.akd.service;

import com.akd.entity.KdmFailInfo;
import com.akd.mapper.KdmFailInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class KDMFailInfoService {

    @Resource
    private KdmFailInfoMapper kdmFailInfoMapper;

    public int storageKDMFailInfo(KdmFailInfo record){
        return kdmFailInfoMapper.insertSelective(record);
    }
    public KdmFailInfo selectKDMFailInfoByFileNameAndSource(String fileName,String source){
        return kdmFailInfoMapper.selectByFileNameAndSource(fileName,source);
    }
}
