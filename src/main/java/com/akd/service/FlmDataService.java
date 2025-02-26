package com.akd.service;

import java.util.List;

import com.akd.entity.FlmData;
import com.akd.mapper.FlmDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FLM数据 服务层实现
 * 
 * @author ruoyi
 * @date 2020-11-10
 */
@Service
@Slf4j
public class FlmDataService
{
	@Autowired
	private FlmDataMapper flmDataMapper;

	/**
     * 查询FLM数据信息
     * 
     * @param id FLM数据ID
     * @return FLM数据信息
     */
	public FlmData selectFlmDataById(Integer id)
	{
	    return flmDataMapper.selectFlmDataById(id);
	}
	
	/**
     * 查询FLM数据列表
     * 
     * @param flmData FLM数据信息
     * @return FLM数据集合
     */
	public List<FlmData> selectFlmDataList(FlmData flmData)
	{
	    return flmDataMapper.selectFlmDataList(flmData);
	}
	
    /**
     * 新增FLM数据
     * 
     * @param flmData FLM数据信息
     * @return 结果
     */
	public int insertFlmData(FlmData flmData)
	{
	    return flmDataMapper.insertFlmData(flmData);
	}
	
	/**
     * 修改FLM数据
     * 
     * @param flmData FLM数据信息
     * @return 结果
     */
	public int updateFlmData(FlmData flmData)
	{
	    return flmDataMapper.updateFlmData(flmData);
	}



}
