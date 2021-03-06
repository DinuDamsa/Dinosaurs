package tasks.model;

import org.apache.log4j.Logger;
import tasks.exceptions.ValidationException;
import tasks.services.TaskIO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task implements Serializable, Cloneable {
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private long interval;
    private boolean active;

    private static final Logger log = Logger.getLogger(Task.class.getName());
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public SimpleDateFormat getDateFormat(){
        return sdf;
    }
    public Task(String title, Date time){
        if (time.getTime() < 0) {
            log.error("time below bound");
            throw new ValidationException("Time cannot be negative");
        }
        this.title = title;
        this.time = time;
        this.start = time;
        this.end = time;
    }
    public Task(String title, Date start, Date end, long interval){
        if (title == null){
            log.error("Title is null!");
            throw new ValidationException("Title must be a String!");
        }
        if (title.length() <= 0 || title.length() > 255){
            log.error("Title length out of range!");
            throw new ValidationException("Title length out of range!");
        }
        if (start.getTime() < 0 || end.getTime() < 0) {
            log.error("time below bound");
            throw new ValidationException("Time cannot be negative");
        }
        if (interval < 1) {
            log.error("interval < than 1");
            throw new ValidationException("interval should me > 1");
        }
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.time = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public boolean isActive(){
        return this.active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        this.start = time;
        this.end = time;
        this.interval = 0;
    }

    public Date getStartTime() {
        return start;
    }

    public Date getEndTime() {
        return end;
    }
    public long getRepeatInterval(){
        return interval > 0 ? interval : 0;
    }

    public void setTime(Date start, Date end, long interval){
        this.time = start;
        this.start = start;
        this.end = end;
        this.interval = interval;

    }
    public boolean isRepeated(){
        return this.interval != 0;

    }
    public Date nextTimeAfter(Date current) {
        if (current.after(end) || current.equals(end)) { // ne aflam dupa ce s-a terminat perioada task-ului.
            return null;
        }
        if (isRepeated() && isActive()) { // task-ul se repeta...
            Date timeBefore = start;
            Date timeAfter = start;
            if (current.before(start)) {
                return start;
            }
            if ((current.after(start) || current.equals(start)) && current.before(end)) {
                for (long i = start.getTime(); i <= end.getTime(); i += interval * 1000) {
                    if (current.equals(timeAfter)) {
                        return new Date(timeAfter.getTime() + interval * 1000);
                    }
                    if (current.after(timeBefore) && current.before(timeAfter)) {
                        return timeBefore;//return timeAfter
                    }
                    timeBefore = timeAfter;
                    timeAfter = new Date(timeAfter.getTime() + interval * 1000);
                }
            }
        }
        if (!isRepeated() && current.before(time) && isActive()) { // task-ul nu se repeta, dar urmeaza sa se intample.
            return time;
        } // task-ul nu se repeta si a trecut timpul lui deja.
        return null;
    }
    //duplicate methods for TableView which sets column
    // value by single method and doesn't allow passing parameters
    public String getFormattedDateStart(){
        return sdf.format(start);
    }
    public String getFormattedDateEnd(){
        return sdf.format(end);
    }
    public String getFormattedRepeated(){
        if (isRepeated()){
            String formattedInterval = TaskIO.getFormattedInterval(interval);
            return "Every " + formattedInterval;
        }
        else {
            return "No";
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!time.equals(task.time)) return false;
        if (!start.equals(task.start)) return false;
        if (!end.equals(task.end)) return false;
        if (interval != task.interval) return false;
        if (active != task.active) return false;
        return title.equals(task.title);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        result = (int) (31 * result + interval);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }
    @Override
    protected Task clone() throws CloneNotSupportedException {
        Task task  = (Task)super.clone();
        task.time = (Date)this.time.clone();
        task.start = (Date)this.start.clone();
        task.end = (Date)this.end.clone();
        return task;
    }
}


