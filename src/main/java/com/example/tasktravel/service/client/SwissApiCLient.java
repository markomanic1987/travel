package com.example.tasktravel.service.client;


import com.example.tasktravel.model.LocStation;
import com.example.tasktravel.model.Location;
import com.example.tasktravel.model.LocationResponse;
import com.example.tasktravel.model.StopStations;
import com.example.tasktravel.model.TravelArraingmens;
import com.example.tasktravel.service.client.model.Checkpoint;
import com.example.tasktravel.service.client.model.Connections;
import com.example.tasktravel.service.client.model.Sections;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class SwissApiCLient
{

    private static final String BASE_URL = "http://transport.opendata.ch/v1/connections";
    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;


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
            throw new Throwable( "There is no connection between towns try new towns");
        }
        LocationResponse locationResponse = transformTolocation(swissApiResponse);
        return locationResponse;
    }


    private LocationResponse transformTolocation( final SwissApiResponse swissApiResponse )
    {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setTravelArraingmensList( trasformPosibleArraigments(swissApiResponse.getConnections()) );
        return locationResponse;
    }


    private List<TravelArraingmens> trasformPosibleArraigments( final List<Connections> connections )
    {
        return connections.stream()
              .map( con -> mapConnection(con)  )
              .collect( Collectors.toList() );
    }


    private TravelArraingmens mapConnection( final Connections con )
    {
      return TravelArraingmens.builder().arivalStation( transformCheckpoints(con.getFrom()) )
              .destinationStation( transformCheckpoints(con.getTo()) )
              .duration( con.getDuration() )
              .platform( con.getFrom().getPlatform() )
              .stopStations( transforStopStatuses(con.getSections().get( 0 )) ).build();

    }


    private List<StopStations> transforStopStatuses( final Sections sections )
    {
        List<StopStations> stopStations = new ArrayList<>();
        for (Checkpoint checkpoint:sections.getJourney().getPassList() ){
            stopStations.add( addStopStation(checkpoint) );
        }
        return stopStations;
    }


    private StopStations addStopStation( final Checkpoint checkpoint )
    {
        return StopStations.builder().arrival( checkpoint.getArrival() )
                           .departure( checkpoint.getDeparture() )
                           .id( checkpoint.getStation().getId() )
                           .name( checkpoint.getStation().getName() ).build();
    }


    private LocStation transformCheckpoints( final Checkpoint from )
    {
      return   LocStation.builder().id( from.getStation().getId() )
              .name( from.getStation().getName() )
              .build();
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
