package com.example.tasktravel.service.client;

import com.example.tasktravel.model.LocStation;
import com.example.tasktravel.model.LocationResponse;
import com.example.tasktravel.model.StopStations;
import com.example.tasktravel.model.TravelArraignment;
import com.example.tasktravel.service.client.model.Checkpoint;
import com.example.tasktravel.service.client.model.Connections;
import com.example.tasktravel.service.client.model.Sections;
import com.example.tasktravel.service.client.model.SwissApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class LocationResponseTranslator
{

    public LocationResponse transformToLocation( final SwissApiResponse swissApiResponse )
    {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setTravelArraignmentList( transformPossibleArraignments( swissApiResponse.getConnections() ) );
        return locationResponse;
    }


    private List<TravelArraignment> transformPossibleArraignments( final List<Connections> connections )
    {
        return connections.stream()
                          .map( con -> mapConnection( con ) )
                          .collect( Collectors.toList() );
    }


    private TravelArraignment mapConnection( final Connections con )
    {
        return TravelArraignment.builder().arrivalStation( transformCheckpoints( con.getFrom() ) )
                                .destinationStation( transformCheckpoints( con.getTo() ) )
                                .duration( con.getDuration() )
                                .platform( con.getFrom().getPlatform() )
                                .stopStations( transformStopStatuses( con.getSections().get( 0 ) ) ).build();
    }


    private List<StopStations> transformStopStatuses( final Sections sections )
    {
        List<StopStations> stopStations = new ArrayList<>();
        for ( Checkpoint checkpoint : sections.getJourney().getPassList() )
        {
            stopStations.add( addStopStation( checkpoint ) );
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
        return LocStation.builder().id( from.getStation().getId() )
                         .name( from.getStation().getName() )
                         .arrival( from.getArrival() )
                         .departure( from.getDeparture() )
                         .build();
    }
}
