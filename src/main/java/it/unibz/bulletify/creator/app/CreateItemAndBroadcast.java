package it.unibz.bulletify.creator.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibz.bulletify.creator.CreatorApplication;
import it.unibz.bulletify.creator.core.CreateItem;
import it.unibz.bulletify.creator.core.Item;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateItemAndBroadcast {
    private CreateItem createItem;
    private RabbitTemplate template;
    private ObjectMapper objectMapper;

    @Autowired
    public CreateItemAndBroadcast(CreateItem createItem, RabbitTemplate template, ObjectMapper objectMapper) {
        this.createItem = createItem;
        this.template = template;
        this.objectMapper = objectMapper;
    }

    public Item createAndBroadcast(CreateItemFormDTO requestBody) {
        Item item = this.createItem.createNewItem(requestBody.getName(), requestBody.getCategory());
        this.broadcast(item);
        return item;
    }

    private void broadcast(Item item) {
        String json = this.toJson(item);
        System.out.println("converted <" + json + ">");
        this.template.convertAndSend(
                CreatorApplication.topicExchange,
                CreatorApplication.routingKey,
                json
        );
    }

    private String toJson(Item item) {
        try {
             return this.objectMapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return "{" +
                        "\"id\":\"" + item.getId() + "\"," +
                        "\"name\":\"" + item.getName() + "\"," +
                        "\"category\":\"" + item.getCategory() + "\"" +
                    "}";
        }
    }
}
