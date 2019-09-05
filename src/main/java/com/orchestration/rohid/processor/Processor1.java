package com.orchestration.rohid.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.orchestration.rohid.dao.OrchestrationDao;
import com.orchestration.rohid.domain.Orchestration;

public class Processor1 implements Processor {

	
	
	private static final Logger logger = LoggerFactory.getLogger(Processor1.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("enter process 1");

		String body = exchange.getIn().getBody(String.class);
		String jobId = (String) exchange.getIn().getHeader("JobId");
		String processName = (String) exchange.getIn().getHeader("ProcessName");

		OrchestrationDao orDao = exchange.getProperty("orDao", OrchestrationDao.class);
		logger.info("jobId" + jobId);
		logger.info("processName" + processName);

		Orchestration or = null;

		or = orDao.getOrchestration(Integer.valueOf(jobId), processName);
		if (null == or) {
			or = new Orchestration();
			or.setJobId(5);
			or.setRefId(5);
			or.setProcessName("1");
			or.setStatus("In-Progress");

			orDao.createOrchestration(or);

			or = orDao.getOrchestration(Integer.valueOf("5"), processName);
			logger.info("value after retreival =" + or.getProcessName() + or.getStatus());
		}
		
		if (or.getStatus().equalsIgnoreCase("In-Progress"))
		{

			/*
			 * 
			 * //make a rest call here
			 * System.out.println("after rest call to another rest service"); final String
			 * uri = "http://localhost:8888/hello";
			 * 
			 * RestTemplate restTemplate = new RestTemplate(); String result =
			 * restTemplate.getForObject(uri, String.class);
			 * 
			 * System.out.println("<<<<<output from another rest end point>>>>");
			 * System.out.println(result);
			 * 
			 * //massaged data body = body + result;
			 * 
			 */
			Thread.sleep(10000);
			body = "Output from process 1";
	
			exchange.getOut().setBody(body);
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);
			
			//mark task as complete
			or = new Orchestration();
			or.setJobId(5);
			or.setRefId(5);
			or.setProcessName("1");
			or.setStatus("Success");
			or.setResult(body);
			orDao.updateOrchestration(or);
			
			or = orDao.getOrchestration(Integer.valueOf("5"), processName);
			logger.info("value after retreival =" + or.getProcessName() + or.getStatus());
		}
		
		if (or.getStatus().equalsIgnoreCase("Success"))
		{
			body = or.getResult();
			//mark job as complete'
			exchange.getOut().setBody(body);
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);
			
		}
		
		

		logger.info("exit process 1");
	}

}
