import command.Invoke;
import command.Receiver;
import test.commandIm;
import thread.taskThread.TaskThreadPool;

public class main {
    public static void main(String[] args) {
        int corePoolSize = 10;
        TaskThreadPool threadPool = new TaskThreadPool(corePoolSize, "测试线程池");
        //开启线程池执行任务
        threadPool.startThreadPool();
        //开始添加任务
        Receiver re = null;
        commandIm commandIm = null;
        Invoke invoke = null;
        for (int i = 0; i < 20; i++) {
            re = new Receiver();
            re.setCarNum(i);
            commandIm = new commandIm();
            commandIm.setReceiver(re);
            invoke = new Invoke();
            invoke.setCommand(commandIm);
            threadPool.putThreadPoolTask(invoke);
        }

    }

}
