package com.moiming.meet.infra;

import com.moiming.meet.domain.MeetJoinUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetJoinUserRepository extends JpaRepository<MeetJoinUser, Long> {
}
