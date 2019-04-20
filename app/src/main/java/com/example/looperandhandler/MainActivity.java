package com.example.looperandhandler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * We create a simple worker
     */
    private SimpleWorker worker;
    private TextView tvMessage;

    /**
     * When we create the handler we supply the looper of the main thread.
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {

        /**
         * Here we handle the message supplied to this Handler
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvMessage.setText((String)msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessage = findViewById(R.id.tv_message);

        /**
         * As soon the worker is created, the thread will start running
         */

        worker = new SimpleWorker();

        /**
         * Now we enqueue the task for the worker to perform
         * We use lambda expressions
         */

        worker.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = Message.obtain();
                message.obj = "Task 1 completed";

                /**
                 * We use the handler created above to send the message
                 * to the main thread.
                 */
                handler.sendMessage(message);
            }
        }).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.obj = "Task 2 completed";
                handler.sendMessage(message);
            }
        }).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.obj = "Task 3 completed";
                handler.sendMessage(message);
            }
        });

        /**
         * For setting a value to a TextView to be set, we've to
         * supply a message from a worker thread to
         * the main thread and main thread will then update the
         * text message.
         * So for communicating with the main thread we require a Handler
         */

    }
}
