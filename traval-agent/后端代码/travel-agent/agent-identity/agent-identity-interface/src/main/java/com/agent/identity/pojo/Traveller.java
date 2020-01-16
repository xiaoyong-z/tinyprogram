package com.agent.identity.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="traveller")
public class Traveller {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer traveller_id;
    private String id_number;
    private String name;
    private String phone_number;
    private String address;
    private Integer age;
    private Boolean gender;
    private String email;
}
