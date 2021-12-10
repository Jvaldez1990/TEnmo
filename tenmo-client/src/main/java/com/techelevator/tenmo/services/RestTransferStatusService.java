package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferStatus;
import org.apiguardian.api.API;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class RestTransferStatusService implements TransferStatusService {
    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public RestTransferStatusService(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL;
    }

    @Override
    public TransferStatus getTransferStatusById(AuthenticatedUser authenticatedUser, int transferStatusId) {
        TransferStatus transferStatus = null;
        String url = API_BASE_URL + "/transferstatus/" + transferStatusId;
        try {
            transferStatus = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser), TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatusByDesc(AuthenticatedUser authenticatedUser, String description) {
        TransferStatus transferStatus = null;
        String url = API_BASE_URL + "/transferstatus/filter?description=" + description;
        try {
            transferStatus = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser), TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return transferStatus;
    }

    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
