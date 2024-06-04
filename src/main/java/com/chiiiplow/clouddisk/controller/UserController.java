package com.chiiiplow.clouddisk.controller;


import com.chiiiplow.clouddisk.common.R;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.service.UserService;
import com.chiiiplow.clouddisk.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户控制层
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


}
