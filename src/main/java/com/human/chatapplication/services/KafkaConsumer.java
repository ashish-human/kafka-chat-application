package com.human.chatapplication.services;

import com.human.chatapplication.entity.ChatMessage;
import com.human.chatapplication.enums.MessageType;
import com.human.chatapplication.storage.MessageStorage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    MessageStorage messageStorage;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Value("${message-topic}")
    String kafkaTopic= "Humancloud";

    @KafkaListener(topics = "${message-topic}")
    public void consumer(ConsumerRecord<?, ?> consumerRecord) throws Exception {
        String[] message = consumerRecord.value().toString().split("-");
        LOGGER.info("Consumed data : '{}' from Kafka Topic : {}", Arrays.toString(message), kafkaTopic);
        messageStorage.add(Arrays.toString(message)); // just to show message received from topic. not needed as such.
        // below line sends data to websocket i.e to web browser
        this.simpMessagingTemplate.convertAndSend("/topic/public",
                new ChatMessage(ChatMessage.MessageType.valueOf(message[0]), message[1], message[2]));
    }
}
