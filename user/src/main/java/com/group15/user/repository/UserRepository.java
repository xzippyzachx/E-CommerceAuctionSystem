package com.group15.user.repository;

import com.group15.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value="SELECT u FROM User u WHERE u.usr_username = :username")
    User findByUserName(@Param("username") String username);

    @Query(value="SELECT u FROM User u WHERE u.usr_id = :user_id")
    User findByUserId(@Param("user_id") Integer user_id);

    @Modifying
    @Transactional
    @Query(value="CALL user_data_reset()", nativeQuery = true)
    void resetUserData();

}
