package com.agent.activity.web;

import com.agent.activity.pojo.Hotel;
import com.agent.activity.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("activity/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Hotel>> getAllHotel(){
        return ResponseEntity.ok(hotelService.getAllHotel());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteHotel(
            @RequestParam(value = "hotel_id") Integer hotel_id
    ){
        hotelService.deleteHotel(hotel_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateTraveller(
            @RequestParam(value = "hotel_id") Integer hotel_id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "phone_number") String phone_number,
            @RequestParam(value = "description") String description
    ){
        hotelService.updateHotel(hotel_id, name, address, phone_number, description);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCompany(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "phone_number") String phone_number,
            @RequestParam(value = "description") String description
    ){
        hotelService.insertHotel(name, address, phone_number, description);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
