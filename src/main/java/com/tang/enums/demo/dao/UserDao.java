package com.tang.enums.demo.dao;

import com.tang.enums.demo.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author: tangj <br>
 * date: 2019-04-04 17:10 <br>
 * description:
 */
@Repository
public interface UserDao {

    List<User> getUsersByRoleName(@Param("roleName") String roleName);

    int insert(User user);

    List<User> selectByProperty(User user);
}
