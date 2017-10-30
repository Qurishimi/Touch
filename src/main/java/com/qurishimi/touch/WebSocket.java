package com.qurishimi.touch;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qurishimi.touch.model.Greating;
import com.qurishimi.touch.model.Message;

@Controller
public class WebSocket {

       @MessageMapping("/hello")
       @SendTo("/topic/greetings")
	   public Greating greeting(Message message) throws InterruptedException {
    	  Thread.sleep(1000);
    	   return new Greating("Hello : " + message.getName() + " !");
       }
	
}
