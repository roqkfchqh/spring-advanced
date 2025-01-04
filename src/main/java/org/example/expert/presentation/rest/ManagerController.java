package org.example.expert.presentation.rest;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.manager.Manager;
import org.example.expert.infrastructure.jwt.JwtUtil;
import org.example.expert.domain.user.auth.Auth;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.presentation.external.dto.request.ManagerSaveRequestDto;
import org.example.expert.presentation.external.dto.response.ManagerResponse;
import org.example.expert.application.service.ManagerService;
import org.example.expert.presentation.external.mapper.ManagerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final JwtUtil jwtUtil;
    private final ManagerMapper managerMapper;

    @PostMapping("/todos/{todoId}/managers")
    public ResponseEntity<ManagerResponse> saveManager(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody final ManagerSaveRequestDto dto
    ) {
        Manager manager = managerService.saveManager(authUser, todoId, dto);
        return ResponseEntity.ok(managerMapper.toDto(manager));
    }

    @GetMapping("/todos/{todoId}/managers")
    public ResponseEntity<List<ManagerResponse>> getMembers(
            @PathVariable long todoId
    ) {
        List<Manager> manager = managerService.getManagers(todoId);
        return ResponseEntity.ok(manager
                .stream()
                .map(managerMapper::toDto)
                .toList());
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
