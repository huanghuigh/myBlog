package com.huang.dao;

import com.huang.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户dao层
 * @author guangtou
 * @create 2020--02--07--9:32
 */
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 校验用户名和密码
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username,String password);
}
