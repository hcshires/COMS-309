package edu.iastate.cs309.hb6.FoodTime.Login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUID(String UID);
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
