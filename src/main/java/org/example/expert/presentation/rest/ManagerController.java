package org.example.expert.presentation.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.presentation.utils.Auth;
import org.example.expert.presentation.utils.AuthUser;
import org.example.expert.application.dto.request.ManagerSaveRequestDto;
import org.example.expert.application.dto.response.ManagerResponse;
import org.example.expert.application.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/todos/{todoId}/managers")
    public ResponseEntity<ManagerResponse> saveManager(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody final ManagerSaveRequestDto dto
    ) {
        ManagerResponse manager = managerService.saveManager(authUser, todoId, dto);
        return ResponseEntity.ok(manager);
    }

    @GetMapping("/todos/{todoId}/managers")
    public ResponseEntity<List<ManagerResponse>> getMembers(
            @PathVariable long todoId
    ) {
        List<ManagerResponse> manager = managerService.getManagers(todoId);
        return ResponseEntity.ok(manager);
    }

    @DeleteMapping("/todos/{todoId}/managers/{managerId}")
    public ResponseEntity<String> deleteManager(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @PathVariable long managerId
    ) {
        managerService.deleteManager(authUser, todoId, managerId);
        return ResponseEntity.ok(todoId + "번 게시물의 " + managerId + "번 관리자가 정상적으로 삭제되었습니다.");
    }
}
