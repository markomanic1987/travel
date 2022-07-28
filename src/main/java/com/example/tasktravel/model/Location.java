package com.example.tasktravel.model;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class Location
{
    private String from;

    private String to;

}
