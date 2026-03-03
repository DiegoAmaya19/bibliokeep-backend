package com.devsenior.diego.bibliokeep.repository;

import com.devsenior.diego.bibliokeep.model.BookStatus;
import com.devsenior.diego.bibliokeep.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByOwnerId(UUID ownerId);

    Optional<Book> findByIdAndOwnerId(Long id, UUID ownerId);

    @Query("""
        SELECT COUNT(b) FROM Book b
        WHERE b.ownerId = :ownerId AND b.status = :status
        """)
    Long countByOwnerIdAndStatus(@Param("ownerId") UUID ownerId, @Param("status") BookStatus status);
}
