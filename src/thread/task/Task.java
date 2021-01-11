package thread.task;


/**
 * 任务基类
 */
public abstract class Task implements Runnable {

    /**
     * 任务优先级
     */
    private int taskPriority;

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }
}
