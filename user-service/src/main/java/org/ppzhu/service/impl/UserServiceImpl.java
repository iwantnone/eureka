package org.ppzhu.service.impl;

import org.ppzhu.mapper.UserMapper;
import org.ppzhu.pojo.User;
import org.ppzhu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Author: ppzhu
 * @Date: 2024/9/26
 * @Description: Describe the purpose or functionality of the class here.
 * You can provide detailed explanations, usage examples, etc.
 * @Modification History:
 * Date         Author          Description
 * -----------  --------------  -----------------------------------
 * 2024/9/26      ppzhu         Initial version
 * @Copyright: Copyright 2024, ppzhu.
 * All rights reserved.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String uid) {
        return userMapper.selectUserById(uid);
    }

    @Override
    public List<User> getUsers() {
        return userMapper.selectUsers();
    }
}
