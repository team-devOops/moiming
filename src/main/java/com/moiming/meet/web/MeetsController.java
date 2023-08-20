package com.moiming.meet.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meets")
@Tag(name = "모임 관리", description = "모임 관리 관련 API")
public class MeetsController {

    @GetMapping
    @Operation(summary = "모임 조회", description = "생성되어 있는 모임 리스트를 조회합니다.")
    public void meetSelect() {
        //TODO: 리스트 조회의 조건 정책 필요
    }

    @GetMapping("/{meetSeq}")
    @Operation(summary = "모임 상세 조회", description = "특정 모임을 상세 조회합니다.")
    public void meetSelect(@PathParam("meetSeq") Long meetSeq) {
        //TODO: meetSeq에 대한 모임 상세 정보를 조회합니다.
    }

    @PostMapping
    @Operation(summary = "모임 생성", description = "모임을 생성합니다.")
    public void meetCreate() {
        //TODO: 입력받은 정보대로 모임을 생성합니다.
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
