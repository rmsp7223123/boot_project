package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByLoginId(String loginId);

	// Spring Data JPA가 이 메소드의 이름을 분석하여, 이에 해당하는 SQL 쿼리를 자동으로 생성하고 실행
	// findByLoginId(String loginId)라는 메소드는 내부적으로 "SELECT * FROM Users WHERE loginid
	// = ?"라는 SQL 쿼리를 생성하고 실행
	// Optional<Users>는 조회 결과가 없을 경우 null 대신에 Optional.empty()를 반환하므로,
	// NullPointerException을 방지하는 안전한 코드 작성
	Optional<Users> findByNickname(String nickname);
 
	Optional<Users> findByLoginIdAndName(String loginId, String name);
	
	Optional<Users> findByLoginIdAndPassword(String loginId, String password);
 
	Boolean existsByLoginId(String loginId);
  
	Boolean existsByNickname(String nickname);	
}
