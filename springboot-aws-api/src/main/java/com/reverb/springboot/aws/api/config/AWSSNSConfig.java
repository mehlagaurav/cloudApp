package com.reverb.springboot.aws.api.config;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSSNSConfig {

    @Value("${cloud.aws.region.static}")
    private String region;
    @Bean
    public AmazonSNSClient getSnsClient(){
        return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(region).build();
    }

}
