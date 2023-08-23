package com.moiming.meet.application;

import com.moiming.core.exception.CustomNoResultException;
import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.dto.MeetInfoResponse;
import com.moiming.meet.infra.MeetInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetInfoService {
    private final MeetInfoRepository repository;

    /**
     * 특정 모임을 조회합니다.
     */
    @Transactional(readOnly = true)
    public MeetInfoResponse findMeet(Long meetId) {
        MeetInfo meetInfo = repository.findById(meetId)
            .orElseThrow(CustomNoResultException::new);

        return MeetInfoResponse.fromEntity(meetInfo);
    }

    /**
     * 모임을 생성합니다.
     */
    @Transactional
    public Long register(MeetInfo request) {
        return repository.save(request)
                .getMeetSeq();
    }

    /**
     * 모임을 삭제합니다.
     */
    @Transactional
    public void remove(Long meetId) {
        //TODO: NoResultException -> CustomNoResultException
        MeetInfo meetInfo = repository.findById(meetId)
                .orElseThrow(NoResultException::new);

        meetInfo.meetRemove();
    }
}
