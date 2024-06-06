package com.swiftdeploy.swiftdeploy.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiftdeploy.swiftdeploy.Models.UserModel;


public interface UserRepository extends JpaRepository<UserModel, UUID> {

    UserModel findByUserName(String userName);
    UserModel findByEmail(String email);
}
