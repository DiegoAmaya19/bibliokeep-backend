package com.devsenior.diego.bibliokeep.controller;

import com.devsenior.diego.bibliokeep.model.dto.request.LoanRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.LoanResponseDTO;
import com.devsenior.diego.bibliokeep.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponseDTO create(@Valid @RequestBody LoanRequestDTO request,
                                   @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return loanService.create(request, ownerId);
    }

    @GetMapping
    public List<LoanResponseDTO> findAll(@RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return loanService.findAllByOwnerId(ownerId);
    }

    @GetMapping("/{id}")
    public LoanResponseDTO findById(@PathVariable Long id,
                                    @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return loanService.findById(id, ownerId);
    }

    @PutMapping("/{id}")
    public LoanResponseDTO update(@PathVariable Long id,
                                   @Valid @RequestBody LoanRequestDTO request,
                                   @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return loanService.update(id, request, ownerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
                       @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        loanService.delete(id, ownerId);
    }

    @PatchMapping("/{id}/return")
    public LoanResponseDTO markAsReturned(@PathVariable Long id,
                                           @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return loanService.markAsReturned(id, ownerId);
    }
}
