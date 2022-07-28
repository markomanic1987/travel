package com.example.tasktravel.client;

import com.example.tasktravel.model.LocationResponse;
import com.example.tasktravel.service.client.LocationResponseTranslator;
import com.example.tasktravel.service.client.model.Checkpoint;
import com.example.tasktravel.service.client.model.Connections;
import com.example.tasktravel.service.client.model.Journey;
import com.example.tasktravel.service.client.model.Sections;
import com.example.tasktravel.service.client.model.Station;
import com.example.tasktravel.service.client.model.SwissApiResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;



public class TestLocationTraslator
{

    private LocationResponseTranslator translator;


    @Before
    public void setUp()
    {
        translator = new LocationResponseTranslator();
    }


    @Test
    public void testtranslator()
    {
        SwissApiResponse response = SwissApiResponse.builder()
                                                    .connections( new ArrayList<>() )
                                                    .build();
        Connections connections = Connections.builder()
                                             .sections( new ArrayList<>() )
                                             .build();
        Checkpoint from = new Checkpoint();
        Checkpoint to = new Checkpoint();
        Sections sections = new Sections();
        connections.setDuration( "32" );
        from.setDeparture( "11-11-11" );
        from.setPlatform( "A12" );
        Station station = new Station();
        station.setId( "12345" );
        station.setName( "nameTest" );
        from.setStation( station );
        to.setStation( station );
        to.setArrival( "12-12-12" );
        Journey journey = Journey.builder()
                                 .passList( new ArrayList<>() )
                                 .build();

        journey.getPassList().add( to );
        journey.getPassList().add( from );
        sections.setJourney( journey );
        connections.getSections().add( sections );
        connections.setFrom( from );
        connections.setTo( to );
        response.getConnections().add( connections );
        LocationResponse locationResponse = translator.transformToLocation( response );

        assertEquals( locationResponse.getTravelArraignmentList().get( 0 ).getDuration(), response.getConnections().get( 0 ).getDuration() );
        assertEquals( locationResponse.getTravelArraignmentList().get( 0 ).getPlatform(), response.getConnections().get( 0 ).getFrom().getPlatform() );
        assertEquals( locationResponse.getTravelArraignmentList().get( 0 ).getArrivalStation().getName(), response.getConnections().get( 0 ).getFrom().getStation().getName() );
        assertEquals( locationResponse.getTravelArraignmentList().get( 0 ).getArrivalStation().getId(), response.getConnections().get( 0 ).getFrom().getStation().getId() );
        assertEquals( locationResponse.getTravelArraignmentList().get( 0 ).getDestinationStation().getName(), response.getConnections().get( 0 ).getFrom().getStation().getName() );
        assertEquals( locationResponse.getTravelArraignmentList().get( 0 ).getDestinationStation().getId(), response.getConnections().get( 0 ).getFrom().getStation().getId() );
        assertEquals( locationResponse.getTravelArraignmentList().get( 0 ).getStopStations().get( 0 ).getId(), response.getConnections().get( 0 ).getSections().get( 0 ).getJourney().getPassList().get( 0 ).getStation().getId() );
        assertEquals( locationResponse.getTravelArraignmentList().get( 0 ).getStopStations().get( 0 ).getName(), response.getConnections().get( 0 ).getSections().get( 0 ).getJourney().getPassList().get( 0 ).getStation().getName() );

    }
}
