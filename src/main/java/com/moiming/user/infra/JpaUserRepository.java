package com.moiming.user.infra;

import com.moiming.user.domain.User;
import com.moiming.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

}
