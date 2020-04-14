package ua.edu.sumdu.ta.medvedkov.pr5;

/**
 * A task list class that stores and works with a linked list of objects
 */
public class LinkedTaskList extends AbstractTaskList {
    static class Link {
        public Task task;
        public Link next;
    }

    public Link head;
    private Link tail;
    public int size = 0;

    /**
     * This method is for adding non-unique tasks. If the array is full, then it grows, after which a task is added to it. It also adds a string constant to each task title.
     *
     * @param task object of class Task
     */
    @Override
    public void add(Task task) throws IllegalArgumentException {
        if (task == null)
            throw new IllegalArgumentException();
        Link list = new Link();
        list.task = task;
        if (head == null) {
            head = list;
            tail = list;
        } else {
            tail.next = list;
            tail = list;
        }
        size++;
    }

    /**
     * This method is to remove all tasks equal to the input. The method implements a position shift so that there are no empty elements.
     *
     * @param task object of class Task
     */
    @Override
    public void remove(Task task) {
        if (head.task == task || head == tail) {
            head = head.next;
        } else {
            Link t = head;
            while (t.next != null) {
                if (t.next.task == task) {
                    if (t.next == tail)
                        tail = t;
                    else t.next = t.next.next;
                }
                t = t.next;
            }
        }
        size--;
        if (size <= -1) size = 0;
    }

    /**
     * This abstract method is used to get the task at a given number (from 0)
     *
     * @param index the number of tasks
     * @return the task at a given number
     */
    @Override
    public Task getTask(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > size - 1)
            throw new ArrayIndexOutOfBoundsException();
        //checkArgument(index < 0, "there is no such element in the array");
        Link a = head;
        for (int i = 0; i < index; i++)
            a = a.next;
        return a.task;
    }

    /**
     * This method returns an array of tasks from the list whose notification time between from (exclusively) and to (inclusive).
     *
     * @param from start of the interval
     * @param to   end of the interval
     * @return an array of tasks from the list whose notification time between from and to
     */
    @Override
    public Task[] incoming(int from, int to) throws IllegalArgumentException {
        if (from < 0 || to < 0 || from > to)
            throw new IllegalArgumentException();
        Task[] newArr = new Task[size()];
        int index = 0;
        Link a = head;
        while (a != null) {
            // a.task.setActive(true);
            if (a.task.nextTimeAfter(from) != -1 && a.task.nextTimeAfter(from) <= to) {
                newArr[index] = a.task;
                index++;
                a = a.next;
            } else a = a.next;
        }
        Task[] newArray = new Task[index];
        System.arraycopy(newArr, 0, newArray, 0, index);
        return newArray;
    }

    /**
     * This method returns the quantity of tasks in the current list.
     *
     * @return return quantity of tasks
     */
    @Override
    public int size() {
        return size;
    }
}
