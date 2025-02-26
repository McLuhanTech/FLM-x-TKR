package com.akd.entity;

import lombok.Data;

import java.util.Date;


@Data
public class FlmParsingAuditorium
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private long id;
	/** 影院ID */
	private long cinemaInfoId;
	/** 影院名称 */
	private String cinemaName;
	/**  */
	private long flmInfoId;
	/** 消息id */
	private String messageId;
	/** 影厅编号 */
	private String auditoriumNumber;
	/** 影厅名称 */
	private String auditoriumName;
	/** 设备识别 */
	private String deviceIdentifier;
	/** 制造商名称 */
	private String manufacturerName;
	/** 设备型号 */
	private String modelNumber;
	/** 设备状态 */
	private String isActive;
	/** 数据状态 */
	private String postType;

	private Date createTime;

	private Date updateTime;
}
