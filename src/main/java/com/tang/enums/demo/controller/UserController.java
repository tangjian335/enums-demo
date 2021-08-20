package com.tang.enums.demo.controller;

import com.tang.enums.demo.utils.Result;
import com.tang.enums.demo.domain.User;
import com.tang.enums.demo.enums.GenderEnum;
import com.tang.enums.demo.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : tangjian
 * @date : 2021-08-18 17:09
 */
@Slf4j
@RestController
@Api("优雅的传参方式")
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("path/{gender}")
    public Result<String> testPath(@PathVariable GenderEnum gender) {
        log.info("接收到参数：{}", gender);
        return new Result<>();
    }

    @GetMapping("query")
    public Result<String> testQuery(@RequestParam GenderEnum gender) {
        log.info("接收到参数：{}", gender);
        return new Result<>();
    }

    @PostMapping("body")
    public Result<String> testBody(@RequestBody User user) {
        log.info("接收到参数：{}", user);
        return new Result<>();
    }

    @PostMapping("search")
    public Result<List<User>> search(@RequestBody User user) {
        List<User> users = userService.search(user);
        return new Result<>(users);
    }

}
