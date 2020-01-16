package com.agent.activity.pojo;

import lombok.Data;
import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="route")
public class Route {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer route_id;
    private String name;
    private String start_address;
    private String end_address;
}
