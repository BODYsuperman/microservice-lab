package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserWrapperTest {


    @Autowired
    private  UserMapper userMapper;

    @Test
   void testWrapper(){

        // 1.构建查询条件 where name like "%o%" AND balance >= 1000

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .like("username", "O")
                .ge("balance", 1000);

        List<User> users = userMapper.selectList(userQueryWrapper);

        users.forEach(System.out::println);



    }

    @Test
    void testLambdaWrapper(){

        // 1.构建查询条件 where name like "%o%" AND balance >= 1000

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.select(User::getId, User::getUsername, User::getInfo, User::getBalance).like(User::getUsername, "O")
                .ge(User::getBalance, 1000);


        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::println);

    }

    @Test
    void testQueryWrapperUpdate(){
        User user = new User();
        user.setBalance(2000);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().eq("username", "jack");

        userMapper.update(user, userQueryWrapper );

    }

    @Test
    void testLambdaUpdateWrapper(){

        List<Long> ids = List.of(1L, 2L, 4L);
        LambdaUpdateWrapper<User> userUpdateWrapper = new LambdaUpdateWrapper<User>();

        userUpdateWrapper.setSql("balance = balance - 200")

                .in(User::getId, ids);

        userMapper.update(null, userUpdateWrapper);
    }

    @Test
    void testCustomWrapper(){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>().in(User::getId, List.of(1L, 2L, 4L));

        userMapper.deductBalanceById(200, userLambdaQueryWrapper);

    }
}
