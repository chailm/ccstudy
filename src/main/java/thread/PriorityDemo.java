package thread;

/**
 * Created by clm43897 on 2020/4/9.
 */
public class PriorityDemo {
    public static class HighPriority extends Thread {
        private int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    if (count > 10000000) {
                        System.out.println("HighPriorityThread is complete");
                        break;
                    }
                }
                count++;
            }
        }
    }

    public static class LowPriority extends Thread {
        private int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    if (count > 10000000) {
                        System.out.println("LowPriorityThread is complete");
                        break;
                    }
                    count++;
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread highPriority = new HighPriority();
        highPriority.setPriority(Thread.MIN_PRIORITY);
        Thread lowPriority = new LowPriority();
        lowPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.start();
        highPriority.start();
    }
}
