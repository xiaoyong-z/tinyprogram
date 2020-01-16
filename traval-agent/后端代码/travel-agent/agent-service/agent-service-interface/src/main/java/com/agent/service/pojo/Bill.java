package com.agent.service.pojo;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table(name="bill")
public class Bill {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer bill_id;
    private Integer traveller_id;
    private Integer activity_id;
    private Integer salesman_id;
    private Timestamp pay_date;
    private double pay_money;
}
