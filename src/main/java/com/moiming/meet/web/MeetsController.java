package com.moiming.meet.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meets")
@Tag(name = "모임 관리", description = "모임 관리 관련 API")
public class MeetsController {
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
