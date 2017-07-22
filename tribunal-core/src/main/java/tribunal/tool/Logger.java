package tribunal.tool;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    String packageName;

    public Logger(String packageName){
        this.packageName = packageName;
    }

    public void print(String message){
        System.out.print(StringColor.RESET + message);
    }

    public void println(String message){
        System.out.println(StringColor.RESET + message);
    }

    public void debug(String message){
        this.debug(message, "");
    }

    public void debug(String message, String color){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.print(color + "[" + date + "] <" + packageName + "> " + message);
    }

    public void debugln(String message){
        this.debugln(message, "");
    }

    public void debugln(String message, String color){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.println(color + "[" + date + "] <" + packageName + "> " + message);
    }

    public void reset(){
        this.print(StringColor.RESET);
    }

    public void error(String message, Exception e){
        this.debugln(message, StringColor.RED);
        this.debug("", StringColor.RED);
        e.printStackTrace();
    }

    public void error(Exception e){
        this.debug("", StringColor.RED);
        e.printStackTrace();
    }

}
