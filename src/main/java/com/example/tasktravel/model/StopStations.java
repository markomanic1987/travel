package com.example.tasktravel.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StopStations
{
    private String id;
    private String name;
    private String arrival;
    private String departure;
}
