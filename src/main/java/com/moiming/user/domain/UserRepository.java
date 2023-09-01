package com.moiming.user.domain;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    User saveAndFlush(User user);

    Optional<User> findByUserId(String id);

    Optional<User> findByEmail(Email email);
}
