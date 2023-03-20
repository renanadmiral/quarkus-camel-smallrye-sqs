package com.renan;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
@Slf4j
public class Consumer {

    @Inject
    UserRepository repository;

    @Inject
    ObjectMapper objectMapper;

    public Consumer(UserRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    private User jacksonDecodeValue(final String json) {
        return objectMapper.readValue(json, User.class);
    }

    @Incoming("user-queue-client")
    public Uni<Void> consume(String payload) {
        final var entity = Uni.createFrom()
                .item(payload)
                .map(this::jacksonDecodeValue)
                .onFailure().invoke(t -> log.error(t.getMessage(), t))
                .await().atMost(Duration.of(1, ChronoUnit.MINUTES));

        log.info(entity.toString());
        return repository.save(entity);
    }

//    @Incoming("driverpayments")
//    public Uni<Void> consumeUsingSession(String payload) {
//        return Uni.createFrom()
//                .item(payload)
//                .map(json -> Json.decodeValue(json, User.class))
//                .flatMap(repository::saveUsingSession)
//                .onFailure().invoke(t -> log.error(t.getMessage(), t));
//    }
}
