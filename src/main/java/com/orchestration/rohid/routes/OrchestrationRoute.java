package com.orchestration.rohid.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.orchestration.rohid.processor.Processor1;
import com.orchestration.rohid.processor.Processor2;
import com.orchestration.rohid.processor.Processor3;
import com.orchestration.rohid.processor.Processor4;
import com.orchestration.rohid.processor.Processor5;

@Component
public class OrchestrationRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception 
  {
		
		 errorHandler(defaultErrorHandler()
				 .maximumRedeliveries(5)
				 .redeliveryDelay(2000)
				 .retryAttemptedLogLevel(LoggingLevel.WARN));

		 // in case of a http exception then retry at most 3 times
		 // and if exhausted then upload using ftp instead
		 onException(Exception.class).maximumRedeliveries(3)
				 .handled(true)
				 .to("file:target/ftp/upload");
		 
		 
		from("direct:process1")
			.id("direct:process1")
			.log("in process 1")
			.process(new Processor1());
		
		
		from("direct:process2")
		.id("direct:process2")
		.log("in process 2")
		.process(new Processor2());
		
		
		from("direct:process3")
		.id("direct:process3")
		.log("in process 3")
		.process(new Processor3());
		
		
		//parallel processing
		
		from("direct:hello6")
		.id("helloroute6")
		.log("in hello route6")
		.multicast()
		.parallelProcessing(true)
		.to("direct:hello4")
		.to("direct:hello5");
		
		from("direct:hello4")
		.id("helloroute4")
		.log("in hello route4")
		.process(new Processor4());
		
		from("direct:hello5")
		.id("helloroute5")
		.log("in hello route5")
		.process(new Processor5());
		
		
	
		
		
	}

}