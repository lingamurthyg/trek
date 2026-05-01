package com.acme.cargotrak.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private String roleName;
    private String description;
    private Set permissions = new HashSet();

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Set getPermissions() { return permissions; }
    public void setPermissions(Set permissions) { this.permissions = permissions; }
}
