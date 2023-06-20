package com.example.sneaker_shop.Repositories;

import com.example.sneaker_shop.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r.id FROM Role r WHERE r.name = ?1")
    Long getRoleIdByName(String roleName);

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    public Role getRoleByName(@Param("name") String name);
}
