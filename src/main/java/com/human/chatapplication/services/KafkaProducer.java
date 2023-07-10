package com.human.chatapplication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger LOGGER= LoggerFactory.getLogger(KafkaProducer.class);

    KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    @Value("${message-topic}")
    String kafkaTopic;

    public void send(String data){
        LOGGER.info("KafkaProducer.send :: Topic : {} Data : {}", kafkaTopic,data);
        kafkaTemplate.send(kafkaTopic,data);
    }
}
