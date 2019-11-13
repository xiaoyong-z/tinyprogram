package com.market.item.web;


import com.market.item.pojo.Item;
import com.market.item.service.ItemService;
import com.sun.codemodel.internal.JMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getAllItem(){
        return ResponseEntity.ok(itemService.getALLItem());
    }

    @RequestMapping(value="search", method = RequestMethod.GET)
    public ResponseEntity<Item> getItem(
            @RequestParam(value="id") Long id){
        return ResponseEntity.ok(itemService.getItem(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addNewItem(
            @RequestParam(value="name") String name,
            @RequestParam(value="category") String category,
            @RequestParam(value="img_url") String img_url,
            @RequestParam(value="price") Long price,
            @RequestParam(value="belong_id") Long belong_id,
            @RequestParam(value="stock") Long stock
    ){
        itemService.addNewItem(name, category, img_url, price, belong_id, stock);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> takeOffItem(
            @RequestParam(value="id") Long id) {
        itemService.takeOffItem(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(value="buy", method = RequestMethod.PATCH)
    public ResponseEntity<Void> buyItem(
            @RequestParam(value="id") Long id,
            @RequestParam(value="count") Long count) {
        itemService.buyItem(id, count);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(value="add", method = RequestMethod.PATCH)
    public ResponseEntity<Void> addStock(
            @RequestParam(value="id") Long id,
            @RequestParam(value="count") Long count){
        itemService.addStock(id, count);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
