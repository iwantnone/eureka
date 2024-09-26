package org.ppzhu.controller;

import org.ppzhu.pojo.AjaxResult;
import org.ppzhu.pojo.User;
import org.ppzhu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{uid}")
    public AjaxResult getUserById(@PathVariable String uid){
        User user = userService.getUserById(uid);
        return AjaxResult.success(user);
    }

    @GetMapping
    public AjaxResult getUsers(){
        List<User> users = userService.getUsers();
        return AjaxResult.success(users);
    }
}
