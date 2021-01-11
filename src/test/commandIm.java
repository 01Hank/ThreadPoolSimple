package test;

import command.Command;
import command.Receiver;

public class commandIm implements Command {
    /**
     * 接受类
     */
    private Receiver receiver = null;


    /**F
     * 设置接受类
     * @param receiver
     */
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.action();
    }
}
