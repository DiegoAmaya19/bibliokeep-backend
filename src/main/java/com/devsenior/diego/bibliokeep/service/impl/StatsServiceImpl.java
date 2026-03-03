package com.devsenior.diego.bibliokeep.service.impl;

import com.devsenior.diego.bibliokeep.model.BookStatus;
import com.devsenior.diego.bibliokeep.model.dto.response.DashboardResponseDTO;
import com.devsenior.diego.bibliokeep.repository.BookRepository;
import com.devsenior.diego.bibliokeep.repository.LoanRepository;
import com.devsenior.diego.bibliokeep.repository.UserRepository;
import com.devsenior.diego.bibliokeep.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardResponseDTO getDashboard(UUID ownerId) {
        var user = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var annualGoal = user.getAnnualGoal();
        var booksReadThisYear = bookRepository.countByOwnerIdAndStatus(ownerId, BookStatus.LEIDO);
        var progressPercentage = annualGoal > 0 
                ? (booksReadThisYear.doubleValue() / annualGoal.doubleValue()) * 100.0 
                : 0.0;
        var totalBooks = (long) bookRepository.findByOwnerId(ownerId).size();
        
        var allLoans = loanRepository.findAllByOwnerId(ownerId);
        var activeLoans = allLoans.stream()
                .filter(loan -> !loan.getReturned())
                .count();
        
        var today = LocalDate.now();
        var overdueLoans = loanRepository.findOverdueLoans(today).stream()
                .filter(loan -> {
                    var book = bookRepository.findById(loan.getBookId());
                    return book.isPresent() && book.get().getOwnerId().equals(ownerId);
                })
                .count();

        return new DashboardResponseDTO(
                annualGoal,
                booksReadThisYear,
                Math.min(progressPercentage, 100.0),
                totalBooks,
                activeLoans,
                overdueLoans
        );
    }
}
