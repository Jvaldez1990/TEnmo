package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class RestAccountService implements AccountService {
    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public RestAccountService(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL;
    }


    @Override
    public BigDecimal getBalance(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = createHttpEntity(authenticatedUser);
        BigDecimal balance = new BigDecimal(1);

        try {
            balance = restTemplate.exchange(API_BASE_URL + "/balance", HttpMethod.GET, entity, BigDecimal.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }

    @Override
    public Account getAccountByUserId(AuthenticatedUser authenticatedUser, int userId) {
        Account account = null;
        try {
            account = restTemplate.exchange(API_BASE_URL + "account/user/" + userId, HttpMethod.GET, createHttpEntity(authenticatedUser), Account.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return account;
    }

    @Override
    public Account getAccountById(AuthenticatedUser authenticatedUser, int accountId) {
        Account account = null;
        try {
            account = restTemplate.exchange(API_BASE_URL + "account/" + accountId, HttpMethod.GET, createHttpEntity(authenticatedUser), Account.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return account;
    }

    private HttpEntity createHttpEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        return entity;
    }

}
