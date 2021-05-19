package jmu.shijh.community_system.common.sqlbuilder;

import jmu.shijh.community_system.common.util.Str;
import org.apache.ibatis.jdbc.SQL;

@SuppressWarnings("unchecked")
public abstract class BaseUpInSQL<T> extends SQL {
    protected String tableName;
    private Boolean camelToUnderscore = null;

    public T useCamelToUnderscore() {
        this.camelToUnderscore = true;
        return (T)this;
    }

    protected boolean isCamelToUnderscore() {
        if(camelToUnderscore != null) {
            return camelToUnderscore;
        }
        return SqlBuilderConfig.useCamelToUnderscore;
    }
}
