package org.example.tobot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.tobot.entity.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
}