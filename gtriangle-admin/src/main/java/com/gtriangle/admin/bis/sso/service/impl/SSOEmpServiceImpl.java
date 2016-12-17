package com.gtriangle.admin.bis.sso.service.impl;

import com.gtriangle.admin.BizErrorConstant;
import com.gtriangle.admin.bis.sso.entity.SSOEmployee;
import com.gtriangle.admin.bis.sso.entity.SSOEmployeeApp;
import com.gtriangle.admin.db.jdbc.BaseDaoSupport;
import com.gtriangle.admin.db.query.QueryBuilder;
import com.gtriangle.admin.db.query.Ssqb;
import com.gtriangle.admin.exception.BizCoreRuntimeException;
import com.gtriangle.admin.GTriangleAppId;
import com.gtriangle.admin.bis.sso.service.ISSOEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Date;


@Service
@Transactional
public class SSOEmpServiceImpl implements ISSOEmpService {

    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Transactional(readOnly = true)
    @Override
    public SSOEmployee queryByEmpName(String empName) {

        QueryBuilder query = QueryBuilder.create()
                .setParam("empName", empName);
        return baseDaoSupport.query(query, SSOEmployee.class);
    }

    @Transactional(readOnly = true)
    @Override
    public SSOEmployee queryById(Long ssoEmpId) {
        return baseDaoSupport.queryById(ssoEmpId, SSOEmployee.class);
    }

    @Override
    public int saveSSOEmployee(SSOEmployee bean) {
        return baseDaoSupport.save(bean);
    }

    @Override
    public int updateSSOEmployee(SSOEmployee bean) {
        return baseDaoSupport.update(bean);
    }


    @Transactional(readOnly = true)
    @Override
    public SSOEmployeeApp querySSOEmpAppBySSOEmpId(Long ssoEmpId, String appId) {
        Assert.hasText(appId, "appId is required");
        QueryBuilder query = QueryBuilder.create()
                .setParam("ssoEmpId", ssoEmpId)
                .setParam("appId", appId);
        return baseDaoSupport.query(query, SSOEmployeeApp.class);
    }

    @Override
    public int saveSSOEmployeeApp(SSOEmployeeApp bean) {
        return baseDaoSupport.create(bean);
    }

    @Override
    public void updateLoginError(SSOEmployee employee, String ip) {
        Date now = new Timestamp(System.currentTimeMillis());

        Ssqb query = Ssqb.create("com.gtriangle.admin.sso.updateLoginInfo")
                .setParam("ssoEmpId", employee.getSsoEmpId())
                .setParam("errorTime", now)
                .setParam("errorCount", employee.getErrorCount() + 1)
                .setParam("errorIp", ip);

        baseDaoSupport.updateByMybatis(query);

    }

    @Override
    public void resetPassword(String EmpName, String smsCode, String password, String salt) {
        SSOEmployee employee = queryByEmpName(EmpName);
        SSOEmployee ss = new SSOEmployee();
        ss.setSsoEmpId(employee.getSsoEmpId());
        ss.setPassword(password);
        ss.setSalt(salt);
        updateSSOEmployee(ss);
    }

    @Override
    public void updatePassword(Long ssoEmpId, String password, String salt) {
        Assert.hasText(password, "password is required");
        Assert.hasText(salt, "password salt is required");
        SSOEmployee ss = new SSOEmployee();
        ss.setSsoEmpId(ssoEmpId);
        ss.setPassword(password);
        ss.setSalt(salt);
        updateSSOEmployee(ss);
    }

    private Boolean checkAccountIsExist(SSOEmployee employee, GTriangleAppId appId) {
        if (employee == null){
            return false;
        }else {
            SSOEmployeeApp app = querySSOEmpAppBySSOEmpId(employee.getSsoEmpId(), appId.value());
            return app != null;
        }
    }

    @Override
    public void updateLoginSuccess(SSOEmployee employee, String ip) {

        Date now = new Timestamp(System.currentTimeMillis());

        Ssqb query = Ssqb.create("com.gtriangle.admin.sso.updateLoginInfo")
                .setParam("ssoEmpId", employee.getSsoEmpId())
                .setParam("errorTime", null)
                .setParam("errorCount", 0)
                .setParam("errorIp", null)
                .setParam("loginCount", employee.getLoginCount() + 1)
                .setParam("lastLoginIp", ip)
                .setParam("lastLoginTime", now);

        baseDaoSupport.updateByMybatis(query);

    }

    @Override
    public SSOEmployeeApp checkAccountState(SSOEmployee ssoEmployee, String appId, Boolean isCheckExpired) throws BizCoreRuntimeException {

        SSOEmployeeApp app = querySSOEmpAppBySSOEmpId(ssoEmployee.getSsoEmpId(), appId);
        if (app == null)
            throw new BizCoreRuntimeException(BizErrorConstant.ACCOUNT_app_id_not_exist);

        if (isLocked(app)) {
            throw new BizCoreRuntimeException(BizErrorConstant.ACCOUNT_IS_Locked);
        }
        return app;
    }

    private boolean isLocked(SSOEmployeeApp ssoEmployeeApp) {
        if (ssoEmployeeApp.getLocked() > 0) {
            return true;
        } else {
            return false;
        }
    }


}
