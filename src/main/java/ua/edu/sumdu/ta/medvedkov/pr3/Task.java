package ua.edu.sumdu.ta.medvedkov.pr3;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The class describes the data type "task", which contains information about the essence of the task, it's status, the time of the notification and the time interval through which the notification should be repeated.
 */
public class Task {

    private String title;
    private boolean active = false;
    private int time;
    private int start;
    private int end;
    private int repeat;
    private boolean repeated;

    /**
     * Constructor for a single task
     *
     * @param title task name
     * @param time  time of the task notification
     */
    public Task(String title, int time) {
        checkArgument(!title.isEmpty(), "Title must not be empty");
        checkArgument(time >= 0, "time should not be negative");
        this.time = time;
        this.start = time;
        this.end = time;
        this.title = title;
    }

    /**
     * Constructor for a recurring task
     *
     * @param title  task name
     * @param start  start time of the task notification
     * @param end    time when task notification was stopped
     * @param repeat time interval after which the task notification must be repeated
     */
    public Task(String title, int start, int end, int repeat) {
        checkArgument(!title.isEmpty(), "Title must not be empty");
        this.title = title;
        this.start = start;
        this.end = end;
        this.repeat = repeat;
        this.time = start;
        checkTime();
    }

    /**
     * Method for argument checking
     */
    public void checkTime() {
        checkArgument(start >= 0 && end >= 0 && repeat >= 0, "variable values should not be negative");
        checkArgument(end >= start, "end time should not be less than start time");
        checkArgument(repeat <= end - start, "time interval should not exceed task duration");
    }

    /**
     * Method for getting the task name
     *
     * @return returns the name of the task
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method for setting the task name
     *
     * @param title task name
     */
    public void setTitle(String title) {
        checkArgument(!title.isEmpty(), "Title mustn't be empty");
        this.title = title;
    }

    /**
     * Method for checking task status
     *
     * @return returns true (for an active task) or false (for an inactive task)
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Method for setting task status
     *
     * @param active boolean indicator of task activity
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Method for setting the notification time for a single task
     *
     * @param time task notification time
     */
    public void setTime(int time) {
        checkArgument(time >= 0, "time shouldn't be negative");
        this.time = time;
        this.start = time;
        this.end = time;
    }

    /**
     * Method for setting the notification time for recurring task
     *
     * @param start  start time of the task notification
     * @param end    time when task notification was stopped
     * @param repeat time interval after which the task notification must be repeated
     */
    public void setTime(int start, int end, int repeat) {
        this.start = start;
        this.repeat = repeat;
        this.end = end;
        this.time = start;
        checkTime();
    }

    /**
     * Method for getting the notification time for task
     *
     * @return returns the time of the notification
     */
    public int getTime() {
        return time;
    }

    /**
     * Method for getting the start time of the task notification
     *
     * @return returns the start time of the notification
     */
    public int getStartTime() {
        return start;
    }

    /**
     * Method for getting the end time of the task notification
     *
     * @return returns the end time of the notification
     */
    public int getEndTime() {
        return end;
    }

    /**
     * Method for getting the time interval after which to repeat the task notification
     *
     * @return returns the time interval (for a recurring task) or 0 (for a single task)
     */
    public int getRepeatInterval() {
        if (!repeated)
            return 0;
        else
            return repeat;
    }

    /**
     * Method for checking the task for repeatability
     *
     * @return returns true (for a recurring task) or false (for a single task)
     */
    public boolean isRepeated() {
        repeated = time != start || time != end;
        return repeated;
    }

    /**
     * Method that returns a description of the task
     *
     * @return returns the description of the task
     */
    public String toString() {
        String info;
        if (!active)
            info = "Task " + title + " is inactive ";
        else if (repeat == 0)
            info = "Task " + title + " at " + time;
        else
            info = "Task " + title + " from " + start + " to " + end + " every " + repeat + " seconds ";
        return info;
    }

    /**
     * Method that returns the time of the next notification, after the specified time
     *
     * @param time time of the task notification
     * @return returns the time of the next notification, or -1 if there are no more notification
     */
    public int nextTimeAfter(int time) {
        checkArgument(time >= 0, "time should not be negative");
        if (time < start && active) {
            return start;
        } else {
            int a = start;
            while ((a + repeat) <= end && repeat != 0 && active) {
                a += repeat;
                if (a > time) return a;
            }
        }
        return -1;
    }
}
