package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.PageQuery;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.PageVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public void deduct(Long id, Integer money) {
        User user = getById(id);

        if (user == null || user.getStatus() == UserStatus.FREEZE) {
            throw new RuntimeException("用户状态异常");
        }

        if(user.getBalance() < money){
            throw new RuntimeException("user balance is not enough");
        }

        //baseMapper.deductMoneyById(id, money);

        int remainBalance = user.getBalance() - money;
        this.lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, 2)
                .eq(id != null, User::getId, id)
                .update();
    }

    @Override
    public UserVO queryUserAndAddressById(Long userId) {

        User user = getById(userId);
        if(user == null){
            return null;
        }

        List<Address> addresses = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, userId)
                .list();

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);

        userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));



        return userVO;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {


        List<User> users = listByIds(ids);
        List<UserVO> userVOList = BeanUtil.copyToList(users, UserVO.class);


        List<Address>  addressList = Db.lambdaQuery(Address.class)
                .in(Address::getUserId, ids)
                .list();

        List<AddressVO> addressVOS = BeanUtil.copyToList(addressList, AddressVO.class);

        Map<Long, List<AddressVO>> userAddressMap = addressVOS.stream().collect(Collectors.groupingBy(AddressVO::getUserId));



        for (UserVO userVO: userVOList){
            List<AddressVO> addressVOS1 = userAddressMap.get(userVO.getId());
            userVO.setAddresses(addressVOS1);
        }

        return userVOList;



    }
}
