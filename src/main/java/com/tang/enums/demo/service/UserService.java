package com.tang.enums.demo.service;

import com.tang.enums.demo.domain.User;
import com.tang.enums.demo.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : tangjian
 * @date : 2021-08-17 14:59
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public void insert(User user){
        userDao.insert(user);
    }

    public List<User> search(User user){
        return userDao.selectByProperty(user);
    }
}
