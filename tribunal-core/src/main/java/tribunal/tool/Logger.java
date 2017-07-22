package tribunal.tool;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    String packageName;

    public Logger(String packageName){
        this.packageName = packageName;
    }

    public void print(Object message){
        System.out.print(message);
    }

    public void println(Object message){
        System.out.println(message);
    }

    public void debug(Object message){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.print("[" + date + "] <" + packageName + "> " + message);
    }

    public void debugln(Object message){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.println("[" + date + "] <" + packageName + "> " + message);
    }

    public void error(Object message, Exception e){
        this.debugln(message);
        this.debug("");
        e.printStackTrace();
    }

    public void error(Exception e){
        this.debug("");
        e.printStackTrace();
    }

}
