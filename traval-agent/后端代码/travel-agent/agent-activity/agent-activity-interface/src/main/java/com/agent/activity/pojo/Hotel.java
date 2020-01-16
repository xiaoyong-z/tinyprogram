package com.agent.activity.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "hotel")
public class Hotel {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer hotel_id;
    private String name;
    private String address;
    private String phone_number;
    private String description;
}
