package com.cupshe.ak.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统一业务异常
 * <p>Title: BusinessException</p>
 * <p>Description: </p>
 * @author zhoutaoping
 * @date 2020年11月9日
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BusinessException extends RuntimeException{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6951032092071539132L;

	/**
	 * 错误编码
	 */
	private String retCode;
	
	/**
	 * 错误信息
	 */
	private String retInfo;
	
	/**
	 * 错误数据
	 */
	private Object data;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String retCode, String retInfo) {
		super(retInfo);
		this.retCode = retCode;
		this.retInfo = retInfo;
	}
	
	public BusinessException(String retCode, String retInfo, Object data) {
		super(retInfo);
		this.retCode = retCode;
		this.retInfo = retInfo;
		this.data = data;
	}
	
}
