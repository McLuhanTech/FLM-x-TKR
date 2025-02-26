package com.akd.mapper;

import com.akd.entity.KdmFailInfo;
import org.apache.ibatis.annotations.Param;

public interface KdmFailInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KdmFailInfo record);

    int insertSelective(KdmFailInfo record);

    KdmFailInfo selectByPrimaryKey(Integer id);

    KdmFailInfo selectByFileNameAndSource(@Param("fileName") String fileName, @Param("source") String source);

    int updateByPrimaryKeySelective(KdmFailInfo record);

    int updateByPrimaryKey(KdmFailInfo record);
}