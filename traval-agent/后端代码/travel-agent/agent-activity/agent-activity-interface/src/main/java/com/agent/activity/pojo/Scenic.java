package com.agent.activity.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="scenic")
public class Scenic {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer scenic_id;
    private String name;
    private String description;
}
