package com.akd.mapper;

import com.akd.entity.CinemaInfo;

public interface CinemaInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CinemaInfo record);

    int insertSelective(CinemaInfo record);

    CinemaInfo selectByPrimaryKey(Integer id);

    CinemaInfo selectByCinemaCode(String cinemaCode);

    CinemaInfo selectByToKen(String toKen);

    int updateByPrimaryKeySelective(CinemaInfo record);

    int updateByPrimaryKey(CinemaInfo record);
}