package com.huang.service;

import com.huang.pojo.User;

/**
 * @author guangtou
 * @create 2020--02--07--9:34
 */

public interface UserService {

    public User checkUser(String username, String password);
}
