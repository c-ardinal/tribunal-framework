package tribunal.test;

import tribunal.core.Plugin;

public class Test2 extends Plugin {

    @Override
    public void init(){
        System.out.println(this.getClass().getName() + " - Plugin init.");
    }


    @Override
    public void call(Object[] args){
        System.out.println(this.getClass().getName() + " - Test2 Plugin call.");
    }

}
