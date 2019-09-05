package com.orchestration.rohid.controller;

import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.orchestration.rohid.dao.OrchestrationDao;
import com.orchestration.rohid.domain.Orchestration;
import com.orchestration.rohid.service.OrchestrationService;

@Controller
@RequestMapping("service")
public class OrchestrationController {

	private static final Logger logger = LoggerFactory.getLogger(OrchestrationController.class);

	@Autowired
	private ProducerTemplate producer;
	
	@Autowired
	private ConsumerTemplate consumer;

	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	

	@Autowired
	OrchestrationDao orDao;

	@Autowired
	OrchestrationService orchestrationService;

	@PostMapping(value = "/process", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<?> hello(final HttpServletRequest request, @RequestBody Map<String, Object> payload) {

		Exchange requestExchange = null;
		String responseBody = null;
		Orchestration or = null;
		String input = "Input to service";

		try {

			// validate input.
			// generate jobid.
			// call orchestration service
			orchestrationService.process(input);

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		responseBody = "Success";
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);

	}
	
	
	
	@PostMapping(value = "/processmessage", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<?> processmessage(final HttpServletRequest request, @RequestBody Map<String, Object> payload) {

		Exchange requestExchange = null;
		String responseBody = null;
		Orchestration or = null;
		String input = "Input to service";

		try {
			
			
			jmsTemplate.convertAndSend("jms:errortopic", "this is a test", m -> {

	           
	            m.setJMSCorrelationID("5");
	           
	            m.setJMSMessageID("5");
	           

	            return m;
	        });

			
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		responseBody = "Success";
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);

	}
	
	

	/*
	 * 
	 * @PostMapping(value = "/hello2", consumes = { MediaType.APPLICATION_JSON_VALUE
	 * }, produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseBody public ResponseEntity<?> hello2(@RequestBody String input) {
	 * 
	 * System.out.println("payload" + input); final Exchange requestExchange =
	 * ExchangeBuilder.anExchange(camelContext).withBody(input).build(); final
	 * Exchange responseExchange = producer.send("direct:hello2", requestExchange);
	 * final String responseBody = responseExchange.getOut().getBody(String.class);
	 * 
	 * System.out.println("<<<responseBod>>>" + responseBody); final int
	 * responseCode =
	 * responseExchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE,
	 * Integer.class) .intValue(); System.out.println("<<<responseCode" +
	 * responseCode);
	 * 
	 * // return ResponseEntity.status(responseCode).body(responseBody);
	 * 
	 * // TODO - rohid the above doesnt seem to work correctly for unknown reasons.
	 * return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	 * 
	 * }
	 * 
	 * @PostMapping(value = "/hello3", consumes = { MediaType.APPLICATION_JSON_VALUE
	 * }, produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseBody public ResponseEntity<?> hello3(@RequestBody String input) {
	 * 
	 * System.out.println("payload" + input); final Exchange requestExchange =
	 * ExchangeBuilder.anExchange(camelContext).withBody(input).build(); final
	 * Exchange responseExchange = producer.send("direct:hello3", requestExchange);
	 * 
	 * System.out.println("<<<responseBod>>>" +
	 * "payload sent to multiple processors"); String responseBody = "Success";
	 * 
	 * return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	 * 
	 * }
	 * 
	 * @GetMapping(value = "/poll", consumes = { MediaType.APPLICATION_JSON_VALUE },
	 * produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseBody public ResponseEntity<?> poll() {
	 * 
	 * try { System.out.println("start of poll"); try {
	 * System.out.println("fetch Message" + fetchMessage()); } catch
	 * (PollerStoppedException ex) { ex.printStackTrace(); }
	 * 
	 * } catch (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (ExecutionException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * return new ResponseEntity<Object>("poll", HttpStatus.OK);
	 * 
	 * }
	 * 
	 * public Boolean fetchMessage() throws InterruptedException, ExecutionException
	 * {
	 * 
	 * Poller<Boolean> poller = PollerBuilder.<Boolean>newBuilder()
	 * 
	 * .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.MINUTES))
	 * .withStopStrategy(StopStrategies.stopAfterAttempt(5)).polling(
	 * 
	 * new AttemptMaker<Boolean>() {
	 * 
	 * @Override public AttemptResult<Boolean> process() throws Exception { Boolean
	 * create = false; System.out.println("process"); final String uri =
	 * "http://localhost:8888/hello";
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); String result =
	 * restTemplate.getForObject(uri, String.class); System.out.println("result" +
	 * result);
	 * 
	 * create = true;
	 * 
	 * if (result.equals("Success")) { return AttemptResults.finishWith(true); }
	 * else { return AttemptResults.justContinue(); } } }) .build();
	 * 
	 * return poller.start().get(); }
	 * 
	 */

}
