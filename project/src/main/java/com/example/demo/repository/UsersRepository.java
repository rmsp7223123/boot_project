package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	
	
	Optional<Users> findByLoginId(String loginId);

	Boolean existsByLoginId(String loginId);

	Boolean existsByNickname(String nickname);
}
