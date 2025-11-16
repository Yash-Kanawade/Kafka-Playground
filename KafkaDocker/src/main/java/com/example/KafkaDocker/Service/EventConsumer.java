package com.example.KafkaDocker.Service;

import com.example.KafkaDocker.Entity.MessageEntity;
import com.example.KafkaDocker.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventConsumer {

    private static final int BATCH_SIZE = 5;
    private final List<String> buffer = new ArrayList<>();

    @Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "demo-topic", groupId = "analytics-group")
    public void receive(String message) {

        synchronized (buffer) {
            buffer.add(message);
            System.out.println("Received and Buffered: " + message);

            if (buffer.size() >= BATCH_SIZE) {
                flushToDB();
            }
        }
    }

    private void flushToDB() {
        List<MessageEntity> batchToSave = buffer
                .stream()
                .map(MessageEntity::new)
                .toList();

        messageRepository.saveAll(batchToSave);

        System.out.println("Batch inserted into DB: " + batchToSave.size());

        buffer.clear();
    }
}
