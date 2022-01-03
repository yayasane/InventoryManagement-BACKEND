package com.yayaveli.inventorymanagement.interceptors;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class Interceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
        System.out.println(sql);
        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
            // select utilisateur0_.
            final String entityName = sql.substring(7, sql.indexOf("."));
            final String companyId = MDC.get("companyId");
            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("company")
                    && !entityName.toLowerCase().contains("user_roles")
                    && StringUtils.hasLength(companyId)) {

                if (sql.contains("where")) {
                    sql += " and company_id = 1";
                } else {
                    sql += " where company_id = 1";
                }
            }
        }
        return super.onPrepareStatement(sql);
    }
}
