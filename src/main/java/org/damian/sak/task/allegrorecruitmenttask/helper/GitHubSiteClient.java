package org.damian.sak.task.allegrorecruitmenttask.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.messages.RepositoryMessageConst;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.damian.sak.task.allegrorecruitmenttask.service.messagesservice.MessagesService;
import org.damian.sak.task.allegrorecruitmenttask.validators.JsonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Http Client to perform requests to GitHub API.
 */
@Component
public class GitHubSiteClient {

    private static final String USERNAME_PLACEHOLDER = "<user>";
    private static final String ADDRESS_PATH_TEMPLATE = "https://api.github.com/users/" + USERNAME_PLACEHOLDER + "/repos";

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private CloseableHttpClient httpClient;
    private JsonValidator jsonValidator;
    private MessagesService messagesService;

    public GitHubSiteClient(JsonValidator jsonValidator, MessagesService messagesService) {
        this.jsonValidator = jsonValidator;
        this.httpClient = HttpClients.createDefault();
        this.messagesService = messagesService;
    }

    private String createURLAddress(String username) {
        return ADDRESS_PATH_TEMPLATE.replace(USERNAME_PLACEHOLDER, username);
    }

    /**
     * Method according to given argument is returning list of repositories from GitHub account. It is using
     * apache http client to communicate with outside server.
     *
     * @param username account username
     * @return map of programming languages with their ratings
     * @throws UserNotFoundException         if username doesn't exist
     * @throws RepositoriesNotFoundException if user doesn't have any public repositories
     * @throws IOException                   if an input or output exception occurred
     * @see HttpGet
     * @see CloseableHttpResponse
     */
    public List<Repository> findAllRepositories(String username) throws IOException,
            UserNotFoundException, RepositoriesNotFoundException {
        HttpGet getRequest = new HttpGet(createURLAddress(username));
        getRequest.addHeader("Content-Type", "application/json");

        LOG.info("Executing GET request to GitHub server for user: {}", username);

        try (CloseableHttpResponse response = httpClient.execute(getRequest)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                LOG.warn("GitHub user: {} doesn't exist on the server", username);
                throw new UserNotFoundException(messagesService.getMessage(RepositoryMessageConst.USER_NOT_FOUND_EX));
            }
            HttpEntity httpEntity = response.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);
            if (jsonValidator.isJsonEmpty(jsonString)) {
                LOG.warn("No public repositories found on: {} account", username);
                throw new RepositoriesNotFoundException(messagesService.getMessage(RepositoryMessageConst.REPOS_NOT_FOUND_EX));
            }

            return mapJsonToRepositoryList(jsonString);
        }
    }

    /**
     * Method according to given argument is creating list of Repository objects from data carried by JSON. Because
     * List cant't be directly created from JSON method is using class TypeToken for finding correct Type to understand
     * List structure correctly.
     */
    private List<Repository> mapJsonToRepositoryList(String jsonString) {
        Gson gson = new Gson();
        Type repositoryListType = new TypeToken<ArrayList<Repository>>() {
        }.getType();

        List<Repository> resultList = gson.fromJson(jsonString, repositoryListType);

        LOG.info("Created Repository List objects from Json - number of repositories: {}", resultList.size());

        return resultList;
    }
}
