package com.market.cart.web;

import com.market.cart.Cart;
import com.market.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> getAllCart(
            @RequestParam(value = "user_id")Long user_id){
        return ResponseEntity.ok(cartService.getAllCart(user_id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addCart(
            @RequestParam(value = "user_id") Long user_id,
            @RequestParam(value = "item_id") Long item_id,
            @RequestParam(value = "count") Long count){
        cartService.addCart(user_id, item_id, count);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateCart(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "count") Long count){
        cartService.updateCart(id, count);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCart(
            @RequestParam(value = "id") Long id
    ){
        cartService.deleteCart(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(value="deleteBatch", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCartBatch(
            @RequestParam(value = "user_id") Long user_id
    ){
        cartService.deleteCartBatch(user_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
