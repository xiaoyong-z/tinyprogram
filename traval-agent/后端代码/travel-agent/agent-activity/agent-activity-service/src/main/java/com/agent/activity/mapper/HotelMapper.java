package com.agent.activity.mapper;

import com.agent.activity.pojo.Hotel;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HotelMapper extends Mapper<Hotel> {
    @Select("select * from hotel")
    List<Hotel> getAllHotel();

    @Select("select * from hotel where hotel_id = #{hotel_id}")
    Hotel getSpecificHotel(Integer hotel_id);

    @Insert("insert into hotel(name, address, phone_number, description)" +
            "values(#{hotel.name}, #{hotel.address}" +
            ", #{hotel.phone_number}, #{hotel.description})")
    void insertHotel(@Param("hotel")  Hotel hotel);

    @Delete("delete from hotel where hotel_id = #{hotel_id}")
    void deleteHotel(@Param("hotel_id") Integer hotel_id);

    @Update("update hotel set name = #{hotel.name}" +
            ",address = #{hotel.address}, phone_number = #{hotel.phone_number}" +
            ",description= #{hotel.description}" +
            "where hotel_id = #{hotel.hotel_id}")
    void updateHotel(@Param("hotel") Hotel hotel);
}
