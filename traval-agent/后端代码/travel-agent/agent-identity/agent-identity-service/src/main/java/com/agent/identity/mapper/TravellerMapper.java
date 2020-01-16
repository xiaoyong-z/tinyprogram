package com.agent.identity.mapper;

import com.agent.identity.pojo.Traveller;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TravellerMapper extends Mapper<Traveller> {
    @Select("select * from traveller")
    List<Traveller> getAllTraveller();

    @Insert("insert into traveller(id_number, name, phone_number, address, age, gender, email) " +
            "values(#{traveller.id_number},#{traveller.name},#{traveller.phone_number}," +
            "#{traveller.address},#{traveller.age},#{traveller.gender},#{traveller.email})")
    void insertTraveller(@Param("traveller") Traveller traveller);

    @Delete("delete from traveller where traveller_id = #{traveller_id}")
    void deleteTraveller(@Param("traveller_id") Integer traveller_id);

    @Update("update traveller set id_number = #{traveller.id_number},name = #{traveller.name}" +
            ",phone_number = #{traveller.phone_number},address = #{traveller.address}" +
            ",age = #{traveller.age}, gender = #{traveller.gender}, email = #{traveller.email} " +
            "where traveller_id = #{traveller.traveller_id}")
    void updateTraveller(@Param("traveller") Traveller traveller);
}
