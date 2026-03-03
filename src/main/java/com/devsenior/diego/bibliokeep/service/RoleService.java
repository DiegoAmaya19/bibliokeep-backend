package com.devsenior.diego.bibliokeep.service;

import java.util.Set;
import com.devsenior.diego.bibliokeep.model.entity.Role;

public interface RoleService {

    Set<Role> findAllByIdList(Set<Long> rolesIds);

}
