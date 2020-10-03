package com.cupshe.ak;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * 分页工具类
 *
 * @author zxy
 */
public class PageUtils {

    /**
     * 开启分页
     *
     * @param page 分页查询对象
     */
    public static void startPage(QueryPageDTO page) {
        if (page == null) {
            return;
        }

        PageHelper.startPage(
                Optional.ofNullable(page.getPageNum()).orElse(1),
                Optional.ofNullable(page.getPageSize()).orElse(10));
    }

    /**
     * Page 封装
     *
     * @param list   数据
     * @param lambda convert
     * @param <P>    Params class
     * @param <R>    Result class
     * @return PageInfo
     */
    @SuppressWarnings("unchecked")
    public static <P, R> PageInfo<R> page(List<P> list, ConvertList<P, R> lambda) {
        PageInfo<R> result = new PageInfo(list);
        result.setList(lambda.convert(list));
        return result;
    }

    /**
     * 列表类型转换 Function
     *
     * @param <P> Params class
     * @param <R> Result class
     */
    @FunctionalInterface
    public interface ConvertList<P, R> {
        /**
         * convert
         *
         * @param list 原始类型
         * @return 目标类型
         */
        List<R> convert(List<P> list);
    }
}
