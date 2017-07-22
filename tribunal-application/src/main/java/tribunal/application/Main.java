package tribunal.application;

import tribunal.core.PluginService;
import tribunal.core.RuleService;
import tribunal.tool.Logger;

public class Main {
    public static void main(String[] args){
        Logger log = new Logger(Main.class.getName());
        PluginService.getInstance().init();
        RuleService.getInstance().registryRules();
        PluginService.getInstance().start();

        String[] targets = {"ほげ", "テスト", "来週から夏休み", "猫に小判"};

        for(String target: targets){
            log.debugln("===== " + target + " : " + RuleService.getInstance().containsRule(target) + " =====");
            String result = (String) PluginService.getInstance().call(RuleService.getInstance().containsRule(target));
            log.debugln(result + "\n");
        }
    }
}
