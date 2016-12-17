package com.gtriangle.admin.permission.auth.service.impl;

import java.util.List;

import com.gtriangle.admin.db.jdbc.BaseDaoSupport;
import com.gtriangle.admin.db.query.Ssqb;
import com.gtriangle.admin.permission.auth.entity.SysFunction;
import com.gtriangle.admin.permission.auth.service.IFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	
	
	@Autowired
	private BaseDaoSupport dao;
	
	@Transactional(readOnly = true)
	@Override
	public List<SysFunction> queryEmpFunction(Long empId) {
		Ssqb query
						= Ssqb.create("com.gtriangle.permission.auth.queryEmpFunction")
						  .setParam("empId", empId);
		return dao.findForList(query, SysFunction.class);
	}

}
