package com.example.tasktravel;

import com.example.tasktravel.v1.TravelController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class TaskTravelApplicationTests
{
    @Autowired
    private TravelController travelController;



    @Test
    void contextLoads()
    {
        assertThat( travelController ).isNotNull();
    }
}