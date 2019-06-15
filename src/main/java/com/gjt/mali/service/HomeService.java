package com.gjt.mali.service;

import com.gjt.mali.pojo.User;
import com.gjt.mali.pojo.UserExample;

import java.util.List;

public interface HomeService {
    List<User> queryAllUser(UserExample userExample);

    int createUser(User user);
}
