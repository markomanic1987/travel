package com.example.tasktravel.service.client.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Journey
{
    private String name;
    private String category;
    private int number;
    private String to;
    private List<Checkpoint> passList;

}
