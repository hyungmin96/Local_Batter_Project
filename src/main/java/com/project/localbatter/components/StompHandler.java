package com.project.localbatter.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompHandler implements ChannelInterceptor {

    private final Logger log = LogManager.getLogger();

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        switch (accessor.getCommand()){
            case CONNECT:
                log.info("connected!");
                log.info("sessionId : " + accessor.getSessionId());
                log.info("command Type : " + accessor.getCommand());
                log.info("Login username : " + accessor.getUser().getName());
                break;
            case DISCONNECT:
                log.info("disconnected!");
                break;
        }
    }
}
