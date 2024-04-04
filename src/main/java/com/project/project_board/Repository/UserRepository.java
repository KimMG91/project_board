package com.project.project_board.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.project.project_board.Entity.UserEntity;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<UserEntity, String>{
    
}
