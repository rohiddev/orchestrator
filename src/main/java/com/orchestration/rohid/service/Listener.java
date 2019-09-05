package com.orchestration.rohid.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
	
	@Autowired
	OrchestrationService orchestrationService ;

	@JmsListener(destination = "jms:errortopic", containerFactory="myFactory")
	public void receiveMessage(final Message message) throws Exception {
		
		if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }
		
		
		String messageData = null;
		
		String input = "Input for orchestration";
		orchestrationService.process(input);
		
		
	}

}