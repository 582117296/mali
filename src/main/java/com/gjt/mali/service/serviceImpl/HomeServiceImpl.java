package com.gjt.mali.service.serviceImpl;

import com.gjt.mali.mapper.UserMapper;
import com.gjt.mali.pojo.User;
import com.gjt.mali.pojo.UserExample;
import com.gjt.mali.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> queryAllUser(UserExample userExample) {
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public int createUser(User user) {
        int num=0;
        int insert = userMapper.insert(user);
        if (insert>0){
            num=1;
        }
        return num;
    }
}
