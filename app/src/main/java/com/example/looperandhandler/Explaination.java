package com.example.looperandhandler;

public class Explaination {

    /**
     * How do the Looper and the Handler provide this solution.
     *
     *  - MessageQueue:
     *      It is a queue that holds the Messages which define payloads
     *      and Runnable.
     *
     *  - Handler:
     *      It puts the Messages into the MessageQueue via Looper.
     *
     *  - Looper:
     *      It keeps the thread alive by putting it in an infinite loop.
     *      It deque the messages and send them to the corresponding Handlers
     *      or execute the Runnable using the thread.
     *
     *  - The Thread gets terminated by calling Looper's quit method.
     *
     * How to use Looper and Handler with a Thread
     *
     * 1. Create a Looper and MessageQueue for a Thread after it is running:
     *
     *  - Looper.prepare(): This method identifies the calling thread and
     *  then create a looper and message queue for that thread.
     *
     *  - Looper.loop(): It starts the looper associated with the thread by
     *  keeping it in an infinite loop.
     *
     *  - Looper.quit(): It terminates looper and put the thread out of the
     *  infinite loop.
     *
     *
     *  class LooperThread extends Thread {
     *      public Handler mHandler;
     *
     *      public void run() {
     *          Looper.prepare();
     *
     *          mHandler = new Handler() {
     *              public void handleMessage(Message msg) {
     *                  // process incoming messages here
     *                  // this will run in non-ui/background thread
     *              }
     *          };
     *
     *          Looper.loop();
     *      }
     *  }
     *
     *
     *  2. Create a Handler:
     *  A handler get implicitly attached with the looper of the thread that
     *  instantiates it or we can explicitly attach a handler to a thread's
     *  looper by passing the looper in the constructor of the handler.
     *
     *  handler = new Handler() {
     *      @Override
     *      public void handleMessage(Message msg) {
     *          // process incoming messages here
     *          // this will run in the thread, which instantiates it
     *      }
     *  };
     *
     *  Or
     *
     *  Handler = new Handler(getSomeLooper())
     *
     * ======================================================
     *
     *  MessageQueue
     *
     *  The MessageQueue can receive two types of tasks:
     *  - Message:
     *      This class defines the various methods which help to supply the
     *      payloads for the task.
     *      It is passed back to the enqueuing handler when the thread is free
     *      to process the message.
     *
     *   - Runnable:
     *      This is also stored in one form of message.
     *      The runnable is executed when the thread is free.
     *
     *
     *  Message msg = Message.obtain();
     *  msg.obj = "Milind send message";
     *  handler.sendMessage(msg);
     *
     *  new Handler(getLooper()).post(new Runnable() {
     *      @Override
     *      public void run() {
     *          //this will run in the main thread
     *      }
     *  });
     *
     *  We can post the task after a specified amount of time:
     *  handler.postDelayed(new Runnable() {
     *      public void() {
     *          doit();
     *      }
     *  }, 1000);
     *
     * ====================================================
     *
     *      Android Main Thread
     *
     *  When an android application starts then a thread called as main
     *  thread starts to run. This thread is also called as UI thread because
     *  it is responsible for drawing the views.
     *
     *  In order to run any task through the main thread we need to send a
     *  message through the looper attached with the main thread.
     *
     *  new Handler(Looper.getMainLooper()).post(new Runnable() {
     *      @Override
     *      public void run() {
     *          // this will run in the main thread.
     *      }
     *  }
     *
     *  ====================================================
     *
     *          HandlerThread
     *
     *  It is an alternative way to create a looper and a handler for a thread.
     *  This class is provided by the Android OS and abstracts the process we just
     *  followed to associate a looper with a thread.
     *
     *  It can be used in two ways:
     *
     *  - Define our thread by extending HandlerThread.
     *  - Create an instance of HandlerThread and
     *    associate a Handler to the thread's Looper.
     *
     *    Usage:
     *
     *    public class MyThread extends HandlerThread {
     *        public MyThread() {
     *            super("MyThread");
     *        }
     *    }
     *
     *                   OR
     *
     *    HandlerThread handlerThread =
     *              new HandlerThread("MyHandlerThread");
     *
     *    handlerThread.start();
     *
     *    Handler handler =
     *              new Handler(handlerThread.getLooper());
     *
     *
     *  HandlerThread Explanation
     *
     *  - Looper is only prepared after HandlerThread's start() is called
     *   i.e. after the thread is running.
     *
     *   - A Handler can be associated with a HandlerThread, only after it's
     *   Looper is prepared.
     *
     *   - We need to call quit() to free the resources and stop the
     *   execution of the thread.
     *
     */
}
