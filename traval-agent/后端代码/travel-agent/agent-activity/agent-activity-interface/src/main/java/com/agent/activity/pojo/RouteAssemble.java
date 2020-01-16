package com.agent.activity.pojo;

import lombok.Data;
import java.util.List;

@Data
public class RouteAssemble {
    Route base_route;
    List<Flight> route_flight;
    List<Food>  route_food;
    List<Hotel> route_hotel;
    List<Scenic> route_scenic;
    String start_flight_id;
    String end_flight_id;
}
