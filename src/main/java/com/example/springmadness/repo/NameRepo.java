package com.example.springmadness.repo;

import com.example.springmadness.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameRepo extends JpaRepository<Name, Long> {
}
