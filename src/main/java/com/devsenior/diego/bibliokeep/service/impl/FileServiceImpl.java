package com.devsenior.diego.bibliokeep.service.impl;

import org.springframework.web.multipart.MultipartFile;

import com.devsenior.diego.bibliokeep.model.dto.response.UpdateResponseDTO;
import com.devsenior.diego.bibliokeep.service.FileService;

public class FileServiceImpl implements FileService {

    @Override
    public UpdateResponseDTO save(MultipartFile file) {
        

        var fileName = "";
        var url = "";

        return new UpdateResponseDTO(fileName, url, file.getSize());
    }
    
}
