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
        log.debug("Plugin init start.");
        List<Class<?>> plugins = Scanner.getInstance(log).scanPlugins(jarName, Plugin.class);
        for(Class<?> plugin: plugins){
            String className = plugin.getName();
            Object instance = plugin.newInstance();
            pluginMap.put(className, instance);

            log.debug("Invoke method of : " + plugin.getName() + ".init()");
            ((Plugin) instance).init();
            log.debug("Plugin init complete.");
        }
    }


    public void start() throws Exception {

    }


    public Object call(String className, Object[] args) throws Exception {
        Object result = null;
        Object inst = pluginMap.get(className);
        log.debug("Invoke method of : " + className + ".call()");
        for(Object arg: args)
            log.debug("Argument : " + arg);
        result = ((Plugin) inst).call(args);
        return result;
    }
}
