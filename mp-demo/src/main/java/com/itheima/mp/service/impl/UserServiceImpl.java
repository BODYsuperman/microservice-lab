package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.PageQuery;
import com.itheima.mp.domain.vo.PageVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<User> queryUsers(String username, Integer status, Long min, Long max) {
        return lambdaQuery().like(User::getUsername, username).list();
    }



    @Override
    public PageVO<UserVO> queryUserByPage(PageQuery query) {
        // 1.分页条件
        Page<User> p = query.toMpPageDefaultSortByUpdate();
        // 2.查询
        page(p);
        // 3.返回
        return new PageVO<>(p, user -> {
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            String username = vo.getUsername();
            vo.setUsername(username.substring(0, username.length()- 4) + "****");
            return vo;
        });
    }
}
