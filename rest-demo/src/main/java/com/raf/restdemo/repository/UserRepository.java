package com.raf.restdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raf.restdemo.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
