package com.devsenior.diego.bibliokeep.service.impl;

import com.devsenior.diego.bibliokeep.mapper.LoanMapper;
import com.devsenior.diego.bibliokeep.model.dto.request.LoanRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.LoanResponseDTO;
import com.devsenior.diego.bibliokeep.repository.BookRepository;
import com.devsenior.diego.bibliokeep.repository.LoanRepository;
import com.devsenior.diego.bibliokeep.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    @Override
    @Transactional
    public LoanResponseDTO create(LoanRequestDTO request, UUID ownerId) {
        var book = bookRepository.findByIdAndOwnerId(request.bookId(), ownerId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado o no pertenece al usuario"));

        if (book.getIsLent()) {
            throw new RuntimeException("El libro ya está prestado");
        }

        var loan = loanMapper.toEntity(request);
        loan = loanRepository.save(loan);

        book.setIsLent(true);
        bookRepository.save(book);

        return loanMapper.toResponseDTO(loan);
    }

    @Override
    public List<LoanResponseDTO> findAllByOwnerId(UUID ownerId) {
        var loans = loanRepository.findAllByOwnerId(ownerId);
        return loans.stream()
                .map(loanMapper::toResponseDTO)
                .toList();
    }

    @Override
    public LoanResponseDTO findById(Long id, UUID ownerId) {

        var loan = loanRepository.findByIdAndOwnerId(id, ownerId)

                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado o no pertenece al usuario"));
        return loanMapper.toResponseDTO(loan);
    }

    @Override
    @Transactional
    public LoanResponseDTO update(Long id, LoanRequestDTO request, UUID ownerId) {
        var loan = loanRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado o no pertenece al usuario"));

        loanMapper.updateEntityFromDTO(request, loan);
        loan = loanRepository.save(loan);

        return loanMapper.toResponseDTO(loan);
    }

    @Override
    @Transactional
    public void delete(Long id, UUID ownerId) {
        var loan = loanRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado o no pertenece al usuario"));

        var book = bookRepository.findById(loan.getBookId())
                .orElseThrow(() -> new RuntimeException("Libro asociado no encontrado"));

        loanRepository.deleteById(id);

        book.setIsLent(false);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public LoanResponseDTO markAsReturned(Long id, UUID ownerId) {
        var loan = loanRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado o no pertenece al usuario"));

        loan.setReturned(true);
        loan = loanRepository.save(loan);

        var book = bookRepository.findById(loan.getBookId())
                .orElseThrow(() -> new RuntimeException("Libro asociado no encontrado"));

        book.setIsLent(false);
        bookRepository.save(book);

        return loanMapper.toResponseDTO(loan);
    }
}
