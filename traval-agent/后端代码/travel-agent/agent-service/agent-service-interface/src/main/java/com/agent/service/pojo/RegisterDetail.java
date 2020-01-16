package com.agent.service.pojo;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class RegisterDetail {
    private String traveller_name;
    private String traveller_id_number;
    private Boolean gender;
    private String activity_name;
    private String activity_description;
}
