package com.itproger.repo;

import com.itproger.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findById(long id);




//    @Query(value = "select id, email from users  where username = :name", nativeQuery = true)
//    User findEmail(@Param("name") String name);





}
