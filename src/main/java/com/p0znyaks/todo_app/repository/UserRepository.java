package com.p0znyaks.todo_app.repository;

import com.p0znyaks.todo_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}