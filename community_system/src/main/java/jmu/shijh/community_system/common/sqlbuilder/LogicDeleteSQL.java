package jmu.shijh.community_system.common.sqlbuilder;

import ch.qos.logback.classic.db.names.TableName;
import org.apache.ibatis.jdbc.SQL;

public class LogicDeleteSQL extends QuerySQL {

    public LogicDeleteSQL(Object dto, String tableName) {
        super(dto,tableName);
        sql = new SQL();
        sql.UPDATE(tableName);
    }

    public LogicDeleteSQL delete(String column, Object flagVal) {
        sql.SET(column + "=${" + flagVal.toString() + "}");
        return this;
    }

}
