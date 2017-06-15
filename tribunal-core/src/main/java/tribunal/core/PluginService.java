package tribunal.core;

import tribunal.tool.Logger;
import tribunal.tool.Scanner;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PluginService {


    private Logger log = new Logger(this.getClass().getName());
    private static PluginService instance;
    private Map<String, Object> pluginManagerMap = new HashMap<>();
    File[] jars;


    /**
     * PluginServiceコンストラクタ
     */
    public PluginService(){
        // Plugin Jarのスキャンを行う
        this.jars = Scanner.getInstance(log).getJars();
    }


    /**
     * PluginServiceのシングルトンインスタンスを返す
     * @return
     */
    public static PluginService getInstance() {
        if (instance == null)
            instance = new PluginService();
        return instance;
    }


    /**
     * プラグインの初期化を行う
     */
    public void init() {
        this.invokePluginManager("init");
    }


    /**
     * プラグインの実行を開始する
     */
    public void start() {
        this.invokePluginManager("start");
    }


    /**
     * プラグインを呼び出す
     */
    public void call(List list) {
        try {
            if (list.get(0).equals("NotFound")) {
                log.debug("Match rule not found.");
            }
            else if(list.get(0).equals("PermissionError")){
                log.debug("Permission error.");
            }
            else{
                String className = list.get(0).toString();
                log.debug(className);
                list.remove(0);
                String[] args = (String[]) list.toArray(new String[list.size()]);
                PluginManager inst = (PluginManager) pluginManagerMap.get(Class.forName(className).getPackage().getName());
                inst.call(className, args);
            }
        }catch(Exception e){
            log.error(e);
        }
    }


    /**
     * プラグインマネージャを操作する
     * @param methodName
     */
    private void invokePluginManager(String methodName) {
        for(File jar: jars) {
            try {
                List<Class<?>> pms = Scanner.getInstance(log).scanPlugins(jar.getName(), PluginManager.class);
                for(Class<?> pm: pms){
                    Object inst;
                    if(!pluginManagerMap.containsKey(pm.getPackage().getName())){
                        String packageName = pm.getPackage().getName();
                        inst = pm.newInstance();
                        pluginManagerMap.put(packageName, inst);
                    }
                    else{
                        inst = pluginManagerMap.get(pm.getPackage().getName());
                    }

                    log.debug("Invoke method of : " + pm.toString() + "." + methodName +"()");
                    if(methodName.equals("init")) {
                        Method m = pm.getMethod(methodName, new Class[]{String.class});
                        m.invoke(inst, jar.getName());
                    }
                    else if(methodName.equals("start")) {
                        Method m = pm.getMethod(methodName);
                        m.invoke(inst);
                    }
                }
            } catch (Exception e) {
                log.debug("Load failed : " + jar.getName());
                e.printStackTrace();
            }
        }
    }
}
