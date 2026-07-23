package app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ScheduledTaskService {

    //Runs every 5 second
//    @Scheduled(fixedRate = 5000)
//    public void runEveryFiveSeconds() {
//        log.info("Fixed rate: {}", LocalDateTime.now());
//    }

    //Runs every 5 second
//    @Scheduled(fixedDelay = 5000)
//    public void runWithFiveSecondsDelay() {
//        log.info("Fixed rate: {}", LocalDateTime.now());
//    }

    //Wait 10 seconds after application startup, then run every 15 seconds
//    @Scheduled(initialDelay = 10000, fixedRate = 15000)
//    public void runWithInitalDelay() {
//        log.info("Fixed rate: {}", LocalDateTime.now());
//    }

//    @Scheduled(cron = "*/5 * * * * *")
//    public void cronExpression() throws InterruptedException {
//        log.info("Schedule 1: {}", LocalDateTime.now());
//        Thread.sleep(50000);
//    }

//    @Scheduled(cron = "*/5 * * * * *")
//    public void cronExpression2() {
//        log.info("Schedule 2: {}", LocalDateTime.now());
//    }



//    0 * * * * *	    Every minute
//
//    0 */5 * * * *	    Every 5 minutes
//
//    0 0 * * * *	    Every hour
//
//    0 0 9 * * *	    Every day at 9:00 AM
//
//    0 0 0 * * MON	    Every Monday at midnight
//
//    0 0 12 1 * *	    Noon on the 1st of every month

}
