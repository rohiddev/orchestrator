package com.orchestration.rohid.domain;

import java.io.Serializable;

public class Orchestration implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2936011647666510462L;
	private Integer jobId;
	private Integer refId;
	                
	private String processName;      
	private String status;           
	private String result;              
	private String error ;
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Integer getRefId() {
		return refId;
	}
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Orchestration(Integer jobId, Integer refId, String processName, String status) {
		this.jobId = jobId;
		this.refId = refId;
		this.processName = processName;
		this.status = status;
	}
	public Orchestration() {
		// TODO Auto-generated constructor stub
	}

	
	
	
}