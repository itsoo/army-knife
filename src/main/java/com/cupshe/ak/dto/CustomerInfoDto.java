package com.cupshe.ak.dto;

import lombok.Data;

/**
 * Description: TODO
 * User: chenzuofu
 * Date: 2020-11-09
 * Time: 下午1:32
 */
@Data
public class CustomerInfoDto {

    /**
     * 用户id
     */
    private Long customerId ;

    /**
     * 用户邮箱
     */
    private String email ;

    /**
     * 用户登录状态0-未登录 1-登录
     */
    private Integer loginStatus ;
}
