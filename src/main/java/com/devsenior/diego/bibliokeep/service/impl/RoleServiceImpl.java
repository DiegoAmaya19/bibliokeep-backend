package com.devsenior.diego.bibliokeep.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsenior.diego.bibliokeep.model.entity.Role;
import com.devsenior.diego.bibliokeep.repository.RoleRepository;
import com.devsenior.diego.bibliokeep.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public Set<Role> findAllByIdList(Set<Long> rolesIds) {
        return roleRepository.findAllById(rolesIds).stream().collect(Collectors.toSet());
    }
}
