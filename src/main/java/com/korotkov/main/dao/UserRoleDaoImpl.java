package com.korotkov.main.dao;

import com.korotkov.main.enums.UserRoleEnum;
import com.korotkov.main.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleDaoImpl implements UserRoleDao{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){this.sessionFactory = sessionFactory;}

    @Override
    public void create(UserRole userRole){
        Session session = sessionFactory.getCurrentSession();
        session.persist(userRole);
    }

    @Override
    public void update(UserRole userRole){
        Session session = sessionFactory.getCurrentSession();
        session.update(userRole);
    }

    @Override
    public UserRole getById(Long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(UserRole.class, id);
    }

    @Override
    public UserRole findByUserRoleName(String userRoleName){
        Session session = sessionFactory.getCurrentSession();
        return (UserRole) session.createQuery("select a from UserRole a where a.name = :userRoleName")
                .setParameter("userRoleName", userRoleName)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserRole> getAllRolesWithoutCurrent(Long userRoleId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select ur from UserRole ur where ur.id <> :userRoleId and ur.name <> :levelOne order by ur.id")
                .setParameter("userRoleId", userRoleId)
                .setParameter("levelOne", String.valueOf(UserRoleEnum.LEVEL_ONE))
                .list();
    }
}
