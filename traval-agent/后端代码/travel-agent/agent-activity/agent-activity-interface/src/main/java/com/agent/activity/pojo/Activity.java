package com.agent.activity.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table(name="activity")
public class Activity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer activity_id;
    private String name;
    private Double adult_price;
    private Double child_price;
    private String description;
    private Integer route_id;
    private Integer company_id;
    private Timestamp start_time;
    private Timestamp end_time;
    private Integer days;
}
