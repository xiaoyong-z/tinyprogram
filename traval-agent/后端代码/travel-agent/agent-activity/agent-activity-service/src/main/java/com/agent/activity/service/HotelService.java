package com.agent.activity.service;

import com.agent.activity.mapper.HotelMapper;
import com.agent.activity.pojo.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    private HotelMapper hotelMapper;

    public List<Hotel> getAllHotel() {
        return hotelMapper.getAllHotel();
    }

    public void deleteHotel(Integer hotel_id) {
        hotelMapper.deleteHotel(hotel_id);
    }

    public void updateHotel(Integer hotel_id, String name,
                             String address, String phone_number,
                             String description) {
        Hotel hotel = new Hotel();
        hotel.setHotel_id(hotel_id);
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setPhone_number(phone_number);
        hotel.setDescription(description);
        hotelMapper.updateHotel(hotel);
    }

    public void insertHotel(String name,
                            String address, String phone_number,
                            String description) {
        Hotel hotel = new Hotel();
        hotel.setHotel_id(null);
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setPhone_number(phone_number);
        hotel.setDescription(description);
        hotelMapper.insertHotel(hotel);
    }
}
