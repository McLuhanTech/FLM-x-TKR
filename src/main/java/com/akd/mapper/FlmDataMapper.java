package com.akd.mapper;

import com.akd.entity.FlmData;

import java.util.List;


public interface FlmDataMapper 
{
	/**
     * 查询FLM数据信息
     * 
     * @param id FLM数据ID
     * @return FLM数据信息
     */
	public FlmData selectFlmDataById(Integer id);
	
	/**
     * 查询FLM数据列表
     * 
     * @param flmData FLM数据信息
     * @return FLM数据集合
     */
	public List<FlmData> selectFlmDataList(FlmData flmData);
	
	/**
     * 新增FLM数据
     * 
     * @param flmData FLM数据信息
     * @return 结果
     */
	public int insertFlmData(FlmData flmData);
	
	/**
     * 修改FLM数据
     * 
     * @param flmData FLM数据信息
     * @return 结果
     */
	public int updateFlmData(FlmData flmData);
	
	/**
     * 删除FLM数据
     * 
     * @param id FLM数据ID
     * @return 结果
     */
	public int deleteFlmDataById(Integer id);
	
	/**
     * 批量删除FLM数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFlmDataByIds(String[] ids);
	
}