package com.jllz.habitissimo.service;

import com.jllz.habitissimo.model.User;

public interface UserService {
    User save(User user);
    User findUserByEmail(String email);
}
