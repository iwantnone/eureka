package org.ppzhu.controller;

import org.ppzhu.feign.UserFeign;
import org.ppzhu.pojo.AjaxResult;
import org.ppzhu.pojo.User;
import org.ppzhu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserFeign userFeign;

    @GetMapping
    public AjaxResult addOrder(){

        User user = orderService.addOrder("6a54faa8-a830-4ac4-a205-da9437f286f4");
        return AjaxResult.success(user);
    }

    @GetMapping("/openfeign")
    public AjaxResult openfeign(){
        AjaxResult ajaxResult = userFeign.getUserById("6a54faa8-a830-4ac4-a205-da9437f286f4");
        return ajaxResult;
    }
}
