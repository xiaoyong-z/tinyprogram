package com.agent.service.pojo;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table
public class Register {
    @Id
    private Integer traveller_id;
    @Id
    private Integer activity_id;
    @Id
    private Timestamp register_date;
}
