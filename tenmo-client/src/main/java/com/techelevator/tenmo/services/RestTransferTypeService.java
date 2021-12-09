package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class RestTransferTypeService implements TransferTypeService {
    private final String API_BASE_URL = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();

    public RestTransferTypeService() {

    }

    @Override
    public TransferType getTransferTypeByTransferTypeId(AuthenticatedUser authenticatedUser, int transferTypeId) {
        TransferType transferType = null;
        String url = API_BASE_URL + "/transfertype/" + transferTypeId;

        try {
            transferType = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser), TransferType.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return transferType;
    }

    @Override
    public TransferType getTransferTypeFromDescription(AuthenticatedUser authenticatedUser, String desc) {
        TransferType transferType = null;
        String url = API_BASE_URL + "/transfertype/filter?description=" + desc;

        try {
            transferType = restTemplate.exchange(url, HttpMethod.GET, makeEntity(authenticatedUser), TransferType.class).getBody();
        } catch (RestClientResponseException e)  {
            System.out.println(e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println(e.getMessage());
        }
        return transferType;
    }

    private HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
