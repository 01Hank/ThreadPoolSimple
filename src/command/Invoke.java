package command;

public class Invoke extends Thread {
    /**
     * 持有命令对象
     */
    private Command command;

    public Invoke(Command command) {
        this.command = command;
    }

    public Invoke() {
    }

    /**
     * 装载命令
     *
     * @param command
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * 行动方法
     */
    public void action() {
        command.execute();
    }

    @Override
    public void run() {
        super.run();
        this.action();
    }
}
