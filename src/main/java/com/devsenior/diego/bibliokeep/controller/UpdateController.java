package com.devsenior.diego.bibliokeep.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devsenior.diego.bibliokeep.model.dto.response.UpdateResponseDTO;
import com.devsenior.diego.bibliokeep.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class UpdateController {

    private final FileService fileService;

    @PostMapping("/upload")
    public UpdateResponseDTO upload(@RequestParam MultipartFile file) {
        return fileService.save(file);
    }
    
}
