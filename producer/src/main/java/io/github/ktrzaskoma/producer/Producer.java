package io.github.ktrzaskoma.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {

    private final ObjectMapper objectMapper;
    @Value("${topic.name}")
    private String orderTopic;

    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(ObjectMapper mapper, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public String sendMessage(FoodOrder foodOrder) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(foodOrder);
        kafkaTemplate.send(orderTopic, orderAsMessage);

        log.info("food order produced {}", orderAsMessage);

        return "message sent";
    }


}
