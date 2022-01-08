package org.damian.sak.task.allegrorecruitmenttask.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.RepositoriesNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.exception.custom_exception.UserNotFoundException;
import org.damian.sak.task.allegrorecruitmenttask.model.Repository;
import org.damian.sak.task.allegrorecruitmenttask.validators.JsonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class GithubSiteHelper {

    private static final String ADDRESS_PATH_BEGINNING = "https://api.github.com/users/";
    private static final String ADDRESS_PATH_ENDING = "/repos";
    private CloseableHttpClient httpClient;
    private JsonValidator jsonValidator;

    @Autowired
    public GithubSiteHelper(JsonValidator jsonValidator) {
        this.jsonValidator = jsonValidator;
        this.httpClient = HttpClients.createDefault();
    }

    private String createURLAddress(String userName) {
        return ADDRESS_PATH_BEGINNING + userName + ADDRESS_PATH_ENDING;
    }

    public List<Repository> findAllRepositories(String userName) throws IOException,
            UserNotFoundException, RepositoriesNotFoundException {

        HttpGet getRequest = new HttpGet(createURLAddress(userName));
        getRequest.addHeader("Content-Type", "application/json");

        CloseableHttpResponse response = httpClient.execute(getRequest);

        try (response) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 404) {
                throw new UserNotFoundException("GitHub user not found on the server ");
            }
            HttpEntity httpEntity = response.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);
            if (jsonValidator.isJsonEmpty(jsonString)) {
                throw new RepositoriesNotFoundException("GitHub user found but doesn't have any public repository");
            }
            return mapJsonToRepositoryList(jsonString);
        }
    }

    private List<Repository> mapJsonToRepositoryList(String jsonString){
        Gson gson = new Gson();
        Type repositoryListType = new TypeToken<ArrayList<Repository>>() {
        }.getType();
        return gson.fromJson(jsonString, repositoryListType);
    }

}
