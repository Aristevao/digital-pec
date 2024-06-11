package com.fho.digitalpec.api.user.repository;

import java.util.List;

import com.fho.digitalpec.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByNameAsc();
    User findByEmail(String email);
}
