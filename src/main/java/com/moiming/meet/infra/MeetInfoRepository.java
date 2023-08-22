package com.moiming.meet.infra;

import com.moiming.meet.domain.MeetInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetInfoRepository extends JpaRepository<MeetInfo, Long> {
}
