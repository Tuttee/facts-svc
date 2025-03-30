package com.app.repository;

import com.app.model.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FactRepository extends JpaRepository<Fact, UUID> {
    Optional<Fact> findByContent(String content);
}
