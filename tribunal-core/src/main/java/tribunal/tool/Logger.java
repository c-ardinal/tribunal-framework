package tribunal.tool;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    String packageName;

    public Logger(String packageName){
        this.packageName = packageName;
    }

    public void debug(String message){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.println("[" + date + "] <" + packageName + "> " + message);
    }

    public void error(String message, Exception e){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.println("[" + date + "] <" + packageName + "> " + message);
        date = d.format(new Date());
        System.out.println("[" + date + "] <" + packageName + "> " + e.getMessage());
    }

    public void error(Exception e){
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date = d.format(new Date());
        System.out.print("[" + date + "] <" + packageName + "> ");
        e.printStackTrace();
    }

}
