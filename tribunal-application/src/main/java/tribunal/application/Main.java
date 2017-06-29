package tribunal.application;

import tribunal.core.PluginService;
import tribunal.core.RuleService;

public class Main {
    public static void main(String[] args){
        PluginService.getInstance().init();
        RuleService.getInstance().registryRules();
        PluginService.getInstance().start();

        System.out.println("\n===== ほげ : " + RuleService.getInstance().containsRule("ほげ") + " =====");
        PluginService.getInstance().call(RuleService.getInstance().containsRule("ほげ"));

        System.out.println("\n===== テスト : " + RuleService.getInstance().containsRule("テスト") + " =====");
        PluginService.getInstance().call(RuleService.getInstance().containsRule("テスト"));

        System.out.println("\n===== 来週から夏休み : " + RuleService.getInstance().containsRule("来週から夏休み") + " =====");
        PluginService.getInstance().call(RuleService.getInstance().containsRule("来週から夏休み"));

        System.out.println("\n===== 猫に小判 : " + RuleService.getInstance().containsRule("猫に小判") + " =====");
        String result = (String)PluginService.getInstance().call(RuleService.getInstance().containsRule("猫に小判"));
        System.out.println(result);
    }
}
