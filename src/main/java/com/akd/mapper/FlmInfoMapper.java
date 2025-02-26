package com.akd.mapper;

import com.akd.entity.FlmInfo;

public interface FlmInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlmInfo record);

    int insertSelective(FlmInfo record);

    FlmInfo selectByPrimaryKey(Integer id);

    FlmInfo selectByCinemaInfoId(Long toKen);

    FlmInfo selectByMessageId(String messageId);

    int updateByPrimaryKeySelective(FlmInfo record);

    int updateByPrimaryKeyWithBLOBs(FlmInfo record);

    int updateByPrimaryKey(FlmInfo record);
}