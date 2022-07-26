package com.example.tasktravel.service.client.model;

import lombok.Data;

import java.util.List;

@Data
public class Journey
{
    private String name;
    private String category;
    private int number;
    private String to;
    private List<Checkpoint> passList;

}
