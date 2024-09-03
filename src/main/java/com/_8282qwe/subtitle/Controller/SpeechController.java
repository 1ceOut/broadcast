package com._8282qwe.subtitle.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Controller
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class SpeechController implements WebSocketMessageBrokerConfigurer {

    @MessageMapping("/{room}/audio")
    @SendTo("/live/topic/{room}/subtitles")
    public String handleAudio(@DestinationVariable String room, String subtitle) throws Exception {
        return subtitle;
    }
}
