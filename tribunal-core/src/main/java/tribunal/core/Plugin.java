package tribunal.core;


public class Plugin {


    private static Plugin instance;


    public void init(){

    }


    public static Plugin getInstance(){
        if(instance == null){
            instance = new Plugin();
        }
        return instance;
    }


    public void call(String[] args){

    }

}
