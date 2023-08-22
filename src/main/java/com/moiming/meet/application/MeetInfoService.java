package com.moiming.meet.application;

import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.infra.MeetInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetInfoService {
    private final MeetInfoRepository repository;

    /**
     * 모임을 생성합니다.
     */
    @Transactional
    public MeetInfo register(MeetInfo request) {
        return repository.save(request);
    }
}
