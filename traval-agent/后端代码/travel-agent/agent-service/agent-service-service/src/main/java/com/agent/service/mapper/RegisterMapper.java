package com.agent.service.mapper;

import com.agent.service.pojo.ActivityDetail;
import com.agent.service.pojo.Register;
import com.agent.service.pojo.RegisterDetail;
import com.agent.service.pojo.TravellerDetail;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RegisterMapper extends Mapper<Register> {
    @Select("select * from register")
    List<Register> getAllRegister();

    @Insert("insert into register(traveller_id, activity_id, register_date)" +
            "values(#{register.traveller_id}, #{register.activity_id}, #{register.register_date})")
    void insertRegister(@Param("register")  Register register);

    @Select("select name, id_number, gender " +
            "from traveller " +
            "where traveller_id = #{traveller_id}")
    TravellerDetail getTravellerDetail(@Param("traveller_id") Integer traveller_id);

    @Select("select name, description " +
            "from activity " +
            "where activity_id = #{activity_id}")
    ActivityDetail getActivityDetail(@Param("activity_id") Integer activity_id);
}
