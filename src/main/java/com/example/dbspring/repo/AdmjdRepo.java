package com.example.dbspring.repo;

import com.example.dbspring.entity.Admjd2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmjdRepo extends JpaRepository<Admjd2, String> {

}
