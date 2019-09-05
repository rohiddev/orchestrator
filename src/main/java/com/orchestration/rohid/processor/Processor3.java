package com.orchestration.rohid.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.orchestration.rohid.dao.OrchestrationDao;
import com.orchestration.rohid.domain.Orchestration;

public class Processor3 implements Processor {
	
	
	private static final Logger logger = LoggerFactory.getLogger(Processor3.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("enter process 3");

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
			or.setProcessName("3");
			or.setStatus("In-Progress");

			orDao.createOrchestration(or);

			or = orDao.getOrchestration(Integer.valueOf("5"), processName);
			logger.info("value after retreival =" + or.getProcessName() + or.getStatus());
		}

		if (or.getStatus().equalsIgnoreCase("In-Progress")) {

			Thread.sleep(10000);
			body = "output from process 3";

			exchange.getOut().setBody(body);
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);
			// mark task as complete
			or = new Orchestration();
			or.setJobId(5);
			or.setRefId(5);
			or.setProcessName("3");
			or.setResult(body);
			or.setStatus("Success");
			orDao.updateOrchestration(or);

			or = orDao.getOrchestration(Integer.valueOf("5"), processName);
			logger.info("value after retreival =" + or.getProcessName() + or.getStatus());
		}

		if (or.getStatus().equalsIgnoreCase("Success")) {
			// mark job as complete'
			body = 	or.getResult();
			exchange.getOut().setBody(body);
			exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);

		}

		logger.info("exit process 3");
	}

}
