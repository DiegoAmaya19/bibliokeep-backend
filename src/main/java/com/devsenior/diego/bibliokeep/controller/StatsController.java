package com.devsenior.diego.bibliokeep.controller;

import com.devsenior.diego.bibliokeep.model.dto.response.DashboardResponseDTO;
import com.devsenior.diego.bibliokeep.service.StatsService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/dashboard/{userId}")
    public DashboardResponseDTO getDashboard(@PathVariable String userId) {
        var ownerId = UUID.fromString(userId);
        return statsService.getDashboard(ownerId);
    }
}