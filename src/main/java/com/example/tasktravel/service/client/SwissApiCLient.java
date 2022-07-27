package com.example.tasktravel.service.client;


import com.example.tasktravel.model.Location;
import com.example.tasktravel.model.LocationResponse;
import com.example.tasktravel.service.client.model.SwissApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;


@Component
public class SwissApiCLient
{

    private static final String BASE_URL = "http://transport.opendata.ch/v1/connections";
    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LocationResponseTranslator locationResponseTranslator;


    public SwissApiCLient( final RestTemplate restTemplate )
    {
        this.restTemplate = restTemplate;
    }



    public LocationResponse findLocation( final Location location )
          throws Throwable
    {
        final HttpHeaders headers = new HttpHeaders();
        headers.add( "Content-Type", MediaType.APPLICATION_JSON_VALUE );
        final HttpEntity httpEntity = new HttpEntity<>( headers );
        MultiValueMap<String, String> queryParams = convert( location );
        SwissApiResponse swissApiResponse= exchange( null, queryParams, httpEntity, new ParameterizedTypeReference<SwissApiResponse>() {} )
              .orElse( null );
        if(swissApiResponse.getConnections().isEmpty()){
            throw new Throwable( "There is no connection between towns, try new towns");
        }
        LocationResponse locationResponse = locationResponseTranslator.transformToLocation(swissApiResponse);
        return locationResponse;
    }

    private <T> Optional<T> exchange( final String url,
                                      final MultiValueMap<String, String> queryParams,
                                      final HttpEntity<?> httpEntity,
                                      final ParameterizedTypeReference<T> responseTypeReference )
          throws Exception
    {
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl( BASE_URL );
        uriComponentsBuilder.path( url );
        if ( MapUtils.isNotEmpty( queryParams ) )
        {
            uriComponentsBuilder.queryParams( queryParams );
        }
        final URI uri = uriComponentsBuilder.build().toUri();

        ResponseEntity<T> responseEntity = null;
        try
        {
            responseEntity = restTemplate.exchange( uri,
                                                    HttpMethod.GET,
                                                    httpEntity,
                                                    responseTypeReference );
        }
        catch ( final HttpStatusCodeException e )
        {
            switch ( e.getStatusCode() )
            {
                case NOT_FOUND:
                    break;
                default:
                    throw new Exception( e.getMessage() );
            }
        }
        return Optional.ofNullable( responseEntity )
                       .map( ResponseEntity::getBody );
    }
    private MultiValueMap<String,String> convert (Location location){
        MultiValueMap parameters = new LinkedMultiValueMap<String, String>();
        Map<String, String> maps = objectMapper.convertValue( location, new TypeReference<Map<String, String>>() {});
        parameters.setAll(maps);
        return parameters;
    }
}
