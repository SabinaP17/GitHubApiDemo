package com.example.githubapidemo.repository;

import com.example.githubapidemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByUserProgrammingLanguagesName(String programmingLanguage);
}
