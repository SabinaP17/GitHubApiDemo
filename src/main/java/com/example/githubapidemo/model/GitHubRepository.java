package com.example.githubapidemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing the GitHub repository of an organization employee
 */
@Entity
@Getter
@Setter
public class GitHubRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long repositoryId;
    private String name;

    @ManyToMany
    @JoinTable(name = "github_repository_programming_language",
            joinColumns = @JoinColumn(name = "github_repository_id"),
            inverseJoinColumns = @JoinColumn(name = "programming_language_id"))
    private List<ProgrammingLanguage> repoProgrammingLanguages;
}
