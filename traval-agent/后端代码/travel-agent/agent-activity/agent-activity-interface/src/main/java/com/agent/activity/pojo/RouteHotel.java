package com.agent.activity.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="route_hotel")
public class RouteHotel {
    @Id
    private Integer route_id;
    @Id
    private Integer hotel_id;
}
