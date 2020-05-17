package com.jllz.habitissimo.repository;

import com.jllz.habitissimo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user u WHERE u.email = ?1", nativeQuery = true)
    User findUserByEmail(String email);
}
