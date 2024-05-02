package com.example.githubapidemo.controller;

import com.example.githubapidemo.service.GitHubAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that handles HTTP requests to the GitHubAPI through GitHubAPIService
 */
@RestController
@RequestMapping("/github")
public class GitHubController {

    private final GitHubAPIService gitHubAPIService;

    @Autowired
    public GitHubController(GitHubAPIService gitHubAPIService) {
        this.gitHubAPIService = gitHubAPIService;
    }

    @GetMapping("/fetch-members")
    public ResponseEntity<String> fetchOrganizationMembers() {
        try {
            gitHubAPIService.getOrganisationMembers("codecentric");
            return ResponseEntity.ok("Organization members fetched successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch organization members: " + e.getMessage());
        }
    }
}
