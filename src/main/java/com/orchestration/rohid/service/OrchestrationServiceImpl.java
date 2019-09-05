package com.orchestration.rohid.service;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.orchestration.rohid.controller.OrchestrationController;
import com.orchestration.rohid.dao.OrchestrationDao;
import com.orchestration.rohid.domain.Orchestration;

@Service
public class OrchestrationServiceImpl implements OrchestrationService {

	private static final Logger logger = LoggerFactory.getLogger(OrchestrationController.class);

	@Autowired
	private ProducerTemplate producer;

	@Autowired
	private CamelContext camelContext;

	@Autowired
	OrchestrationDao orDao;

	@Async("processExecutor")
	public void process(String input) throws Exception {

		
		String responseBody = null;
		Orchestration or = null;
		Exchange requestExchange = null;
		String jobId = null;
		
		

		try {

			requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(input).withHeader("JobId", "5")
					.withHeader("ProcessName", "1").withProperty("orDao", orDao).build();

			Exchange responseExchange = producer.send("direct:process1", requestExchange);
			responseBody = responseExchange.getOut().getBody(String.class);

			logger.info("output from process 1:" + responseBody);

			// call second processor:
			
			requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(input).withHeader("JobId", "5")
					.withHeader("ProcessName", "2").withProperty("orDao", orDao).build();

			
			responseExchange = producer.send("direct:process2", requestExchange);
			responseBody = responseExchange.getOut().getBody(String.class);
			logger.info("output from process 2:" + responseBody);

			// call 3rd processor:
			
			requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(input).withHeader("JobId", "5")
					.withHeader("ProcessName", "3").withProperty("orDao", orDao).build();

			
			responseExchange = producer.send("direct:process3", requestExchange);
			responseBody = responseExchange.getOut().getBody(String.class);
			logger.info("output from process 3:" + responseBody);
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			producer.sendBodyAndHeader("activemq:topic:error", input, "jobId", "5");
		}
		
	}

}
