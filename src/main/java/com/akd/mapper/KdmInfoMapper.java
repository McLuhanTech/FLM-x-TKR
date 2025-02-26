package com.akd.mapper;

import com.akd.entity.KdmInfo;
import org.apache.ibatis.annotations.Param;

public interface KdmInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KdmInfo record);

    int insertSelective(KdmInfo record);

    KdmInfo selectByPrimaryKey(Integer id);

    KdmInfo selectByUuid(@Param("uuid") String uuid);

    int updateByPrimaryKeySelective(KdmInfo record);

    int updateByPrimaryKey(KdmInfo record);

    KdmInfo selectByUuidAndCertificate(@Param("uuid") String uuid, @Param("certificate") String certificate);
}