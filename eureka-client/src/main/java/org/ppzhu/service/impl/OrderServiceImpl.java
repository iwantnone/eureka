package org.ppzhu.service.impl;

import org.ppzhu.pojo.AjaxResult;
import org.ppzhu.pojo.User;
import org.ppzhu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

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
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public boolean addOrder(String uid) {
        //新建订单对象。。。。

        //查找用户订单

        //获取实例服务的地址
        ServiceInstance instance = loadBalancerClient.choose("user-service");
        URI uri = instance.getUri();

        //发送请求获取数据
        ResponseEntity<AjaxResult> responseEntity = restTemplate.exchange(uri+"/user/" + uid, HttpMethod.GET, null, AjaxResult.class);

        AjaxResult aj = responseEntity.getBody();
        Object data = aj.getData();
        if (!Objects.isNull(data) && data instanceof User) {
            User user = (User) data;
            System.out.println(user);
            //成功获取数据
        }


        return true;
    }
}
