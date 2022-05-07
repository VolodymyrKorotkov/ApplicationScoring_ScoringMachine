package com.korotkov.main.service.userRole;

import com.korotkov.main.model.UserAccount;
import com.korotkov.main.model.UserRole;

import java.util.List;

public interface UserRoleService {
    void create(UserRole userRole);
    void update(UserRole userRole);
    UserRole getById(Long id);
    UserRole findByUserRoleName(String userRoleName);
    List<UserRole> getAllRolesWithoutCurrent(UserAccount userAccount);
}
