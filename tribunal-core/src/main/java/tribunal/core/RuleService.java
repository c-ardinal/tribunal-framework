package tribunal.core;

import tribunal.tool.Logger;
import tribunal.tool.Scanner;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RuleService {


    private Logger log = new Logger(this.getClass().getName());
    private LinkedList<Rule> allRuleList;
    private File[] jars;
    private static RuleService instance;


    public RuleService(){
        this.allRuleList = new LinkedList<>();
        this.jars = Scanner.getInstance(log).getJars();
    }


    public static RuleService getInstance() {
        if (instance == null)
            instance = new RuleService();
        return instance;
    }


    public void registryRules() {
        for(File jar: jars) {
            try {
                List<Class<?>> plugins = Scanner.getInstance(log).scanPlugins(jar.getName(), RuleManager.class);
                for (Class<?> plugin : plugins) {
                    log.debug("Invoke method of : " + plugin.getName() + ".init()");
                    Method m = plugin.getMethod("loadRules");
                    LinkedList<Rule> result = ((LinkedList<Rule>) m.invoke(plugin.newInstance()));
                    this.allRuleList.addAll(result);
                }
            } catch (Exception e) {
                log.debug("" + e.getMessage());
            }
        }
    }


    public List containsRule(String context) {
        LinkedList<Object> result = null;
        for(Rule rule: this.allRuleList){
            result = new LinkedList<>();
            String regex = rule.getRuleRegex();
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(context);
            if (m.find()){
                result.add(rule.getClassName());
                for(int i=1; i<=m.groupCount(); i++)
                    result.add(m.group(i));
                return result;
            }
            else{
                result.add("NotFound");
            }
        }
        return result;
    }


    public List containsRule(String context, String executor) {
        LinkedList<Object> result = null;
        for (Rule rule : this.allRuleList) {
            result = new LinkedList<>();
            String regex = rule.getRuleRegex();
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(context);
            if (m.find()) {
                for(Object executable: rule.getExecutorIds()) {
                    if (executor.equals(executable.toString()) || executable.toString().equals("anyone")){
                        result.add(rule.getClassName());
                        for (int i = 1; i<=m.groupCount(); i++)
                            result.add(m.group(i));
                        return result;
                    }
                }
                result.add("PermissionError");
                return result;
            } else {
                result.add("NotFound");
            }
        }
        return result;
    }
}
