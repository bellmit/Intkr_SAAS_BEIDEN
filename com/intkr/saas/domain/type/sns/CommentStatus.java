package com.intkr.saas.domain.type.sns;

/**
 * 
 * @author Beiden
 * @date 2011-10-18 下午2:30:44
 * @version 1.0
 */
public enum CommentStatus {

	WaitAudit("waitAudit", "待审核"), //
	unapprove("unapprove", "驳回"), //
	Spam("spam", "垃圾"), //
	Trash("trash", "回收站"), //
	Approve("approve", "批准"), //
	Error("error", "异常"); //

	private CommentStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public static CommentStatus getByCode(String code) {
		CommentStatus[] values = values();
		for (CommentStatus value : values) {
			if (value.getCode().equals(code)) {
				return value;
			}
		}
		return Error;
	}

}
