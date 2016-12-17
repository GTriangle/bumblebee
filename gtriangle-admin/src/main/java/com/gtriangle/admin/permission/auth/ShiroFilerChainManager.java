package com.gtriangle.admin.permission.auth;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.gtriangle.admin.permission.auth.entity.PermUrlVo;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 基于动态URL的shiro扩展
* @author Brian   
* @date 2016年2月1日 下午5:46:48
 */
@Service
public class ShiroFilerChainManager {

    @Autowired
    private DefaultFilterChainManager filterChainManager;

    private Map<String, NamedFilterList> defaultFilterChains;

    @PostConstruct
    public void init() {
        defaultFilterChains = new HashMap<String, NamedFilterList>(filterChainManager.getFilterChains());
    }

    public void initFilterChains(List<PermUrlVo> urlFilters) {
        //1、首先删除以前老的filter chain并注册默认的
        filterChainManager.getFilterChains().clear();
        if(defaultFilterChains != null) {
            filterChainManager.getFilterChains().putAll(defaultFilterChains);
        }

        //2、循环URL Filter 注册filter chain
        for (PermUrlVo urlFilter : urlFilters) {
            String url = urlFilter.getPermUrl();
            //注册roles filter
//            if (!StringUtils.isEmpty(urlFilter.getRoles())) {
//                filterChainManager.addToChain(url, "roles", urlFilter.getRoles());
//            }
            //注册perms filter
            if (!StringUtils.isEmpty(urlFilter.getFuncKey())) {
                filterChainManager.addToChain("/mgt" + url, "perms", urlFilter.getFuncKey());
            }
        }


    }

}
