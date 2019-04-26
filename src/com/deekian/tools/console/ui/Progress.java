package com.deekian.tools.console.ui;

/**
 * 进度条工具类
 */
public class Progress {

    private long minimum = 0; // 进度条起始值

    private long maximum = 100; // 进度条终止值

    private long barSize = 100; // 进度条长度

    private char showChar = '+'; // 进度字符
    private char waitChar = '-'; // 占位字符
    private String itemName = ">"; // 进度条名称

    private String lastRate = "";   //上次进度百分比

    private boolean effective = true;   //是否有效

    /**
     * 使用系统标准输出，显示字符进度条及其百分比。
     *
     * @param minimum  进度条起始值
     * @param maximum  进度条最大值
     * @param barLen   进度条长度
     * @param showChar 用于进度条显示的字符
     * @param waitChar 用于进度条显示的字符
     * @param itemName 显示名称
     */
    public Progress(long minimum, long maximum,
                    long barLen, char showChar, char waitChar, String itemName) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.barSize = barLen;
        this.showChar = showChar;
        this.waitChar = waitChar;
        this.itemName = itemName;
    }

    public Progress(long maximum, String itemName) {
        this.maximum = maximum;
        this.itemName = itemName;
    }

    /**
     * 显示进度条。
     *
     * @param value 当前进度。进度必须大于或等于起始点且小于等于结束点（start <= current <= end）。
     */
    public void show(long value) {
        if (value < this.minimum || value > this.maximum) {
            return;
        }
        this.minimum = value;
        float rate = (float) (this.minimum * 1.0 / this.maximum);
        long len = (long) (rate * this.barSize);
        draw(len, rate);
        if (this.minimum == this.maximum) {
            afterComplete();
        }
    }

    public void forward() {
        show(++this.minimum);
    }

    public void end() {
        this.show(this.maximum);
        this.effective = false;
        System.out.println();
    }

    private void draw(long len, float rate) {
        String rateStr = format(rate);
        if (this.effective && !rateStr.equalsIgnoreCase(this.lastRate)) {
            reset();
            System.out.printf("%s > [", this.itemName);
            for (int i = 0; i < len; i++) {
                System.out.print(this.showChar);
            }
            for (int i = 0; i < this.barSize - len; i++) {
                System.out.print(this.waitChar);
            }
            System.out.print("] ");
            System.out.print(rateStr);
            this.lastRate = rateStr;
        }
    }


    private void reset() {
        System.out.print('\r'); //光标移动到行首
    }

    private void afterComplete() {
        System.out.print('\n');
    }

    private String format(float num) {
        return String.format("%.1f", num * 100) + "%";
    }

    public static void main(String[] args) throws InterruptedException {
        Progress cpb = new Progress(1000, "测试");
        for (int i = 1; i <= 1000; i++) {
            cpb.show(i);
            Thread.sleep(100);
        }
    }

}