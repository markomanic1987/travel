package com.example.tasktravel.service.client.model;

import lombok.Data;


@Data
public class Sections
{
    private Journey journey;
    private String walk;
    private Checkpoint departure;
    private Checkpoint arrival;
}
