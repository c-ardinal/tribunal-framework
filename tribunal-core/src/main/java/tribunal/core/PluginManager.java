package tribunal.core;


import tribunal.tool.Logger;
import tribunal.tool.Scanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * プラグインの管理を行う
 */
public class PluginManager {

    private Logger log = new Logger(this.getClass().getName()); // ロガー
    private Map<String, Object> pluginMap = new HashMap<>();    // Pluginインスタンス保存用


    public void init(String jarName) throws Exception {
        log.debugln("Plugin init start.");
        List<Class<?>> plugins = Scanner.getInstance(log).scanPlugins(jarName, Plugin.class);
        for(Class<?> plugin: plugins){
            String className = plugin.getName();
            Object instance = plugin.newInstance();
            pluginMap.put(className, instance);

            log.debugln("Invoke method of : " + plugin.getName() + ".init()");
            ((Plugin) instance).init();
            log.debugln("Plugin init complete.");
        }
    }


    public void start() throws Exception {

    }


    public Object call(String className, Object[] args) throws Exception {
        Object result = null;
        Object inst = pluginMap.get(className);
        log.debugln("Invoke method of : " + className + ".call()");
        for(Object arg: args)
            log.debugln("Argument : " + arg);
        result = ((Plugin) inst).call(args);
        return result;
    }
}
