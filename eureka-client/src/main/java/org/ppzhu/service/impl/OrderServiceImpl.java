package org.ppzhu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.ppzhu.pojo.AjaxResult;
import org.ppzhu.pojo.User;
import org.ppzhu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @HystrixCommand(fallbackMethod = "fallback",commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager
                    .EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS,
                    value = "5000"), // 统计周期，默认10秒
            @HystrixProperty(name = HystrixPropertiesManager
                    .CIRCUIT_BREAKER_ENABLED,
                    value = "true"), // 是否开启熔断，默认true
            @HystrixProperty(name = HystrixPropertiesManager
                    .CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,
                    value = "2"), // 统计周期内，错误几次，开启熔断， 默认20
            @HystrixProperty(name = HystrixPropertiesManager
                    .CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,
                    value = "50"), // 统计周期内，错误百分比达到多少，开启熔断， 默认50
            @HystrixProperty(name = HystrixPropertiesManager
                    .CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS,
                    value = "3000"), // 开启熔断后，多少毫秒不访问远程服务，默认5000毫秒
            @HystrixProperty(name = HystrixPropertiesManager
                    .CIRCUIT_BREAKER_FORCE_OPEN,
                    value = "false"), // 是否强制开启熔断器， 默认false
            @HystrixProperty(name = HystrixPropertiesManager
                    .CIRCUIT_BREAKER_FORCE_CLOSED,
                    value = "false") // 是否强制关闭熔断器， 默认false
    })
    @Cacheable(cacheNames = "org.pphzu",key = "#uid")
    public User addOrder(String uid) {

        System.out.println("主方法被执行");
        //新建订单对象。。。。

        //查找用户订单

        //获取实例服务的地址
        ServiceInstance instance = loadBalancerClient.choose("user-service");
        URI uri = instance.getUri();
        System.out.println(uri);

        //发送请求获取数据
        ResponseEntity<AjaxResult> responseEntity = restTemplate.exchange(uri+"/user/" + uid, HttpMethod.GET, null, AjaxResult.class);
        System.out.println(responseEntity.getStatusCode());
        AjaxResult aj = responseEntity.getBody();
        System.out.println(aj);
        Object data = aj.getData();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(data);
            User user = objectMapper.readValue(json, User.class);


            //成功获取数据
            return user;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 降级方法需遵守如下规则
     * 访问权限: 降级方法的访问权限必须与原方法相同或更宽松。例如，如果原方法是 public 的，那么降级方法可以是 public、protected 或 default 的，但不能是 private 的。
     * 返回值类型: 降级方法的返回值类型必须与原方法的返回值类型相同，或者可以赋值给原方法的返回值类型。例如，如果原方法返回 User 类型，那么降级方法可以返回 User 类型或其子类类型。
     * 异常类型: 降级方法可以抛出与原方法相同的异常类型，或者可以抛出原方法异常类型的子类类型。
     * 方法名称: 降级方法的名称必须在 @HystrixCommand 注解的 fallbackMethod 属性中指定。
     * 方法签名唯一性: 在同一个类中，不能存在多个具有相同名称和参数类型的降级方法。
     * @param uid
     * @return
     */
    public User fallback(String uid){
        System.out.println("兜底啦");
        User user = new User();
        user.setUid(uid);
        user.setUname("游客");
        return user;
    }
}
