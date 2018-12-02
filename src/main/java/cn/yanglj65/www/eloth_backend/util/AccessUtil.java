package cn.yanglj65.www.eloth_backend.util;

import cn.yanglj65.www.eloth_backend.entity.Result;

public class AccessUtil {
    public static Result ACCESSDENY(){
        return ResultUtil.resultBadReturner("Access Deny");
    }
}
