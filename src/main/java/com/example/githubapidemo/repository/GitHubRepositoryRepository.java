package com.example.githubapidemo.repository;

import com.example.githubapidemo.model.GitHubRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitHubRepositoryRepository extends JpaRepository<GitHubRepository, Long> {
}
