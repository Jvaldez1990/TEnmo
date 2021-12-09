package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestTransferService implements TransferService {

    private final String API_BASE_URL = "http://localhost:8080";
    private RestTemplate restTemplate;

    public RestTransferService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Transfer[] getTransfersByUserId(AuthenticatedUser authenticatedUser, int userId) {
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(API_BASE_URL + "/transfers/user/" + userId,
                    HttpMethod.GET, createHttpEntity(authenticatedUser), Transfer[].class).getBody();
        } catch(RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return transfers;
    }

    @Override
    public Transfer getTransferByTransferId(AuthenticatedUser authenticatedUser, int transferId) {
        Transfer transfer = null;

        try {
            transfer = restTemplate.exchange(API_BASE_URL + "/transfers/" + transferId, HttpMethod.GET,
                    createHttpEntity(authenticatedUser), Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return transfer;
    }

    @Override
    public Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser) {
        Transfer[] transfers = null;

        try {
            transfers = restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.GET, createHttpEntity(authenticatedUser), Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return transfers;
    }

    @Override
    public Transfer[] getPendingTransfers(AuthenticatedUser authenticatedUser, int userId) {
        return null;
    }

    @Override
    public void createTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
        String url = API_BASE_URL + "/transfers/" + transfer.getTransferId();
        try {
            restTemplate.exchange(url, HttpMethod.POST, makeTransferEntity(authenticatedUser, transfer), Transfer.class);
        } catch (RestClientResponseException e) {
            System.out.println();
        }
    }

    @Override
    public void updateTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
        String url = API_BASE_URL + "/transfers/" + transfer.getTransferId();

        try {
            restTemplate.exchange(url, HttpMethod.PUT, makeTransferEntity(authenticatedUser, transfer), Transfer.class);
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private HttpEntity createHttpEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        return entity;
    }

    private HttpEntity<Transfer> makeTransferEntity (AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity(transfer, headers);
        return entity;
    }

}
