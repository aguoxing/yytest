package com.test.service;

import com.test.domain.User;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author gx
 * @date 2023-03-07
 */
public interface IUserService {
    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    User selectUserById(Long id);

    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return 用户集合
     */
    List<User> selectUserList(User user);

    /**
     * 新增用户
     *
     * @param user 用户
     * @return 结果
     */
    int insertUser(User user);

    /**
     * 修改用户
     *
     * @param user 用户
     * @return 结果
     */
    int updateUser(User user);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户主键集合
     * @return 结果
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    int deleteUserById(Long id);
}
