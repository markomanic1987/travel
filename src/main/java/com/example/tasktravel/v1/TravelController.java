package com.example.tasktravel.v1;

import com.example.tasktravel.model.Location;
import com.example.tasktravel.model.LocationResponse;
import com.example.tasktravel.model.SwisApiResponseError;
import com.example.tasktravel.service.client.SwissApiCLient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class TravelController
{
    @Autowired
  private SwissApiCLient cLient;

    @RequestMapping( value ="/v1/locations",
                     method= RequestMethod.POST ,
                     consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationResponse> findLocationToTravel( @RequestBody final Location location )
          throws Throwable

    {
        LocationResponse locationResponse;
        try
        {
            locationResponse = cLient.findLocation( location );
        }
        catch ( Throwable e )
        {
            return new ResponseEntity<>( LocationResponse.builder().error( new SwisApiResponseError(e.getMessage()) ).build(), HttpStatus.BAD_REQUEST );
        }


        return new ResponseEntity<>( locationResponse, HttpStatus.ACCEPTED );
    }
}
