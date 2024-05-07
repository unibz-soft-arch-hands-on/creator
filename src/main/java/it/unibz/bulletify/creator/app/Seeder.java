package it.unibz.bulletify.creator.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Seeder implements CommandLineRunner {
    private final CreateItemAndBroadcast createItemAndBroadcast;

    @Autowired
    public Seeder(CreateItemAndBroadcast createItemAndBroadcast) {
        this.createItemAndBroadcast = createItemAndBroadcast;
    }

    @Override
    public void run(String... args) throws Exception {
        List.of(
                new CreateItemFormDTO("canederli", "missing ingredient"),
                new CreateItemFormDTO("rabanada", "extra sweet"),
                new CreateItemFormDTO("rice and beans", "extra salty"),
                new CreateItemFormDTO("vin brule", "extra sweet"),
                new CreateItemFormDTO("mini pizza", "burned")
        ).forEach(createItemAndBroadcast::createAndBroadcast);
    }
}
