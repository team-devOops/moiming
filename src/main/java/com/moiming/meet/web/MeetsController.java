package com.moiming.meet.web;

import com.moiming.meet.application.MeetInfoService;
import com.moiming.meet.dto.MeetCreateRequest;
import com.moiming.meet.dto.MeetInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/meets")
@RequiredArgsConstructor
@Tag(name = "모임 관리", description = "모임 관리 관련 API")
public class MeetsController {

    private final MeetInfoService meetInfoService;

    @GetMapping
    @Operation(summary = "모임 조회", description = "생성되어 있는 모임 리스트를 조회합니다.")
    public void meetSelect() {
        //TODO: 리스트 조회의 조건 정책 필요
    }

    @GetMapping("/{meetSeq}")
    @Operation(summary = "모임 상세 조회", description = "특정 모임을 상세 조회합니다.")
    public MeetInfoResponse meetSelect(@PathVariable("meetSeq") Long meetSeq) {
        return meetInfoService.findMeet(meetSeq);
    }

    @PostMapping
    @Operation(summary = "모임 생성", description = "모임을 생성합니다.")
    public ResponseEntity<Void> meetCreate(@RequestBody @Valid MeetCreateRequest request) {
        Long response = meetInfoService.register(request.toEntity());

        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{meetSeq}")
                .buildAndExpand(response)
                .toUri())
                .build();
    }

    @PutMapping
    @Operation(summary = "모임 정보 수정", description = "모임의 정보를 수정합니다.")
    public void meetChange() {
        //TODO: 입력받은 정보대로 모임 정보를 수정합니다.
    }

    @DeleteMapping
    @Operation(summary = "모임 삭제", description = "모임을 해체합니다.")
    public void meetRemove() {
        //TODO: 정책수립 필요.
    }
}
