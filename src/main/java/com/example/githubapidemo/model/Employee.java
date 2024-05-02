package com.example.githubapidemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing the organization employee
 */

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long employeeId;
    private String name;
    private String location;

    @ManyToMany
    @JoinTable(name = "employee_github_repository",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "github_repository_id"))
    private List<GitHubRepository> userGitHubRepositories;

    @ManyToMany
    @JoinTable(name = "employee_programming_language",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "programming_language_id"))
    private List<ProgrammingLanguage> userProgrammingLanguages;
}
