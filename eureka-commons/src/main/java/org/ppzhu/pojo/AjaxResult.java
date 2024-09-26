package org.ppzhu.pojo;

import lombok.Data;

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
@Data
public class AjaxResult<T> {
    private Integer code;
    private String msg;
    private T data;


    public static <T> AjaxResult success(T data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(200);
        ajaxResult.setMsg("success");
        ajaxResult.setData(data);
        return ajaxResult;
    }
}
