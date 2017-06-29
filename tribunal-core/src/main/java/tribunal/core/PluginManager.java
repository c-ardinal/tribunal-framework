package tribunal.core;


import tribunal.tool.Logger;
import tribunal.tool.Scanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * プラグインの管理を行う
 */
public class PluginManager {

    private Logger log = new Logger(this.getClass().getName()); // ロガー
    private Map<String, Object> pluginMap = new HashMap<>();    // Pluginインスタンス保存用


    public void init(String jarName){
        log.debug("Plugin init start.");
        try {
            List<Class<?>> plugins = Scanner.getInstance(log).scanPlugins(jarName, Plugin.class);
            for(Class<?> plugin: plugins){
                String className = plugin.getName();
                Object instance = plugin.newInstance();
                pluginMap.put(className, instance);

                log.debug("Invoke method of : " + plugin.getName() + ".init()");
                Method m = plugin.getMethod("init");
                m.invoke(instance);
            }
        } catch (Exception e) {
            log.debug("" + e.getMessage());
        }
        log.debug("Plugin init complete.");
    }


    public void start(){

    }


    public Object call(String className, Object[] args) {
        Object result = null;
        try {
            Object inst = pluginMap.get(className);
            Class<?> cl = Class.forName(className);
            log.debug("Invoke method of : " + cl.toString() + ".call()");
            for(Object arg: args)
                log.debug("Argument : " + arg);
            Method m = cl.getMethod("call", new Class[]{Object[].class});
            result = m.invoke(inst, new Object[]{args});
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }
}
