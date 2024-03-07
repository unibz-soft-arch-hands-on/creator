package it.unibz.bulletify.creator.app;

import it.unibz.bulletify.creator.core.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/items")
public class CreateItemController {
    private CreateItemAndBroadcast createItemAndBroadcast;

    @Autowired
    public CreateItemController(CreateItemAndBroadcast createItemAndBroadcast) {
        this.createItemAndBroadcast = createItemAndBroadcast;
    }

    @PostMapping
    public ItemCreatedDTO create(@RequestBody CreateItemFormDTO body) {
        Item newItem = this.createItemAndBroadcast.createAndBroadcast(body);

        return new ItemCreatedDTO(newItem.getId(), newItem.getName(), newItem.getCategory());
    }
}
