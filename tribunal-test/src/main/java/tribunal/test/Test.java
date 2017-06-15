package tribunal.test;

import tribunal.core.Plugin;

public class Test extends Plugin {

    @Override
    public void init(){
        System.out.println(this.getClass().getName() + " - Plugin init.");
    }


    @Override
    public void call(Object[] args){
        System.out.println(this.getClass().getName() + " - Test Plugin call.");
        for(Object arg: args) {
            System.out.println(this.getClass().getName() + " - " + arg);
        }
    }

}
