package tribunal.test;

import tribunal.core.Plugin;

public class Test extends Plugin {

    @Override
    public void init(){
        System.out.println(this.getClass().getName() + " - Plugin init.");
    }


    @Override
    public void call(String[] args){
        System.out.println(this.getClass().getName() + " - Test Plugin call.");
        for(String arg: args) {
            System.out.println(this.getClass().getName() + " - " + arg);
        }
    }

}
