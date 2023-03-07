package com.test.service.impl;

import com.test.domain.User;
import com.test.mapper.UserMapper;
import com.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service业务层处理
 *
 * @author gx
 * @date 2023-03-07
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    @Override
    public User selectUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    /**
     * 查询用户列表
     *
     * @param user 用户
     * @return 用户
     */
    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 新增用户
     *
     * @param user 用户
     * @return 结果
     */
    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户
     *
     * @param user 用户
     * @return 结果
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户主键
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] ids) {
        return userMapper.deleteUserByIds(ids);
    }

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    @Override
    public int deleteUserById(Long id) {
        return userMapper.deleteUserById(id);
    }
}
