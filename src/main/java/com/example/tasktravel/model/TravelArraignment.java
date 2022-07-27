package com.example.tasktravel.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TravelArraignment
{
 private LocStation arrivalStation;
 private LocStation destinationStation;
 private String duration;
 private String platform;
 private List<StopStations> stopStations;

}
