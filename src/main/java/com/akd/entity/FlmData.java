package com.akd.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FlmData
{
	
	/**  */
	private Long id;
	/**  */
	private Long cinemaInfoId;
	/** flm消息 */
	private String flm;

	private Date createTime;

	private Date updateTime;
}
