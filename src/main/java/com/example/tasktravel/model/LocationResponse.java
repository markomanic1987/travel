package com.example.tasktravel.model;

import com.example.tasktravel.service.client.model.SwissApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse
{
    private List<TravelArraingmens> travelArraingmensList;

    private SwisApiResponseError error;
}
