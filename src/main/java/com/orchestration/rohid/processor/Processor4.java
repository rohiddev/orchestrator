package com.orchestration.rohid.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

public class Processor4 implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("enter process 4");
		
		String body = exchange.getIn().getBody(String.class);
		
		System.out.println("<<<body>>> processor 4" + body);
		
		
	    
	    
	    
		exchange.getOut().setBody("Process 4: " + body);
		exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.ACCEPTED);
	}

}
