package com.acme.cargotrak.service;

import java.util.Iterator;
import java.util.List;

import com.acme.cargotrak.dao.RoleDao;
import com.acme.cargotrak.dao.UserDao;
import com.acme.cargotrak.domain.Role;
import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.util.MD5Util;
import com.acme.cargotrak.util.Util;

public class UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    public void setUserDao(UserDao userDao) { this.userDao = userDao; }
    public void setRoleDao(RoleDao roleDao) { this.roleDao = roleDao; }

    public List listAll() { return userDao.findAll(User.class); }

    public List listActive() { return userDao.findActive(); }

    public User findById(Integer id) { return (User) userDao.findById(User.class, id); }

    public User findByUsername(String name) { return userDao.findByUsername(name); }

    public List search(String fragment) { return userDao.searchByName(Util.nvl(fragment, "")); }

    public Integer createUser(String username, String password, String email, String fullName) {
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(MD5Util.hash(password));
        u.setEmail(email);
        u.setFullName(fullName);
        u.setActiveFlag("Y");
        u.setCreatedDate(Util.formatDate(Util.today()));
        return (Integer) userDao.save(u);
    }

    public void updateUser(User u) { userDao.update(u); }

    public void deactivate(Integer userId) {
        User u = (User) userDao.findById(User.class, userId);
        if (u == null) return;
        u.setActiveFlag("N");
        userDao.update(u);
    }

    public void assignRole(Integer userId, String roleName) {
        User u = (User) userDao.findById(User.class, userId);
        Role r = roleDao.findByName(roleName);
        if (u == null || r == null) return;
        boolean already = false;
        Iterator it = u.getRoles().iterator();
        while (it.hasNext()) {
            Role rr = (Role) it.next();
            if (rr.getRoleId().equals(r.getRoleId())) { already = true; break; }
        }
        if (!already) {
            u.getRoles().add(r);
            userDao.update(u);
        }
    }

    public void removeRole(Integer userId, String roleName) {
        User u = (User) userDao.findById(User.class, userId);
        if (u == null) return;
        Iterator it = u.getRoles().iterator();
        while (it.hasNext()) {
            Role rr = (Role) it.next();
            if (roleName.equals(rr.getRoleName())) { it.remove(); break; }
        }
        userDao.update(u);
    }

    public List listRoles() { return roleDao.findAll(Role.class); }
}
