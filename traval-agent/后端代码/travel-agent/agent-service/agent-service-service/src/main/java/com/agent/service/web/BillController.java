package com.agent.service.web;

import com.agent.service.pojo.Bill;
import com.agent.service.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
@RestController
@RequestMapping("service/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Bill>> getAllBill(){
        return ResponseEntity.ok(billService.getAllBill());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBill(
            @RequestParam(value = "bill_id") Integer bill_id
    ){
        billService.deleteBill(bill_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateTraveller(
            @RequestParam(value = "bill_id") Integer bill_id,
            @RequestParam(value = "traveller_id") Integer traveller_id,
            @RequestParam(value = "activity_id") Integer activity_id,
            @RequestParam(value = "salesman_id") Integer salesman_id,
            @RequestParam(value = "pay_date") String raw_pay_date,
            @RequestParam(value = "pay_money") Double pay_money
    ){
        Timestamp pay_date = new Timestamp(Long.parseLong(raw_pay_date));
        billService.updateBill(bill_id, traveller_id, activity_id, salesman_id, pay_date, pay_money);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCompany(
            @RequestParam(value = "traveller_id") Integer traveller_id,
            @RequestParam(value = "activity_id") Integer activity_id,
            @RequestParam(value = "salesman_id") Integer salesman_id,
            @RequestParam(value = "pay_date") String raw_pay_date,
            @RequestParam(value = "pay_money") Double pay_money
    ){
        Timestamp pay_date = new Timestamp(Long.parseLong(raw_pay_date));
        billService.insertBill(traveller_id, activity_id, salesman_id, pay_date, pay_money);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
