package org.jquijano.proyects.MovieManagement.persistence.repository;

import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

//    List<User> findByNameContaining(String name);

    Optional<User> findByUsername(String username);

    //Indica a JPA que la consulta puede tener efectos secundarios en el estado persistence de la base de datos
    @Modifying
    @Transactional
    int deleteByUsername(String username);
}
