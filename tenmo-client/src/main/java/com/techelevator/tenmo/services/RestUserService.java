package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.apiguardian.api.API;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class RestUserService implements UserService {
    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public RestUserService(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL;
    }

    @Override
    public User[] getAllUsers(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = createHttpEntity(authenticatedUser);
        User[] users = null;

        try {
            users = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, entity, User[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        } return users;
    }

    @Override
    public User getUserByUserId(AuthenticatedUser authenticatedUser, int id) {
        HttpEntity entity = createHttpEntity(authenticatedUser);
        User user = null;

        try {
            user = restTemplate.exchange(API_BASE_URL + "/users" + id, HttpMethod.GET, entity, User.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    private HttpEntity createHttpEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        return entity;
    }

}
