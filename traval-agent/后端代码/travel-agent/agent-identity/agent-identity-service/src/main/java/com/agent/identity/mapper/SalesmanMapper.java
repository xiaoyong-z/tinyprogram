package com.agent.identity.mapper;
import com.agent.identity.pojo.Salesman;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SalesmanMapper extends Mapper<Salesman> {
    @Select("select * from salesman")
    List<Salesman> getAllSalesman();

    @Delete("delete from salesman where salesman_id = #{salesman_id}")
    void deleteSalesman(@Param("salesman_id") Integer salesman_id);
    
    @Insert("insert into salesman(id_number, name, phone_number, address, age, gender, account," +
            "password, company_id, is_manager) values(#{salesman.id_number},#{salesman.name},#{salesman.phone_number}," +
            "#{salesman.address},#{salesman.age},#{salesman.gender},#{salesman.account}," +
            "#{salesman.password}, #{salesman.company_id}, #{salesman.is_manager})")
    void insertSalesman(@Param("salesman")  Salesman salesman);

    @Update("update salesman set id_number = #{salesman.id_number},name = #{salesman.name}" +
            ",phone_number = #{salesman.phone_number},address = #{salesman.address}" +
            ",age = #{salesman.age}, gender = #{salesman.gender}, account = #{salesman.account} " +
            ",password = #{salesman.password}, company_id = #{salesman.company_id}" +
            " where salesman_id = #{salesman.salesman_id}")
    void updateSalesman(@Param("salesman") Salesman salesman);
}

