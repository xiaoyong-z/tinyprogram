package com.agent.activity.pojo;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="route_flight")
public class RouteFlight {
    @Id
    private Integer route_id;
    @Id
    private String flight_id;
    private Boolean go_back;
}
