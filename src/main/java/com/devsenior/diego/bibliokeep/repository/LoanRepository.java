package com.devsenior.diego.bibliokeep.repository;

import com.devsenior.diego.bibliokeep.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("""
        SELECT l FROM Loan l
        JOIN Book b ON l.bookId = b.id
        WHERE b.ownerId = :ownerId
        ORDER BY l.loanDate DESC
        """)
    List<Loan> findAllByOwnerId(@Param("ownerId") UUID ownerId);

    @Query("""
        SELECT l FROM Loan l
        JOIN Book b ON l.bookId = b.id
        WHERE b.ownerId = :ownerId AND l.id = :loanId
        """)
    Optional<Loan> findByIdAndOwnerId(@Param("loanId") Long loanId, @Param("ownerId") UUID ownerId);

    @Query("""
        SELECT l FROM Loan l
        JOIN Book b ON l.bookId = b.id
        WHERE l.dueDate < :today AND l.returned = false
        """)
    List<Loan> findOverdueLoans(@Param("today") LocalDate today);
}
