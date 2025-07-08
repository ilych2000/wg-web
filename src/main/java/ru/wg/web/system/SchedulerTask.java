package ru.wg.web.system;

import java.util.Timer;
import java.util.TimerTask;

public abstract class SchedulerTask {

    private Timer timer;

    public abstract void Process();

    public SchedulerTask(long delay) {
        this(delay, delay, false);
    }

    public SchedulerTask(long delay, long period, boolean fixedRate) {
        timer = new Timer();

        if (fixedRate) {
            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    Process();
                }
            }, delay, period);
        } else {
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    Process();
                }
            }, delay, period);
        }

    }

    public synchronized void stopDaemon() {
        timer.cancel();
    }
}
