package thread.taskThread;

import command.Invoke;

import java.util.concurrent.*;

public class TaskThreadPool extends ThreadPoolExecutor {
    /**
     * 最大线程数
     */
    private final static int MAXIMVMPOOLSIZE = 100;

    /**
     * 线程空闲时间
     */
    private final static long KEEPALICETIME = 10000;

    /**
     * 线程池名称
     */
    private String threadPoolName;

    /**
     * 线程执行命令队列
     */
    private ArrayBlockingQueue<Invoke> queue;

    /**
     * 任务队列最大长度
     */
    private final static int MAXQUEUESIZE = 10;

    /**
     * 线程是否处于休眠状态
     */
    private boolean isWait = false;

    /**
     * 线程参数
     *
     * @param corePoolSize
     */
    public TaskThreadPool(int corePoolSize, String name) {
        super(corePoolSize, MAXIMVMPOOLSIZE, KEEPALICETIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        this.threadPoolName = name;

        //初始任务队列长度
        this.queue = new ArrayBlockingQueue(MAXQUEUESIZE);
    }


    /**
     * 获取线程池名称
     *
     * @return
     */
    public String getThreadPoolName() {
        return threadPoolName;
    }


    /**
     * 获取当前任务队列长度
     *
     * @return
     */
    public int getQueueSize() {
        return queue.size();
    }

    /**
     * 添加任务
     *
     * @param command
     * @return
     */
    private boolean addTask(Invoke command) {
        boolean addStatus = false;
        if (queue.size() > 0 && queue.size() >= MAXQUEUESIZE) {
            System.err.println("任务队列已经达到最大长度");
            return false;
        }

        try {
            synchronized (this.queue) {
                queue.put(command);
            }

            addStatus = true;
        } catch (Exception e) {
            System.err.println("队列已满，添加新任务失败");
            return false;
        }

        return addStatus;
    }


    /**
     * 执行任务
     *
     * @param semaphore
     */
    public void executeTask(Semaphore semaphore) {
        Invoke command = null;
        while (true) {
            synchronized (this.queue) {
                command = this.queue.poll();
            }
            try {
                //获取执行许可
                semaphore.acquire();
                if (command != null) {
//                    System.out.println("队列长度：" + this.queue.size());
                    super.execute(command);
                } else {

                }
            } catch (Exception e) {
                //关闭线程池
                shutdown();
                e.printStackTrace();
                return;
            } finally {
                //释放许可
                semaphore.release();
            }


        }
    }


    /**
     * 启动线程池
     */
    public void startThreadPool() {
        try {
            TaskThreadPool taskThreadPool = this;
            Semaphore semaphore = new Semaphore(taskThreadPool.getMaximumPoolSize());

            submit(new Runnable() {
                @Override
                public void run() {
                    taskThreadPool.executeTask(semaphore);
                }
            });
        } catch (Exception e) {

        }
    }


    /**
     * 向线程池添加任务
     *
     * @param command
     * @return
     */
    public void putThreadPoolTask(Invoke command) {
        TaskThreadPool taskThreadPool = this;
        Thread addThread = new Thread() {

            @Override
            public void run() {
                super.run();
                boolean b = taskThreadPool.addTask(command);
                if (!b) {
                    System.err.println("添加任务失败");
                }
            }
        };

        addThread.start();
    }
}
