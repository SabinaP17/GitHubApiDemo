package com.example.githubapidemo.service;

import com.example.githubapidemo.model.Employee;
import com.example.githubapidemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Java service class that implements business logic methods for the employee
 */

@Service
public class GitHubService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public GitHubService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findEmployeesByProgrammingLanguage(String programmingLanguage) {
        return employeeRepository.findByUserProgrammingLanguagesName(programmingLanguage);
    }
}
