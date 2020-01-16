package com.agent.service.mapper;

import com.agent.service.pojo.Bill;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BillMapper extends Mapper<Bill> {
    @Select("select * from bill")
    List<Bill> getAllBill();

    @Delete("delete from bill where bill_id = #{bill_id}")
    void deleteBill(@Param("bill_id") Integer bill_id);

    @Insert("insert into bill(bill_id, traveller_id, activity_id, salesman_id," +
            "pay_date, pay_money) values (#{bill.bill_id}, #{bill.traveller_id}, #{bill.activity_id}" +
            ", #{bill.salesman_id}, #{bill.pay_date}, #{bill.pay_money})")
    void insertBill(@Param("bill")  Bill bill);

    @Update("update bill set " +
            "traveller_id = #{bill.traveller_id}" +
            ",activity_id = #{bill.activity_id}" +
            ",salesman_id = #{bill.salesman_id}" +
            ",pay_date = #{bill.pay_date}" +
            ",pay_money = #{bill.pay_money}" +
            "where bill_id = #{bill.bill_id}")
    void updateBill(@Param("bill") Bill bill);
}
