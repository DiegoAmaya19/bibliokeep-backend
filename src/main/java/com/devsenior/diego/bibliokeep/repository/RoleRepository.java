package com.devsenior.diego.bibliokeep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsenior.diego.bibliokeep.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
    
}