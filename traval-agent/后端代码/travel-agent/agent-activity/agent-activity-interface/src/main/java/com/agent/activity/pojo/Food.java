package com.agent.activity.pojo;

import com.sun.xml.internal.txw2.output.DumpSerializer;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="food")
public class Food {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer food_id;
    private String name;
    private String description;
}
