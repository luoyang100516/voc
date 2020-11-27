package com.lizhen.common.druidFilter;

import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.lizhen.common.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**druid sql解析辅助类
 * Created by sunyongfeng on 2019/4/30.
 */
public  class TableAliasVisitorUtil extends MySqlASTVisitorAdapter {
    private Map<String, String> aliasMap = new HashMap<>();

    @Override
    public boolean visit(SQLExprTableSource x) {
        String alias = x.getAlias();
        if(StringUtil.isBlank(alias)){
            alias = x.getName().getSimpleName();
        }
        //notfilter_ 开头的不需要拦截
        if(!alias.startsWith("notfilter_")){
            aliasMap.put(alias,x.getName().getSimpleName() );
        }

        return true;
    }

    public Map<String, String> getAliasMap() {
        return aliasMap;
    }
}