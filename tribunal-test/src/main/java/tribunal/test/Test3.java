package tribunal.test;

import tribunal.core.Plugin;

public class Test3 extends Plugin {

    @Override
    public void init(){
        System.out.println(this.getClass().getName() + " - Plugin init.");
    }


    @Override
    public Object call(Object[] args){
        return this.getClass().getName() + " - Test3 Plugin call.";
    }

}
