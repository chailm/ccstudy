package thread;

import org.junit.Test;

/**
 * Created by clm43897 on 2019/11/19.
 */
public class VolatileTest {
      int i = 0;
    @Test
    public void testVolatile() throws InterruptedException {
        Thread[] threads = new Thread[1000];
        for (int j = 0; j < 1000; j++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k<10000; k++) {
                        synchronized (Object.class) {
                            i++;
                        }
                    }
                }
            };
            threads[j] = new Thread(runnable);
            threads[j].start();
        }
        for (int j = 0;j<1000; j++) {
            threads[j].join();
        }
//        Thread.sleep(1000);
        System.out.println(i);
    }

    @Test
    public void testSync() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int j = 0; j < 10; j++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    addi();
                }
            };
            threads[j] = new Thread(runnable);
            threads[j].start();
        }
        for (int j = 0;j<10; j++) {
            threads[j].join();
        }
        System.out.println(i);
    }

    synchronized public void addi() {
        for (int k = 0; k<10000; k++) {
            i++;
        }
    }
}
