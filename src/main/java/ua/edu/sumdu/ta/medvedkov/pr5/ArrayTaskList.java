package ua.edu.sumdu.ta.medvedkov.pr5;

/**
 * A task list class that stores and works with a list of objects
 */
public class ArrayTaskList extends AbstractTaskList {
    private final String name = "[EDUCTR][TA]";
    public int quantityLists = 0;

    /**
     * A constructor for creating a task list. It uses a variable that displays the current number of task lists.
     */
    public ArrayTaskList() {
        this.quantityLists++;
        super.array = new Task[10];
    }

    /**
     * This method is for adding non-unique tasks. If the array is full, then it grows, after which a task is added to it. It also adds a string constant to each task title.
     *
     * @param task object of class Task
     */
    @Override
    public void add(Task task) {
        final int add = 10;
        if (size() == array.length) {
            Task[] nextArr = new Task[array.length + add];
            System.arraycopy(array, 0, nextArr, 0, array.length);
            array = nextArr;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = task;
                array[i].setTitle(name + array[i].getTitle());
                break;
            }
        }
    }

    /**
     * This method is to remove all tasks equal to the input. The method implements a position shift so that there are no empty elements.
     *
     * @param task object of class Task
     */
    @Override
    public void remove(Task task) {
        for (int i = 0; i < array.length; i++) {
            if (task.equals(array[i])) {
                array[i] = null;
                for (int k = i; k < array.length - 1; k++)
                    array[k] = array[k + 1];
            }
        }
    }

    /**
     * This abstract method is used to get the task at a given number (from 0)
     *
     * @param index the number of tasks
     * @return the task at a given number
     */
    @Override
    public Task getTask(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index > array.length - 1 || index > size() - 1)
            throw new ArrayIndexOutOfBoundsException();
        return array[index];
    }

    /**
     * This method returns an array of tasks from the list whose notification time between from (exclusively) and to (inclusive).
     *
     * @param from start of the interval
     * @param to   end of the interval
     * @return an array of tasks from the list whose notification time between from and to
     */
    public Task[] incoming(int from, int to) throws IllegalArgumentException {
        if (from < 0 || to < 0 || from > to)
            throw new IllegalArgumentException();
        Task[] newArr = new Task[array.length];
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].nextTimeAfter(from) != -1 && array[i].nextTimeAfter(from) <= to) {
                newArr[index] = array[i];
                index++;
            }
        }
        int j = 0;
        for (int i = 0; i < newArr.length; i++) {
            if (newArr[i] != null)
                j++;
        }
        Task[] newArray = new Task[j];
        System.arraycopy(newArr, 0, newArray, 0, j);
        return newArray;
    }
}
