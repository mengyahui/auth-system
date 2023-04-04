package com.auth.helper;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @time 2023/04/03 下午 09:01
 */
public class BeanCopyHelper {

    /**
     * bean 拷贝方法
     * @param source 源数据
     * @param targetClass 目标 Bean 的 class
     * @return 目标对象
     * @param <V> 目标类
     */
    public static <V> V copyBean(Object source, Class<V> targetClass) {
        V result = null;
        try {
            //创建对象
            result = targetClass.getDeclaredConstructor().newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * BeanList 拷贝方法
     * @param list 源 BeanList 数据
     * @param targetClass 目标 Bean 的 class
     * @return 目标 Bean 的 List
     * @param <O> 源 BeanList 泛型
     * @param <V> 目标 BeanList 泛型
     */
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> targetClass) {
        return list.stream()
                .map(o -> copyBean(o, targetClass))
                .collect(Collectors.toList());
    }
}
