package tribunal.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import tribunal.tool.Logger;

import java.util.Iterator;
import java.util.LinkedList;


public class RuleManager {


    private Logger log = new Logger(this.getClass().getName());
    private LinkedList<Rule> pluginRuleList;


    public RuleManager(){
        this.pluginRuleList = new LinkedList<>();
    }


    public LinkedList<Rule> loadRules() throws Exception {
        log.debugln("Rules load start.");
        SAXReader reader = new SAXReader();
        String xmlDir = System.getProperty("user.dir") + "/../rules/" + this.getClass().getPackage().getName() + ".xml";
        log.debugln("Load from : " + xmlDir);
        Document document = reader.read(xmlDir);
        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Rule rule = new Rule();
            log.debugln("========== Load data ==========");
            Element element = (Element) i.next();
            rule.setRuleRegex(element.elementText("regex"));
            log.debugln("Regex : " + rule.getRuleRegex());
            rule.setClassName(element.elementText("class"));
            log.debugln("Class : " + rule.getClassName());
            LinkedList executors = new LinkedList<>();
            for(Object e: element.element("executors").elements("executor")){
                executors.add(((Element) e).getStringValue());
                log.debugln("Executor : " + ((Element) e).getStringValue());
            }
            rule.setExecutorIds(executors);
            this.pluginRuleList.add(rule);
            log.debugln("=================================");
        }
        log.debugln("Rules load finished.");
        return this.pluginRuleList;
    }


    public LinkedList<Rule> getRules(){
        return this.pluginRuleList;
    }
}
