package com.example.looperandhandler;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * We have a SimpleWorker which extends a Thread.
 * There's a flag 'alive' which keeps the thread running in a while loop
 * We've a taskQueue, whenever a Task is enqueued, we're into the queue
 * Whenever a task is there in the queue, the thread after finishing the
 * previous task will dequeue this queue and then run the runnable
 * which is supplied into the task queue.
 */

public class SimpleWorker extends Thread {

    private static final String TAG = "SimpleWorker";

    private AtomicBoolean alive = new AtomicBoolean(true);

    /**
     * We create a queue on which we execute tasks
     * Concurrent because multiple thread can try to add tasks into the queue
     */
    private ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    public SimpleWorker() {
        super(TAG);
        start();
    }

    /**
     * When start() is called it executes the run() of thread.
     */

    @Override
    public void run() {

        /**
         * While alive keep running
         * We'll be posting tasks as Runnables to the worker and running it while it is in loop
         * so we create queue above
         */
        while (alive.get()) {
            Runnable task = taskQueue.poll();
            if(task != null) {
                task.run();
            }
        }
        Log.i(TAG, "SimpleWorker Terminated");
    }

    /**
     * As an interface for the outer world to interact with simple worker
     * we provide execute with instance of Runnable
     * and we add the task into the queue
     */

    public SimpleWorker execute(Runnable task) {
        taskQueue.add(task);
        return this;
    }

    /**
     * We also create a method named quit()
     * It will set the value of alive to false
     * When we set the value of alive to false, the while loop will break and
     * log message gets printed.
     */

    public void quit() {
        alive.set(false);
    }
}
