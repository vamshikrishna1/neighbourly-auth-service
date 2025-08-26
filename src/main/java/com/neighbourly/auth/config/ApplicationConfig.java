package com.neighbourly.auth.config;


import com.neighbourly.auth.store.CacheStore;
import com.neighbourly.auth.store.RedisStore;
import com.neighbourly.auth.store.SimpleStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ApplicationConfig {

    @Value("${app.mail.username}")
    private String userName;
    @Value("${app.mail.password}")
    private String password;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }


    @Bean
    public CacheStore simpleOtpStore(){
        return new SimpleStore();
    }


    @Bean
    @Profile("!dev")
    public CacheStore redisOtpStore(){
        return new RedisStore();
    }





}
