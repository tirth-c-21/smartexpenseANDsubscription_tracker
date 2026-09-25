package com.microservices.user_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservices.user_service.entity.AppUser;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long>{

	@Query("SELECT au FROM AppUser au WHERE au.username= ?1")
	Optional<AppUser> findByUserName(String username);

	
}
