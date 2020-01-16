package com.agent.service.service;

import com.agent.service.mapper.BillMapper;
import com.agent.service.pojo.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillMapper billMapper;

    public List<Bill> getAllBill() {
        return billMapper.getAllBill();
    }

    public void deleteBill(Integer bill_id) {
        billMapper.deleteBill(bill_id);
    }

    public void updateBill(Integer bill_id,
                           Integer traveller_id,
                           Integer activity_id,
                           Integer salesman_id,
                           Timestamp pay_date,
                           Double pay_money) {
        Bill bill = new Bill();
        bill.setBill_id(bill_id);
        bill.setTraveller_id(traveller_id);
        bill.setActivity_id(activity_id);
        bill.setSalesman_id(salesman_id);
        bill.setPay_date(pay_date);
        bill.setPay_money(pay_money);
        billMapper.updateBill(bill);
    }

    public void insertBill(Integer traveller_id,
                           Integer activity_id,
                           Integer salesman_id,
                           Timestamp pay_date,
                           Double pay_money) {
        Bill bill = new Bill();
        bill.setBill_id(null);
        bill.setTraveller_id(traveller_id);
        bill.setActivity_id(activity_id);
        bill.setSalesman_id(salesman_id);
        bill.setPay_date(pay_date);
        bill.setPay_money(pay_money);
        billMapper.insertBill(bill);
    }
}
