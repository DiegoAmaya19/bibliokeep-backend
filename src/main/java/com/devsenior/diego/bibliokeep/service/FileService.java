package com.devsenior.diego.bibliokeep.service;

import org.springframework.web.multipart.MultipartFile;

import com.devsenior.diego.bibliokeep.model.dto.response.UpdateResponseDTO;

public interface FileService {
    
    UpdateResponseDTO save(MultipartFile file);

}
