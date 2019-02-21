package datacenter.crudreposity.crontab;


import org.springframework.scheduling.annotation.Scheduled;

public class demo {


    //定义定时任务方法
    @Scheduled(cron = "0 8 * * ?")
    public void crontab(){

      System.out.println("start executing...");
    }

}
