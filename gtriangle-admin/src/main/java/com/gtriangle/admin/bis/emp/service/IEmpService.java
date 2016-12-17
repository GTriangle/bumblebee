package com.gtriangle.admin.bis.emp.service;


import com.gtriangle.admin.bis.emp.vo.UpdateEmpVo;
import com.gtriangle.admin.db.page.Pagination;
import com.gtriangle.admin.bis.emp.entity.Employee;
import com.gtriangle.admin.bis.emp.vo.SaveEmpVo;

import java.util.Map;

public interface IEmpService {

	Employee queryByEmpName(String empName);

	/**
	 * 查询员工列表
	 * @param pageNo
	 * @param pageSize
     * @return
     */
	Pagination queryEmplist(Integer pageNo, Integer pageSize);

	/**
	 * 保存员工
	 * @param saveEmpVo
	 * @param requestIp
	 * @return
	 */
	void saveEmp(SaveEmpVo saveEmpVo,String requestIp);

	/**
	 * 查询员工详情
	 * @param empId
	 * @return
     */
    Map<String,Object> queryEmpDetail(Long empId);

	/**
	 * 更新员工信息
	 * @param updateEmpVo
     */
    void updateEmp(UpdateEmpVo updateEmpVo);

	/**
	 * 更新当前用户密码
	 * @param empId
	 * @param oldPassword
	 * @param newPassword
	 */
    void udpatePassword(Long empId, String oldPassword, String newPassword);
}
