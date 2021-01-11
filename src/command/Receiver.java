package command;


/**
 * 接受者类
 */
public class Receiver {
    private int carNum = 0;

    public Receiver(int carNum) {
        this.carNum = carNum;
    }

    public Receiver() {
    }


    /**
     * 设置获取个数
     *
     * @param carNum
     */
    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    /**
     * 真正执行操作
     */
    public void action() {
        System.out.println("放入的数量：" + this.carNum);
//        System.out.println(this.toString());
    }
}

