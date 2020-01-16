package com.agent.activity.mapper;

import com.agent.activity.pojo.Activity;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ActivityMapper extends Mapper<Activity> {
    @Select("select * from activity")
    List<Activity> getAllActivity();

    @Select("select * from activity where activity_id = #{activity_id}")
    Activity getSpecificActivity(Integer activity_id);

    @Delete("delete from activity where activity_id = #{activity_id}")
    void deleteActivity(@Param("activity_id") Integer activity_id);

    @Insert("insert into activity(name, adult_price, child_price, description, route_id, company_id, days, start_time, end_time)" +
            "values(#{activity.name}, #{activity.adult_price}, #{activity.child_price}" +
            ", #{activity.description}, #{activity.route_id}, #{activity.company_id}," +
            "#{activity.days}, #{activity.start_time}, #{activity.end_time})")
    void insertActivity(@Param("activity")  Activity activity);

    @Update("update activity set name = #{activity.name}" +
            ",adult_price = #{activity.adult_price}, child_price = #{activity.child_price}" +
            ",description = #{activity.description}, route_id = #{activity.route_id}" +
            ",company_id = #{activity.company_id}, days = #{activity.days}" +
            ",start_time = #{activity.start_time}, end_time = #{activity.end_time}" +
            " where activity_id = #{activity.activity_id}")
    void updateActivity(@Param("activity") Activity activity);
}
