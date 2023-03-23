package com.group15.user.repository;

import com.group15.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value="SELECT u FROM User u WHERE u.usr_username = :username")
    User findByUserName(@Param("username") String username);
}
