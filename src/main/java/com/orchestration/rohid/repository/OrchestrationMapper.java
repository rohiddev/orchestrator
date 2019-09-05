package com.orchestration.rohid.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.orchestration.rohid.domain.Orchestration;

@Mapper
public interface OrchestrationMapper {

	@Insert("INSERT INTO orchestration(processname, status, jobid, refid) "
			+ "VALUES(#{processName}, #{status}, #{jobId}, #{refId})")
	public void insertOrchestration(@Param("processName") String processName, @Param("status") String status, @Param("jobId") int jobId, @Param("refId") int refId);

	@Select("SELECT * FROM orchestration WHERE jobid = #{jobId} and processname=#{processName}")
	@Results({ @Result(property = "jobId", column = "jobid"), @Result(property = "refId", column = "refid"),
			@Result(property = "processName", column = "processname"), @Result(property = "status", column = "status"),
			@Result(property = "result", column = "result") })
	public Orchestration getOrchestration(@Param("jobId") Integer jobId, @Param("processName") String processName);

	@Update("update orchestration set status=#{status}, result=#{result}, error=#{error} where jobid=#{jobId} and processname=#{processName} ")
	public void updateOrchestration(@Param("status") String status, @Param("result") String result, @Param("error") String error, @Param("jobId") Integer jobId, @Param("processName") String processName);

}