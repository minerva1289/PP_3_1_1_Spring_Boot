package org.minerva.PP_3_1_1_Spring_Boot.service;


import org.minerva.PP_3_1_1_Spring_Boot.model.User;

import java.util.List;

public interface UserService {
    User getUserByID (long id);

    List <User> getAllUsers ();

    void addUser (User user);

    void deleteUser (long id);

    boolean existsEmail (String email);
}
