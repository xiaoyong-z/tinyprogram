package com.agent.identity.mapper;

import com.agent.identity.pojo.Company;
import com.agent.identity.pojo.Traveller;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CompanyMapper extends Mapper<Company> {
    @Select("select * from company")
    List<Company> getAllCompany();

    @Insert("insert into company(name, phone_number, address) " +
            "values(#{company.name}, #{company.phone_number}, #{company.address})")
    void insertCompany(@Param("company")  Company company);

    @Delete("delete from company where company_id = #{company_id}")
    void deleteCompany(@Param("company_id") Integer company_id);

    @Update("update company set name = #{company.name}" +
            ",phone_number = #{company.phone_number},address = #{company.address}" +
            "where company_id = #{company.company_id}")
    void updateCompany(@Param("company") Company company);
}
