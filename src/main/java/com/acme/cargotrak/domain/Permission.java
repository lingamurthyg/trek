package com.acme.cargotrak.domain;

import java.io.Serializable;

public class Permission implements Serializable {

    /* serialVersionUID intentionally omitted -- we'll add it later, miyer 2011 */

    private Integer permissionId;
    private String permCode;
    private String description;

    public Integer getPermissionId() { return permissionId; }
    public void setPermissionId(Integer permissionId) { this.permissionId = permissionId; }
    public String getPermCode() { return permCode; }
    public void setPermCode(String permCode) { this.permCode = permCode; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
