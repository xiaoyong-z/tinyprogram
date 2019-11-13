package com.market.user.web;


import com.market.user.User;
import com.market.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Long> userLogin(
            @RequestParam(value="name") String name,
            @RequestParam(value="password") String password,
            @RequestParam(value="is_seller") Boolean is_seller){
            return ResponseEntity.ok(userService.userLogin(name, password, is_seller));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> userSearch(
            @RequestParam(value="id") Long id){
        return ResponseEntity.ok(userService.userSearch(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> userRegister(
            @RequestParam(value="name") String name,
            @RequestParam(value="password") String password,
            @RequestParam(value="is_seller") Boolean is_seller){
        userService.userRegister(name, password, is_seller);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> userRegister(
            @RequestParam(value="id") Long id,
            @RequestParam(value="amount") Long amount){
        userService.userTopup(id, amount);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(value = "buy", method = RequestMethod.PATCH)
    public ResponseEntity<Void> userBuy(
            @RequestParam(value="id1") Long id1,
            @RequestParam(value="id2") Long id2,
            @RequestParam(value="amount") Long amount){
        userService.userBuy(id1, id2, amount);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
