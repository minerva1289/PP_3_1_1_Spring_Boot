package org.minerva.PP_3_1_1_Spring_Boot.repository;

import org.minerva.PP_3_1_1_Spring_Boot.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {
    Optional <User> findByEmail (String email);
}
