package com.moiming.meet.web;

import com.moiming.meet.application.MeetJoinService;
import com.moiming.meet.dto.MeetJoinRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/meets/join")
@RequiredArgsConstructor
@Tag(name = "모임 가입", description = "모임 가입 관련 API")
public class MeetJoinController {

    private final MeetJoinService meetJoinService;

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
