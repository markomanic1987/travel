package com.example.tasktravel.client;

import com.example.tasktravel.model.Location;
import com.example.tasktravel.model.LocationResponse;
import com.example.tasktravel.service.client.SwissApiCLient;
import com.example.tasktravel.service.client.model.SwissApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;



@RunWith( MockitoJUnitRunner.class )
public class SwissApiClientTest
{


    @Test
    public void findLocation()
    {
        final SwissApiResponse swissApiResponse = new SwissApiResponse();


        final ResponseEntity<SwissApiResponse> response = new ResponseEntity<>( swissApiResponse, HttpStatus.OK );

        assertEquals( response.getBody(), swissApiResponse );
        assertEquals( response.getBody().getConnections(), swissApiResponse.getConnections() );

    }

}

