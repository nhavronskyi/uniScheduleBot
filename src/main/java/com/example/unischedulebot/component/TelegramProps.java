package com.example.unischedulebot.component;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "telegram")
public class TelegramProps {

    private String token;
    private String username;
}
