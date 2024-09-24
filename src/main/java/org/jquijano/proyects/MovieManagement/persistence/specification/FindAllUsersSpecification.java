package org.jquijano.proyects.MovieManagement.persistence.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jquijano.proyects.MovieManagement.dto.response.UserSearchCriteria;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllUsersSpecification implements Specification<User> {

    private UserSearchCriteria userSearchCriteria;

    public FindAllUsersSpecification(UserSearchCriteria userSearchCriteria) {
        this.userSearchCriteria = userSearchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(userSearchCriteria.name())) {
            Predicate nameLike = criteriaBuilder.like(root.get("name"), "%" + userSearchCriteria.name() + "%");
            predicates.add(nameLike);
        }

        if (StringUtils.hasText(userSearchCriteria.username())) {
            Predicate usernameLike = criteriaBuilder.like(root.get("username"), "%" + userSearchCriteria.username() + "%");
            predicates.add(usernameLike);
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
