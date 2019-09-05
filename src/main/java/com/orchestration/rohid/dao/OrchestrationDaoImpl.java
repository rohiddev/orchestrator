package com.orchestration.rohid.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.orchestration.rohid.domain.Orchestration;
import com.orchestration.rohid.repository.OrchestrationMapper;



@Repository
public class OrchestrationDaoImpl implements OrchestrationDao {
	
	private static Logger logger = LoggerFactory.getLogger(OrchestrationDaoImpl.class);
	
	@Autowired
	OrchestrationMapper orMapper;
	
	public void createOrchestration(Orchestration input) {
		orMapper.insertOrchestration(input.getProcessName(), 
				input.getStatus(), 
				input.getJobId(),
				input.getRefId());
		
	}

	

	
	@Override
	public Orchestration getOrchestration(Integer jobId, String processName) {
		return orMapper.getOrchestration(jobId, processName);
	}

	
	@Override
	public void updateOrchestration(Orchestration input) {
		orMapper.updateOrchestration(input.getStatus(), input.getResult(), input.getError(), input.getJobId(), input.getProcessName());
		
	}
	
	
	
}
