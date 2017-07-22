package tribunal.core;

import tribunal.tool.Logger;
import tribunal.tool.Scanner;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PluginService {


    private Logger log = new Logger(this.getClass().getName());
    private static PluginService instance;
    private static Map<String, Object> pluginManagerMap;
    File[] jars;


    /**
     * PluginServiceコンストラクタ
     */
    public PluginService(){
        // Plugin Jarのスキャンを行う
        this.jars = Scanner.getInstance(log).getJars();
        this.pluginManagerMap = new HashMap<>();
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
    public Object call(List list) {
        Object result = null;
        try {
            if (list.get(0).equals("NotFound")) {
                log.debugln("Match rule not found.");
                result = "Match rule not found.";
            }
            else if(list.get(0).equals("PermissionError")){
                log.debugln("Permission error.");
                result = "Permission error.";
            }
            else{
                String className = list.get(0).toString();
                String[] parts = className.split("\\.", 0);
                String packageName = "";
                for(int i=0; i<parts.length-1; i++){
                    packageName += parts[i] + ".";
                }
                packageName = packageName.substring(0, packageName.length()-1);
                log.debugln(packageName);
                list.remove(0);
                Object[] args = list.toArray(new Object[list.size()]);
                PluginManager inst = (PluginManager) pluginManagerMap.get(packageName);
                result = inst.call(className, args);
            }
        }catch(Exception e){
            log.error(e);
            result = e.getMessage();
        }
        return result;
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
                        log.debugln("Generate instance: " + inst.getClass().getName());
                    }
                    else{
                        inst = pluginManagerMap.get(pm.getPackage().getName());
                        log.debugln("Instance call: " + inst.getClass().getName());
                    }

                    log.debugln("Invoke method of : " + pm.toString() + "." + methodName +"()");
                    if(methodName.equals("init"))
                        ((PluginManager) inst).init(jar.getName());
                    else if(methodName.equals("start"))
                        ((PluginManager) inst).start();
                }
            } catch (Exception e) {
                log.debugln("Load failed : " + jar.getName());
                log.error(e);
            }
        }
    }
}
