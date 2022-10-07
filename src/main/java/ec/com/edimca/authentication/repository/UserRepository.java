package ec.com.edimca.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.edimca.authentication.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
