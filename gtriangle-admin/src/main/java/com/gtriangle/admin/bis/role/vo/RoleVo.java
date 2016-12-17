package com.gtriangle.admin.bis.role.vo;

/**
 * Created by gaoyan on 2016/11/16.
 */
public class RoleVo {

    public RoleVo() {
    }

    public RoleVo(Long id, String roleName, String roleMemo) {
        this.id = id;
        this.roleName = roleName;
        this.roleMemo = roleMemo;

    }

    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleMemo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleMemo() {
        return roleMemo;
    }

    public void setRoleMemo(String roleMemo) {
        this.roleMemo = roleMemo;
    }
}
