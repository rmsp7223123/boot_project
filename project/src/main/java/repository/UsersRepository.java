package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByLoginId(String loginId);

}
