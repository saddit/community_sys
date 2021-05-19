package jmu.shijh.community_system.common.sqlbuilder;

public class ConditionSQL extends BaseQuerySQL<ConditionSQL>{
    public ConditionSQL(Object dto, String tableName) {
        super(dto, tableName);
    }
    public ConditionSQL c(Condition condition) {
        condition.removeLogic();
        return conditions(condition);
    }

    public ConditionSQL cs(Conditions condition) {
        return conditions(condition.toArray());
    }
}
