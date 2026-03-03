package com.devsenior.diego.bibliokeep.service;

import com.devsenior.diego.bibliokeep.model.dto.response.DashboardResponseDTO;

import java.util.UUID;

public interface StatsService {

    DashboardResponseDTO getDashboard(UUID ownerId);
}