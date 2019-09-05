package com.orchestration.rohid.dao;

import com.orchestration.rohid.domain.Orchestration;



public interface OrchestrationDao {

public  void createOrchestration( Orchestration input);


	
	public Orchestration getOrchestration(Integer id, String processName);
	public void updateOrchestration(Orchestration input);
	
	

}