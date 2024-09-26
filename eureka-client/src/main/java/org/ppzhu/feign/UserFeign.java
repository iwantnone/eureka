package org.ppzhu.feign;

import org.ppzhu.pojo.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
@FeignClient("user-service")
public interface UserFeign {

    /**
     * @PathVariable("uid")中的uid不能省略
     * @param uid 用户ID
     * @return
     */
    @GetMapping("/user/{uid}")
    public AjaxResult getUserById(@PathVariable("uid") String uid);

}
