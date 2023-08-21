package com.yayaveli.inventorymanagement.interceptors;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class Interceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
        // System.out.println(sql);
        // if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
        // // select utilisateur0_.
        // final String entityName = sql.substring(7, sql.indexOf("."));
        // final String companyId = MDC.get("companyId");
        // if (StringUtils.hasLength(entityName)
        // && !entityName.toLowerCase().contains("company")
        // && !entityName.toLowerCase().contains("user_role")
        // && !entityName.toLowerCase().contains("user")
        // && StringUtils.hasLength(companyId)) {
        // if (sql.contains("where")) {
        // System.out.println("--------------IN------" + companyId + "---------");
        // sql += " and " + entityName + ".company_id = " + companyId;
        // } else {
        // sql += " where " + entityName + ".company_id = " + companyId;
        // }

        // System.out.println("---------" + sql + "--------");
        // }
        // }
        return super.onPrepareStatement(sql);
    }
}
