package com.itheima.mp.controller;


import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import io.swagger.annotations.Api;
import com.itheima.mp.domain.query.PageQuery;
import com.itheima.mp.domain.vo.PageVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api("User interface management")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/page")
    public PageVO<UserVO> queryUserByPage(PageQuery query){
        return userService.queryUserByPage(query);
    }


    @ApiOperation("add new user")
    @PostMapping
    public  void savaUser(@RequestBody UserFormDTO userFormDTO){



        User user =  BeanUtil.copyProperties(userFormDTO, User.class);

        userService.save(user);
    }

    @ApiOperation("delete user")
    @DeleteMapping("/{id}")
    public  void removeUserByid(@PathVariable("id") Long userId ){

        userService.removeById(userId);
    }


    @ApiOperation("get user by id")
    @GetMapping("/{id}")
    public UserVO queryUserById(@PathVariable("id") Long userId ){
//        User user = userService.getById(userId);
//
//        return   BeanUtil.copyProperties(user, UserVO.class);

        return  userService.queryUserAndAddressById(userId);
    }

    @ApiOperation("get users by ids")
    @GetMapping
    public  List<UserVO> queryUserByIds(@RequestParam("ids") List<Long> ids){
//        List<User> users = userService.listByIds(ids);
//
//        return BeanUtil.copyToList(users, UserVO.class);



        return  userService.queryUserAndAddressByIds(ids);
    }

    @ApiOperation("deduct balance from user id")
    @PostMapping("/{id}/deduction/{money}")
    public void freezeUser(@PathVariable("id") Long id, @PathVariable("money") Integer money){

        userService.deduct(id, money);

    }
    @GetMapping("/list")
    @ApiOperation("根据id集合查询用户")
    public List<UserVO> queryUsers(UserQuery query){


        String username = query.getName();
        Integer status = query.getStatus();
        Integer minBalance = query.getMinBalance();
        Integer maxBalance = query.getMaxBalance();


        List<User> users = userService.lambdaQuery()
                .like(username != null, User::getUsername, username)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();

        return BeanUtil.copyToList(users, UserVO.class);



    }

}
