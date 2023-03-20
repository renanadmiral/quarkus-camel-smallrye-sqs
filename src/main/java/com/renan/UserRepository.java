package com.renan;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LockMode;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@ActivateRequestContext
@Slf4j
public class UserRepository implements PanacheRepository<User> {

    private final Mutiny.SessionFactory sessionFactory;

    public UserRepository(Mutiny.SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @ReactiveTransactional
    public Uni<Void> save(User user) {
        return persist(user)
                .invoke(() -> log.info("Saved user {}", user.getFirstName()))
                .replaceWithVoid();
    }

    public Uni<Void> saveUsingSession(User user) {
        return sessionFactory
                .withTransaction((session, transaction) -> session.persist(user))
                .invoke(() -> log.info("Saved user {}", user.getFirstName()))
                .replaceWithVoid();
    }

    @Transactional
    public User query(final Long id) {
        return findById(id, LockModeType.PESSIMISTIC_WRITE).await().indefinitely();
    }
}