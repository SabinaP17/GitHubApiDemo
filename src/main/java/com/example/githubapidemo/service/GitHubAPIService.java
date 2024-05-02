package com.example.githubapidemo.service;

import com.example.githubapidemo.mapper.GitHubDataMapper;
import com.example.githubapidemo.model.Employee;
import com.example.githubapidemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Java service class that makes HTTP requests to the GitHub API to retrieve information
 * about the members of the organization
 */
@Service
public class GitHubAPIService {
    private final RestTemplate restTemplate;
    private final GitHubDataMapper gitHubDataMapper;
    private final EmployeeRepository employeeRepository;
    @Value("${github.api.url}")
    private String githubApiUrl;
    @Autowired
    public GitHubAPIService(RestTemplate restTemplate, GitHubDataMapper gitHubDataMapper, EmployeeRepository employeeRepository) {
        this.restTemplate = restTemplate;
        this.gitHubDataMapper = gitHubDataMapper;
        this.employeeRepository = employeeRepository;
    }
    public void getOrganisationMembers(String organization) throws IOException {
        // Makes GET request to GitHub API to retrieve members of an organization
        String membersJson = restTemplate.getForObject(githubApiUrl + "/orgs/{organization}/members", String.class, organization);
        List<Employee> employees = gitHubDataMapper.mapMembersResponseToEmployees(membersJson);

        // Save the list of employees into the database
        employeeRepository.saveAll(employees);
    }
}
