package com.moiming.meet.web;

import com.moiming.meet.application.MeetJoinService;
import com.moiming.meet.dto.MeetJoinRequest;
import com.moiming.meet.dto.MeetJoinResponse;
import com.moiming.meet.dto.MeetValidateMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
@RestController
@RequestMapping("/meets/join")
@RequiredArgsConstructor
@Tag(name = "모임 가입", description = "모임 가입 관련 API")
public class MeetJoinController {

    private final MeetJoinService meetJoinService;

    @GetMapping("/{meetJoinId}")
    @Operation(summary = "모임 가입 정보 조회", description = "모임에 가입된 정보 단건을 조회합니다.")
    public MeetJoinResponse meetSelect(@PathVariable("meetJoinId")
                                       @Pattern(regexp = "^[0-9]*$", message = MeetValidateMessage.INPUT_IS_NUMBER_FORMAT) String meetJoinId) {
        return meetJoinService.findByMeetJoinId(Long.valueOf(meetJoinId));
    }

    @PostMapping
    @Operation(summary = "모임 가입", description = "모임에 가입합니다.")
    public ResponseEntity<Void> meetJoin(@RequestBody @Valid MeetJoinRequest request) {
        //TODO: 1. 모임장의 수락 후 가입하는 별도 절차도 필요
        Long response = meetJoinService.register(request);

        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{meetJoinId}")
                        .buildAndExpand(response)
                        .toUri())
                .build();
    }
}
