package com.orchestration.rohid.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

public class Processor5 implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("enter process 5");
		
		String body = exchange.getIn().getBody(String.class);
		System.out.println("<<<body>>> processor  5:" + body);
		
		
		//make a rest call here
		
	    
	    
	    
		exchange.getOut().setBody(" Processor 5: " + body);
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);
	}

}
