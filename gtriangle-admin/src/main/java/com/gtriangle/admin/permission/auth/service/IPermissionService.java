package com.gtriangle.admin.permission.auth.service;

import java.util.Collection;
import java.util.Set;

import com.gtriangle.admin.permission.auth.entity.MenuVo;

public interface IPermissionService {
	 
	 
	 /**
	  * 获取用户权限
	  * @param empId
	  * @return
	  */
	 Set<String> queryEmpPerm(Long empId);
	 /**
	  * 获取用户系统菜单
	  * @param empId
	  * @return
	  */
	 Collection<MenuVo> queryEmpMenu(Long empId);
}
