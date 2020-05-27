package thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by clm43897 on 2019/11/7.
 */

public class ThreadTest {
    @Test
    public void test() {
        List<Runnable> runnables = new ArrayList<>();
        for (int i = 0; i<100; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("task" + index);
                }
            };
            runnables.add(runnable);
        }
        execute(runnables);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void execute(List<Runnable> runnables) {
        final Queue<Runnable> queue = new ConcurrentLinkedQueue<>(runnables);
        for (int i = 0; i<20; i++) {
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Runnable r = queue.poll();
                        if (r == null) {
                            break;
                        }
                        System.out.println(index + "" + r);
                        r.run();
                    }
                }
            }).start();
        }
    }

    @Test
    public void test1() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(1 / 0);
            }
        };


        try {
            executorService.execute(runnable);
        } catch (Exception e) {
            System.out.println("执行execute捕获到异常 "+e.getMessage());
            e.printStackTrace();
        }


        Callable myCallable = new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println(1/0);
                return 1/0;
            }
        };


        try {
            Future future= executorService.submit(myCallable);
            future.get();
        } catch (Exception e) {
            System.out.println("执行submit捕获到异常 "+e.getMessage());
            e.printStackTrace();
        }

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testThreadPoolExecutor() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5), new DefaultThreadFactory("DefaultThread"),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i<23;i++) {
            final int time = i*1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(Thread.currentThread().getName());
                }
            };
            Future<?> future = threadPoolExecutor.submit(runnable);
            System.out.println("线程池中核心线程数--" + threadPoolExecutor.getCorePoolSize() + "--线程池中当前线程数--" + threadPoolExecutor.getPoolSize() + "--队列中的任务数--"+ threadPoolExecutor.getQueue().size() + "--已经执行完的任务数--" +  threadPoolExecutor.getCompletedTaskCount() + "--活动线程--" + threadPoolExecutor.getActiveCount());
        }



        System.out.println("线程池中核心线程数--" + threadPoolExecutor.getCorePoolSize() + "--线程池中当前线程数--" + threadPoolExecutor.getPoolSize() + "--队列中的任务数--"+ threadPoolExecutor.getQueue().size() + "--已经执行完的任务数--" +  threadPoolExecutor.getCompletedTaskCount() + "--活动线程--" + threadPoolExecutor.getActiveCount());

        while (threadPoolExecutor.getPoolSize()>5){
            System.out.println("--线程池中当前线程数--" + threadPoolExecutor.getPoolSize() + "--活动线程--" + threadPoolExecutor.getActiveCount());
            Thread.sleep(10);
        }
        System.out.println("--线程池中核心线程数--" + threadPoolExecutor.getCorePoolSize() + "--线程池中当前线程数--" + threadPoolExecutor.getPoolSize() + "--队列中的任务数--"+ threadPoolExecutor.getQueue().size() + "--已经执行完的任务数--" +  threadPoolExecutor.getCompletedTaskCount() + "--活动线程--" + threadPoolExecutor.getActiveCount());

        threadPoolExecutor.shutdown();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPoolExecutor.execute(runnable);

        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPoolExecutor.shutdown();
    }

    @Test
    public void testThreadPoolExecutor2() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(5), new DefaultThreadFactory("DefaultThread"),
                new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i<50;i++) {
            final int time = i*100;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1 + time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Future<?> future = threadPoolExecutor.submit(runnable);
            System.out.println("线程池中核心线程数--" + threadPoolExecutor.getCorePoolSize() + "--线程池中当前线程数--" + threadPoolExecutor.getPoolSize() + "--队列中的任务数--"+ threadPoolExecutor.getQueue().size() + "--已经执行完的任务数--" +  threadPoolExecutor.getCompletedTaskCount());
        }

        while (threadPoolExecutor.getPoolSize()>0){
            System.out.println("--线程池中当前线程数--" + threadPoolExecutor.getPoolSize());
            Thread.sleep(1000);
        }
        try {
            Thread.sleep(10000*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPoolExecutor.shutdown();
    }
    @Test
    public void test6() {

    }
}
