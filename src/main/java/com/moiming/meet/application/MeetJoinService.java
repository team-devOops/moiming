package com.moiming.meet.application;

import com.moiming.core.exception.CustomNoResultException;
import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.domain.MeetJoinUser;
import com.moiming.meet.dto.MeetJoinRequest;
import com.moiming.meet.dto.MeetJoinResponse;
import com.moiming.meet.infra.MeetInfoRepository;
import com.moiming.meet.infra.MeetJoinUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetJoinService {
    private final MeetInfoRepository meetInfoRepository;
    private final MeetJoinUserRepository meetJoinUserRepository;

    /**
     * 모임 가입 정보를 조회합니다.
     */
    @Transactional(readOnly = true)
    public MeetJoinResponse findByMeetJoinId(Long meetJoinId) {
        MeetJoinUser response = meetJoinUserRepository.findById(meetJoinId)
                .orElseThrow(CustomNoResultException::new);

        return MeetJoinResponse.fromEntity(response);
    }

    /**
     * 모임에 가입합니다.
     */
    @Transactional
    public Long register(MeetJoinRequest request) {
        MeetInfo meetInfo = meetInfoRepository.findById(request.getMeetId())
                .orElseThrow(CustomNoResultException::new);

        return meetJoinUserRepository.save(request.toEntity(meetInfo))
                .getMeetJoinId();
    }
}
