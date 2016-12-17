package com.gtriangle.admin.permission.auth.service;

import java.util.List;

import com.gtriangle.admin.permission.auth.entity.SysFunction;

public interface IFunctionService {
	
	/**
	 * 用户权限列表
	 * @param empId
	 * @return
	 */
	List<SysFunction> queryEmpFunction(Long empId);
}
