package com.gtriangle.admin.util;

import java.util.HashSet;
import java.util.List;

/**
 * list的工具类  
 * @author gaoyan_scj    
 * @version 1.0  
 * @created 2015-5-5 下午1:21:57
 */

public class ListUtil {

    /**
     *
     * 此方法描述的是：判断一个list是否是空的
     * @param list  待判断的list
     * @return boolean
     */
    public static boolean isEmpty(List<?> list) {
        if (null == list || 0 == list.size()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
    *
    * 此方法描述的是：判断一个list是否是空的
    * @param list  待判断的list
    * @return boolean
    */
   public static boolean isNotEmpty(List<?> list) {
      return !isEmpty(list);
   }

    /**
     * 校验list是否有重复数据
     * @param list
     * @return
     */
   public static  boolean isExistDuplicate(List<?> list) {
       if(isNotEmpty(list)){
           HashSet<?> set=new HashSet(list);
           return set.size()!=list.size();
       }
       return false;
   }
}
