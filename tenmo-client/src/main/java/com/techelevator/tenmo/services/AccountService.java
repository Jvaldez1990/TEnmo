package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    private RestTemplate restTemplate = new RestTemplate();
    public static final String API_BASE_URL = "http://localhost:8080";

    public Account getAccount() {
        return restTemplate.getForObject(API_BASE_URL, Account.class);
    }


}
