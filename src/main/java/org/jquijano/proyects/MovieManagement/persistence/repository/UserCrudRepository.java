package org.jquijano.proyects.MovieManagement.persistence.repository;

import jakarta.transaction.Transactional;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Long> {

    List<User> findByNameContaining(String name);

    Optional<User> findByUsername(String username);

    //Indica a JPA que la consulta puede tener efectos secundarios en el estado persistence de la base de datos
    @Modifying
    @Transactional
    int deleteByUsername(String username);
}
