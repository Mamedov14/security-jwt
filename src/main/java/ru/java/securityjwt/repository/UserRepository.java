package ru.java.securityjwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.java.securityjwt.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.username= :username and u.password= :password")
    User validateUser(String username, String password);

    @Query("select u from User u where u.id= :id")
    User getUserById(Long id);

    @Query("select u from User u where u.username= :username")
    User getByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
