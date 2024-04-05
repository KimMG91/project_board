package com.project.project_board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.project_board.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
    public boolean existsByEmailAndPassword(String email, String password);
}
