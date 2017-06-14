package tribunal.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
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


    public LinkedList<Rule> loadRules(){
        log.debug("Rules load start.");
        SAXReader reader = new SAXReader();
        try {
            String xmlDir = System.getProperty("user.dir") + "/../rules/" + this.getClass().getPackage().getName() + ".xml";
            log.debug("Load from : " + xmlDir);
            Document document = reader.read(xmlDir);
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Rule rule = new Rule();
                log.debug("========== Load data ==========");
                Element element = (Element) i.next();
                rule.setRuleRegex(element.elementText("regex"));
                log.debug("Regex : " + rule.getRuleRegex());
                rule.setClassName(element.elementText("class"));
                log.debug("Class : " + rule.getClassName());
                LinkedList executors = new LinkedList<>();
                for(Object e: element.element("executors").elements("executor")){
                    executors.add(((Element) e).getStringValue());
                    log.debug("Executor : " + ((Element) e).getStringValue());
                }
                rule.setExecutorIds(executors);
                this.pluginRuleList.add(rule);
                log.debug("=================================");
            }
        } catch (DocumentException e) {
            log.error(e);
        }
        log.debug("Rules load finished.");
        return this.pluginRuleList;
    }


    public LinkedList<Rule> getRules(){
        return this.pluginRuleList;
    }
}
