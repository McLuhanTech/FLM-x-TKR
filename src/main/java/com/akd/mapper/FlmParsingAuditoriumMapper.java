package com.akd.mapper;

import com.akd.entity.FlmParsingAuditorium;
import java.util.List;	



public interface FlmParsingAuditoriumMapper 
{
	/**
     * 查询设备列信息
     * 
     * @param id 设备列ID
     * @return 设备列信息
     */
	public FlmParsingAuditorium selectFlmParsingAuditoriumById(Integer id);
	
	/**
     * 查询设备列列表
     * 
     * @param flmParsingAuditorium 设备列信息
     * @return 设备列集合
     */
	public List<FlmParsingAuditorium> selectFlmParsingAuditoriumList(FlmParsingAuditorium flmParsingAuditorium);
	
	/**
     * 新增设备列
     * 
     * @param flmParsingAuditorium 设备列信息
     * @return 结果
     */
	public int insertFlmParsingAuditorium(FlmParsingAuditorium flmParsingAuditorium);
	
	/**
     * 修改设备列
     * 
     * @param flmParsingAuditorium 设备列信息
     * @return 结果
     */
	public int updateFlmParsingAuditorium(FlmParsingAuditorium flmParsingAuditorium);
	
	/**
     * 删除设备列
     * 
     * @param id 设备列ID
     * @return 结果
     */
	public int deleteFlmParsingAuditoriumById(Integer id);
	
	/**
     * 批量删除设备列
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFlmParsingAuditoriumByIds(String[] ids);


	public FlmParsingAuditorium getDbLatestMessageIdByCinemaInfoId(long cinemaInfoId);
	
}