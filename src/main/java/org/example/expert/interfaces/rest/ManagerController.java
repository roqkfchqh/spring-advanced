package org.example.expert.interfaces.rest;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.manager.Manager;
import org.example.expert.infrastructure.jwt.JwtUtil;
import org.example.expert.domain.user.auth.Auth;
import org.example.expert.domain.user.auth.AuthUser;
import org.example.expert.interfaces.external.dto.request.ManagerSaveDto;
import org.example.expert.interfaces.external.dto.response.ManagerResponse;
import org.example.expert.application.service.ManagerService;
import org.example.expert.interfaces.external.mapper.ManagerMapper;
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
            @Valid @RequestBody final ManagerSaveDto dto
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
    public void deleteManager(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable long todoId,
            @PathVariable long managerId
    ) {
        Claims claims = jwtUtil.extractClaims(bearerToken.substring(7));
        long userId = Long.parseLong(claims.getSubject());
        managerService.deleteManager(userId, todoId, managerId);
    }
}
