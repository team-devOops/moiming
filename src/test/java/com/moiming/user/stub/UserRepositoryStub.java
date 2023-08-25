package com.moiming.user.stub;

import com.moiming.user.domain.Email;
import com.moiming.user.domain.User;
import com.moiming.user.domain.UserRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryStub implements UserRepository {

    private Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        Long key = users.keySet().stream().max(Long::compareTo).orElse(0L) + 1L;

        User newUser = User.builder()
                        .seq(key)
                        .id(user.getId())
                        .birthDate(user.getBirthDate())
                        .email(user.getEmail())
                        .name(user.getName())
                        .password(user.getPassword())
                        .build();

        users.put(key, newUser);

        return users.get(key);
    }

    @Override
    public Optional<User> findById(String id) {
        return users.values()
                .stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();

    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return  users.values()
                .stream()
                .filter(it -> it.getEmail().equals(email))
                .findFirst();
    }
}
