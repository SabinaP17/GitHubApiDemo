package com.example.githubapidemo.mapper;

import com.example.githubapidemo.model.Employee;
import com.example.githubapidemo.model.GitHubRepository;
import com.example.githubapidemo.model.ProgrammingLanguage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Component class which maps the JSON response from the GitHub API to the entities
 */
@Component
public class GitHubDataMapper {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    @Autowired
    public GitHubDataMapper(ObjectMapper objectMapper, RestTemplate restTemplate){
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public List<Employee> mapMembersResponseToEmployees(String membersJson) throws IOException {
        JsonNode rootNode = objectMapper.readTree(membersJson);
        List<Employee> employees = new ArrayList<>();

        // Parse JSON response and map it to Employee entities
        for (JsonNode node : rootNode) {
            Employee employee = new Employee();
            employee.setEmployeeId(node.get("id").asLong());

            // Fetch additional information from URL
            String userDetailsUrl = node.get("url").asText();
            JsonNode userDetailsNode = fetchUserDetails(userDetailsUrl);
            employee.setName(userDetailsNode.get("name").asText());
            employee.setLocation(userDetailsNode.get("location").asText());

            // Fetch information about the user repositories
            String userRepositoriesUrl = userDetailsNode.get("repos_url").asText();
            List<GitHubRepository> userRepositories = fetchRepositories(userRepositoriesUrl);
            employee.setUserGitHubRepositories(userRepositories);
            employee.setUserProgrammingLanguages(fetchUserProgrammingLanguages(employee.getUserGitHubRepositories()));

            employees.add(employee);
        }
        return employees;
    }

    private JsonNode fetchUserDetails(String userDetailsUrl) throws JsonProcessingException {
        String userDetailsJson = restTemplate.getForObject(userDetailsUrl, String.class);
        return objectMapper.readTree(userDetailsJson);
    }

    private List<GitHubRepository> fetchRepositories(String repositoriesUrl) throws JsonProcessingException {
        String repositoriesJson = restTemplate.getForObject(repositoriesUrl, String.class);
        List<GitHubRepository> repositories = new ArrayList<>();
        JsonNode repositoriesNode = objectMapper.readTree(repositoriesJson);

        for (JsonNode repositoryNode : repositoriesNode) {
            GitHubRepository repository = new GitHubRepository();
            repository.setRepositoryId(repositoryNode.get("id").asLong());
            repository.setName(repositoryNode.get("name").asText());

            // Fetch programming languages for the repository
            String languagesUrl = repositoryNode.get("languages_url").asText();
            List<ProgrammingLanguage> languages = fetchProgrammingLanguages(languagesUrl);
            repository.setRepoProgrammingLanguages(languages);

            repositories.add(repository);
        }
        return repositories;
    }

    private List<ProgrammingLanguage> fetchProgrammingLanguages(String languagesUrl) throws JsonProcessingException {
        String languagesJson = restTemplate.getForObject(languagesUrl, String.class);
        JsonNode languagesNode = objectMapper.readTree(languagesJson);

        List<ProgrammingLanguage> languages = new ArrayList<>();
        languagesNode.fieldNames().forEachRemaining(language -> {
            ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
            programmingLanguage.setName(language);
            languages.add(programmingLanguage);
        });
        return languages;
    }

    private List<ProgrammingLanguage> fetchUserProgrammingLanguages(List<GitHubRepository> userGitHubRepositories) {
        Set<ProgrammingLanguage> userProgrammingLanguages = new HashSet<>();
        for(GitHubRepository gitHubRepository : userGitHubRepositories){
            List<ProgrammingLanguage> repoLanguages = gitHubRepository.getRepoProgrammingLanguages();
            userProgrammingLanguages.addAll(repoLanguages);
        }
        return new ArrayList<>(userProgrammingLanguages);
    }
}
