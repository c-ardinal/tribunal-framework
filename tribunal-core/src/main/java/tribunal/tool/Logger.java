package tribunal.tool;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    String packageName;

    public Logger(String packageName){
        this.packageName = packageName;
    }

    public void print(Object message){
        System.out.print(StringColor.RESET + message);
    }

    public void println(Object message){
        System.out.println(StringColor.RESET + message);
    }

    public void debug(Object message){
        this.debug(message, StringColor.RESET);
    }

    public void debug(Object message, String color){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.print(color + "[" + date + "] <" + packageName + "> " + message);
    }

    public void debugln(Object message){
        this.debugln(message, StringColor.RESET);
    }

    public void debugln(Object message, String color){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.println(color + "[" + date + "] <" + packageName + "> " + message);
    }

    public void reset(){
        this.print(StringColor.RESET);
    }

    public void error(Object message, Exception e){
        this.debugln(message, StringColor.RED);
        this.debug("", StringColor.RED);
        e.printStackTrace();
        this.reset();
    }

    public void error(Exception e){
        this.debug("", StringColor.RED);
        e.printStackTrace();
        this.reset();
    }

}
