package com.agent.activity.mapper;

import com.agent.activity.pojo.Flight;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FlightMapper extends Mapper<Flight> {
    @Select("select * from flight")
    List<Flight> getAllFlight();

    @Select("select * from flight where flight_id = #{flight_id}")
    Flight getSpecificFlight(String flight_id);

    @Insert("insert into flight(flight_id, flight_company, start_time, end_time, start_address, end_address)" +
            "values(#{flight.flight_id}, #{flight.flight_company}, #{flight.start_time}" +
            ", #{flight.end_time}, #{flight.start_address}, #{flight.end_address})")
    void insertFlight(@Param("flight")  Flight flight);

    @Delete("delete from flight where flight_id = #{flight_id}")
    void deleteFlight(@Param("flight_id") String flight_id);

    @Update("update flight set flight_company = #{flight.flight_company}" +
            ",start_time = #{flight.start_time}, end_time = #{flight.end_time}" +
            ",start_address = #{flight.start_address}, end_address = #{flight.end_address}" +
            "where flight_id = #{flight.flight_id}")
    void updateFlight(@Param("flight") Flight flight);
}
