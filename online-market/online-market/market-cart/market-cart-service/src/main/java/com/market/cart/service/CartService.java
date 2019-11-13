package com.market.cart.service;

import com.market.cart.Cart;
import com.market.cart.mapper.CartMapper;
import com.market.common.enums.ExceptionEnum;
import com.market.common.exception.MkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    public List<Cart> getAllCart(Long userId) {
        Cart cart = new Cart();
        cart.setUser_id(userId);
        List<Cart> list = cartMapper.select(cart);
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.CART_NOT_FOUND);
        }
        return list;
    }

    public void addCart(Long user_id, Long item_id, Long buy_count) {
        Cart cart = new Cart();
        cart.setId(null);
        cart.setUser_id(user_id);
        cart.setItem_id(item_id);
        cart.setCount(buy_count);
        int count = cartMapper.insert(cart);
        if(count != 1){
            throw new MkException(ExceptionEnum.ADD_CART_ERROR);
        }
    }

    public void deleteCart(Long id) {
        Cart cart = new Cart();
        cart.setId(id);
        int count = cartMapper.deleteByPrimaryKey(cart);
        if(count != 1){
            throw new MkException(ExceptionEnum.DELETE_CART_ERROR);
        }
    }

    public void updateCart(Long id, Long buy_count) {
        Cart cart = new Cart();
        cart.setId(id);
        cart.setCount(buy_count);
        int count = cartMapper.updateByPrimaryKeySelective(cart);
        if(count != 1){
            throw new MkException(ExceptionEnum.UPDATE_CART_ERROR);
        }
    }

    public void deleteCartBatch(Long user_id) {
        List<Long> idList = new ArrayList<Long>();
        List<Cart> cartList = getAllCart(user_id);
        for (Cart cart : cartList) {
            idList.add(cart.getId());
        }
        int count = cartMapper.deleteByIdList(idList);
        if(count < 1){
            throw new MkException(ExceptionEnum.DELETE_BATCH_CART_ERROR);
        }
    }
}
