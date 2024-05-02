package com.example.githubapidemo.controller;

import com.example.githubapidemo.model.Employee;
import com.example.githubapidemo.repository.EmployeeRepository;
import com.example.githubapidemo.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class that handles employee HTTP requests and invokes EmployeeRepository and Service
 */

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final GitHubService gitHubService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(GitHubService gitHubService, EmployeeRepository employeeRepository) {

        this.gitHubService = gitHubService;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/programminglanguage/{language}")
    public ResponseEntity<List<Employee>> getEmployeesByProgrammingLanguage(@PathVariable("language") String programmingLanguage) {
        List<Employee> employeeList = gitHubService.findEmployeesByProgrammingLanguage(programmingLanguage);
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return ResponseEntity.ok(employeeList);
    }
}
