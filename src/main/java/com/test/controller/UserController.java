package com.test.controller;

import com.test.common.BaseController;
import com.test.common.R;
import com.test.common.page.TableDataInfo;
import com.test.domain.User;
import com.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 *
 * @author gx
 * @date 2023-03-07
 */
@RestController
@RequestMapping("/test/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 查询用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(User user) {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 获取用户详细信息
     */
    @GetMapping(value = "/{id}")
    public R getInfo(@PathVariable("id") Long id) {
        return R.success(userService.selectUserById(id));
    }

    /**
     * 新增用户
     */
    @PostMapping
    public R add(@RequestBody User user) {
        return toJson(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PutMapping
    public R edit(@RequestBody User user) {
        return toJson(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable Long[] ids) {
        return toJson(userService.deleteUserByIds(ids));
    }
}
