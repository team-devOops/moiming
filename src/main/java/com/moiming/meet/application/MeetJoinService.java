package com.moiming.meet.application;

import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.domain.MeetJoinUser;
import com.moiming.meet.dto.MeetJoinRequest;
import com.moiming.meet.infra.MeetInfoRepository;
import com.moiming.meet.infra.MeetJoinUserRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetJoinService {
    private final MeetInfoRepository meetInfoRepository;
    private final MeetJoinUserRepository meetJoinUserRepository;

    /**
     * 모임에 가입합니다.
     */
    @Transactional
    public void register(MeetJoinRequest request) {
        MeetInfo meetInfo = meetInfoRepository.findById(request.getMeetId())
                .orElseThrow(NoResultException::new);

        meetJoinUserRepository.save(request.toEntity(meetInfo));
    }
}
