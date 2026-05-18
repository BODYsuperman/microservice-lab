package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest1 {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testQueryByIds() {
        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L));
        users.forEach(System.out::println);
    }


    @Test
    void testSaveUser1() {
        User user = new User();
        user.setUsername("LiLei2");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setId(100L);

        UserInfo info = new UserInfo();
        info.setAge(24);
        info.setIntro("英文老师");
        info.setGender("female");

        user.setInfo(info);

        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userMapper.insert(user);
    }


    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("LiLei");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testGetById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }

    @Test
    void testSelectByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(600);
        userMapper.updateById(user);
    }

    @Test
    void testRemove() {
        userMapper.deleteById(5L);
    }

    @Test
    void testQueryWrapper() {
        // 1.构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", 1000);

        // 2.查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateByQueryWrapper() {
        // 1.数据
        User user = new User();
        user.setBalance(2000);
        // 2.条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", "jack");
        // 3.更新
        userMapper.update(user, wrapper);
    }

    @Test
    void testUpdateWrapper() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id", List.of(1L, 2L, 4L));
        userMapper.update(null, wrapper);
    }

    @Test
    void testLambdaQueryWrapper() {
        // 1.构建条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .select(User::getId, User::getUsername, User::getInfo, User::getBalance)
                .like(User::getUsername, "o")
                .ge(User::getBalance, 1000);
        userMapper.selectList(wrapper);
    }

    @Test
    void testCustomSql() {
        List<Long> ids = List.of(1L, 2L, 4L);
        int amount = 200;
        // 1.定义条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .in("id", ids);
        // 2.执行更新
        userMapper.updateBalanceByWrapper(amount, wrapper);
    }

    @Test
    void testMultiTableQuery() {
        List<Long> ids = List.of(1L, 2L, 4L);
        String city = "北京";

        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .in("u.id", ids)
                .eq("a.city", city);

        List<User> users = userMapper.queryUsersByWrapper(wrapper);
        users.forEach(System.out::println);
    }
}