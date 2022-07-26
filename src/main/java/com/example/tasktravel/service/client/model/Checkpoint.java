package com.example.tasktravel.service.client.model;

import lombok.Data;


@Data
public class Checkpoint
{
    private String arrival;

    private String departure;

    private String platform;

    private Station station;

}
