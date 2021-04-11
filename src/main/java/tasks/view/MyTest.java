package tasks.view;

import tasks.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyTest {
    public static void f(List<Task> tasks, Date start, Date end){
        ArrayList<Task> incomingTasks = new ArrayList<>();
        for (Task t : tasks) {
            Date nextTime = t.nextTimeAfter(start);
            boolean v1 = nextTime != null;
            System.out.println("V1 = " + v1);
            if (v1) {
                boolean v2 = nextTime.before(end);
                boolean v3 = nextTime.equals(end);
                System.out.println("V2 = " + v2);
                System.out.println("V3 = " + v3);
                if (v2 || v3) {
                    incomingTasks.add(t);
                    System.out.println(t.getTitle());
                }
            }
        }
    }
    // nextTime > end
    // start < nextTime
}
