package com.agent.activity.pojo;

import lombok.Data;
import com.oracle.webservices.internal.api.databinding.DatabindingMode;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="route_food")
public class RouteFood {
    @Id
    private Integer route_id;
    @Id
    private Integer food_id;
}
