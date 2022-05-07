package com.korotkov.main.service.userRole;

import com.korotkov.main.dao.UserRoleDao;
import com.korotkov.main.model.UserAccount;
import com.korotkov.main.model.UserRole;
import com.korotkov.main.service.userRole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleDao userRoleDao;

    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao){
        this.userRoleDao = userRoleDao;
    }

    @Override
    @Transactional
    public void create(UserRole userRole){userRoleDao.create(userRole);}

    @Override
    @Transactional
    public void update(UserRole userRole){userRoleDao.update(userRole);}

    @Override
    @Transactional
    public UserRole getById(Long id){return userRoleDao.getById(id);}

    @Override
    @Transactional
    public UserRole findByUserRoleName(String userRoleName){return userRoleDao.findByUserRoleName(userRoleName);}

    @Override
    @Transactional
    public List<UserRole> getAllRolesWithoutCurrent(UserAccount userAccount){
        return userRoleDao.getAllRolesWithoutCurrent(userAccount.getRole().getId());
    }

}
