package com.example.dbspring.repo;

import com.example.dbspring.entity.MainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MainRepo extends JpaRepository<MainEntity, Long> {
}
