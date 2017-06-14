package tribunal.test;

import tribunal.core.PluginManager;

public class TestPluginManager extends PluginManager{

    @Override
    public void start() {
        System.out.println(this.getClass().getName() + " - System start.");
    }
}
