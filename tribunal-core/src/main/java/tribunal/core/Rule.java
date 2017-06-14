package tribunal.core;

import java.util.List;

public class Rule {


    private String ruleRegex;
    private String className;
    private List ExecutorIds;


    public String getRuleRegex() {
        return ruleRegex;
    }

    public void setRuleRegex(String ruleRegex) {
        this.ruleRegex = ruleRegex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List getExecutorIds() {
        return ExecutorIds;
    }

    public void setExecutorIds(List executorIds) {
        ExecutorIds = executorIds;
    }
}
