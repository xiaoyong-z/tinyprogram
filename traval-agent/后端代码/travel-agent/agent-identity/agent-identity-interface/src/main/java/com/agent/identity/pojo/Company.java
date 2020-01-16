package com.agent.identity.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Table(name="company")
public class Company {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer company_id;
    private String name;
    private String phone_number;
    private String address;
}

