package com.example.tasktravel.service.client.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Connections
{
    private Checkpoint from;

    private Checkpoint to;

    private String duration;

    private List<String> products;

    private List<Sections> sections;
}
