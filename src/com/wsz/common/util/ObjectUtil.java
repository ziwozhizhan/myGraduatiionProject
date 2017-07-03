package com.wsz.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * 工具类<br/>
 * 一般无法明确要操作的具体对象是什么类型，操作对于所有类型的对象都适应
 * @author wanshenzhen  2017/3/15.
 */
public class ObjectUtil {
    private ObjectUtil(){}
    /**
     * 判断对象是否为空
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof String){
            return ((String) obj).length() == 0;
        }
        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
        }
        return false;
    }
}
