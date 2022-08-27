package com.reverb.springboot.aws.api.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SNSController {

    @Autowired
    private AmazonSNSClient snsClient;

    @Value("${application.sns.topic}")
    private String snsTopic;

    @GetMapping("/addSubscription/{email}")
    public String addSubscription(@PathVariable String email){

        SubscribeRequest req=new SubscribeRequest(snsTopic,"email",email);
        snsClient.subscribe(req);
        return "please confirm your pending subscription at "+email;

    }

    @GetMapping("/sendNotification")
    public String publishMessageToTopic(){
        PublishRequest publishRequest=new PublishRequest(snsTopic,getEmailBody(),"Servers Down");
        snsClient.publish(publishRequest);

        return "Message Sent";

    }

    private String getEmailBody() {
        return "Hi Team,\n" +
                "\n" +
                "Our Application server is down due to some technical Issues.\n" +
                "We are rectifying the issues and will notify once it is fixed.\n" +
                "\n" +
                "Thanks,\n" +
                "Reverb";
    }

}
