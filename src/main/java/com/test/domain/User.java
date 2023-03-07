package com.test.domain;

import lombok.Data;

/**
 * com.test对象 user
 *
 * @author gx
 * @date 2023-03-07
 */
@Data
public class User {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String username;

    /**
     * 年龄
     */
    private Long age;

    /**
     * 性别
     */
    private Long gender;

    /**
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 详细地址
     */
    private String address;
    private String addressCode;

}
