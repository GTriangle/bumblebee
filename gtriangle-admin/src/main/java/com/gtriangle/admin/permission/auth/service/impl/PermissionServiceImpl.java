package com.gtriangle.admin.permission.auth.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;

import com.gtriangle.admin.db.jdbc.BaseDaoSupport;
import com.gtriangle.admin.db.query.BaseQuery;
import com.gtriangle.admin.db.query.QueryBuilder;
import com.gtriangle.admin.db.query.Ssqb;
import com.gtriangle.admin.permission.auth.ShiroFilerChainManager;
import com.gtriangle.admin.permission.auth.service.IFunctionService;
import com.gtriangle.admin.permission.auth.service.IPermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gtriangle.admin.permission.auth.entity.MenuVo;
import com.gtriangle.admin.permission.auth.entity.PermUrl;
import com.gtriangle.admin.permission.auth.entity.PermUrlVo;
import com.gtriangle.admin.permission.auth.entity.SimpleMenuVo;
import com.gtriangle.admin.permission.auth.entity.SysFunction;


@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	private BaseDaoSupport dao;
	@Autowired
	private IFunctionService functionService;
	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;


	//@Transactional(readOnly = true)
	//@Override
	private List<PermUrlVo> queryAllPermUrlVo() {
		Ssqb query = Ssqb.create("com.gtriangle.permission.auth.queryAllPermUrl");
		return dao.findForList(query, PermUrlVo.class);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Set<String> queryEmpPerm(Long empId) {
		Set<String>	funcKeySet = new HashSet<String>();
		List<SysFunction> funcList = functionService.queryEmpFunction(empId);
		for(Iterator<SysFunction> it = funcList.iterator();it.hasNext();) {
			funcKeySet.add(it.next().getFuncKey());
		}
		
		return funcKeySet;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<MenuVo> queryEmpMenu(Long empId) {
		//获取用户所有拥有的权限
		List<SysFunction> funcList = functionService.queryEmpFunction(empId);
		Set<Long> funcIdSet = new HashSet<Long>();
		for(Iterator<SysFunction> it = funcList.iterator();it.hasNext();) {
			funcIdSet.add(it.next().getId().longValue());
		}
		//查询系统菜单
		QueryBuilder query = QueryBuilder.create()
				                         .setParam("permType", 1)
				                         .setOrderBys("pid", BaseQuery.OrderByType.asc)
				                         .setOrderBys("sorter", BaseQuery.OrderByType.desc);
		List<PermUrl> permUrlList = dao.queryList(query, PermUrl.class);
		Map<Long,MenuVo> menuMap = new LinkedHashMap<Long,MenuVo>();
		for(PermUrl permUrl : permUrlList) {
			Long funcId = permUrl.getFuncId().longValue();
			Long pid = permUrl.getPid();
			String menuName = permUrl.getPermName();
			if(pid == 0L) {
				//一级菜单
				if(funcId != 0L && !funcIdSet.contains(funcId)) {
					//菜单需要权限控制,无权限
					continue;
				} 
				if(funcId == 0L) {
					MenuVo m = new MenuVo(menuName, permUrl.getIcon(),permUrl.getIconHover());
					m.setRouter(permUrl.getRouter());
					menuMap.put(permUrl.getId(), m);
				} else {
					MenuVo m = new MenuVo(menuName, permUrl.getIcon(),permUrl.getIconHover());
					menuMap.put(permUrl.getId(), m);
				}
				
			} else {
				//二级菜单
				if(funcId != 0L && !funcIdSet.contains(funcId)) {
					//菜单需要权限控制,无权限
					continue;
				} 
				SimpleMenuVo subMenu = new SimpleMenuVo(menuName, permUrl.getRouter());
				MenuVo parentMenu = menuMap.get(pid);
				if(parentMenu != null) {
					if(StringUtils.isEmpty(parentMenu.getRouter())) {
						//一级菜单没有权限路径 则使用第一个二级菜单的权限路径
						parentMenu.setRouter(subMenu.getRouter());
					}
					parentMenu.getSub().add(subMenu);
				}
			}
		}
		Collection<MenuVo> menuColl = menuMap.values();
		return menuColl;
	}
	
	@PostConstruct
	public void initFilterChain() {
		shiroFilerChainManager.initFilterChains(queryAllPermUrlVo());
	}
	
	

}
