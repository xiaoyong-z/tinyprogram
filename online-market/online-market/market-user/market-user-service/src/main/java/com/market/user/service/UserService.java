package com.market.user.service;

import com.market.common.enums.ExceptionEnum;
import com.market.common.exception.MkException;
import com.market.user.mapper.UserMapper;
import com.market.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Long userLogin(String name, String password, Boolean is_seller) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setIs_seller(is_seller);
        List<User> list = userMapper.select(user);
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.USER_NOT_FOUND);
        }
        return list.get(0).getId();
    }

    public User userSearch(Long id) {
        User user = new User();
        user.setId(id);
        List<User> list = userMapper.select(user);
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.USER_NOT_FOUND);
        }
        return list.get(0);
    }

    public void userRegister(String name, String password, Boolean is_seller) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setIs_seller(is_seller);
        user.setId(null);
        user.setBalance((long) 0);
        try {
            userMapper.insert(user);
        }catch (Exception e){
            throw new MkException(ExceptionEnum.USER_REGISTER_ERROR);
        }
    }

    public void userBuy(Long id1, Long id2, Long amount) {
        if(id1.equals(id2)) {
            throw new MkException(ExceptionEnum.USER_BUY_ERROR);
        }
        // 买家花钱
        User oldUser = userSearch(id1);
        Long newBalance = oldUser.getBalance() - amount;
        if(newBalance < 0){
            throw new MkException(ExceptionEnum.USER_BUY_ERROR);
        }
        User user = new User();
        user.setId(id1);
        user.setBalance(newBalance);
        int count = userMapper.updateByPrimaryKeySelective(user);
        if(count != 1){
            throw new MkException(ExceptionEnum.USER_BUY_ERROR);
        }

        // 卖家赚钱
        oldUser = userSearch(id2);
        newBalance = oldUser.getBalance() + amount;
        user = new User();
        user.setId(id2);
        user.setBalance(newBalance);
        count = userMapper.updateByPrimaryKeySelective(user);
        if(count != 1){
            throw new MkException(ExceptionEnum.USER_BUY_ERROR);
        }
    }

    public void userTopup(Long id, Long amount) {
        // 充值
        User oldUser = userSearch(id);
        Long newBalance = oldUser.getBalance() + amount;
        User user = new User();
        user.setId(id);
        user.setBalance(newBalance);
        int count = userMapper.updateByPrimaryKeySelective(user);
        if(count != 1){
            throw new MkException(ExceptionEnum.USER_TOPUP_ERROR);
        }
    }
}
