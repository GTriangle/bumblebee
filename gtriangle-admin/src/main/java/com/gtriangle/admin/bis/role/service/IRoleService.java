package com.gtriangle.admin.bis.role.service;

import com.gtriangle.admin.bis.role.vo.RoleFunctionResultVo;
import com.gtriangle.admin.bis.role.vo.RoleVo;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    /**
     * 查询角色列表
     * @return
     */
    List<RoleVo> queryRoleList();

    /**
     * 设置 查询角色 权限关系
     * @param roleId
     * @return
     */
    List<RoleFunctionResultVo> queryRoleFuncList(Long roleId);

    /**
     * 保存角色信息
     * @param roleName
     */
    void saveRole(String roleName);

    /**
     * 更新角色信息
     * @param roleId
     * @param roleName
     */
    void updateRole(Long roleId, String roleName);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteRole(Long roleId);

    /**
     * 校验待删除的角色 员工的占用数量
     * @param roleId
     * @return
     */
    int validateEmpRole(Long roleId);


    void updateRoleFunc(Long roleId, List<Long> funcIds);
}
