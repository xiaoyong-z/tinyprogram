package com.agent.activity.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table(name="flight")
public class Flight {
    @Id
    private String flight_id;
    private String flight_company;
    private Timestamp start_time;
    private Timestamp end_time;
    private String start_address;
    private String end_address;
}
