package com.akd.service;

import java.util.List;

import com.akd.entity.FlmParsingAuditorium;
import com.akd.mapper.FlmParsingAuditoriumMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlmParsingAuditoriumService
{
	@Autowired
	private FlmParsingAuditoriumMapper flmParsingAuditoriumMapper;

	/**
     * 查询设备列信息
     * 
     * @param id 设备列ID
     * @return 设备列信息
     */
	public FlmParsingAuditorium selectFlmParsingAuditoriumById(Integer id)
	{
	    return flmParsingAuditoriumMapper.selectFlmParsingAuditoriumById(id);
	}
	
	/**
     * 查询设备列列表
     * 
     * @param flmParsingAuditorium 设备列信息
     * @return 设备列集合
     */
	public List<FlmParsingAuditorium> selectFlmParsingAuditoriumList(FlmParsingAuditorium flmParsingAuditorium)
	{
	    return flmParsingAuditoriumMapper.selectFlmParsingAuditoriumList(flmParsingAuditorium);
	}
	
    /**
     * 新增设备列
     * 
     * @param flmParsingAuditorium 设备列信息
     * @return 结果
     */
	public int insertFlmParsingAuditorium(FlmParsingAuditorium flmParsingAuditorium)
	{
	    return flmParsingAuditoriumMapper.insertFlmParsingAuditorium(flmParsingAuditorium);
	}
	
	/**
     * 修改设备列
     * 
     * @param flmParsingAuditorium 设备列信息
     * @return 结果
     */
	public int updateFlmParsingAuditorium(FlmParsingAuditorium flmParsingAuditorium)
	{
	    return flmParsingAuditoriumMapper.updateFlmParsingAuditorium(flmParsingAuditorium);
	}

	
}
