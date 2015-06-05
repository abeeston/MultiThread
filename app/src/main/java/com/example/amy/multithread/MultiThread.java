package com.example.amy.multithread;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MultiThread
 *
 * A class demonstrating the use of multiple threads in the creation and reading from a simple
 * file. The app updates a progress bar as it creates the file and then reads from it.
 */
public class MultiThread extends Activity {

    private Handler handler;
    private ProgressBar progressBar;
    private int progress = 0;
    private static final String fileName = "numbers.txt";
    private List<String> list;
    private static Context context;
    private File file;
    public ListView lView;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);

        // setting up components
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        MultiThread.context = MultiThread.this.getApplicationContext();
        handler = new Handler();
        progress = 0;

        // list and file setup
        list = new ArrayList<>();
        file = new File(this.getFilesDir(), fileName);
        if (file.exists()) {
            file.delete();
        }

        // list view and adapter setup
        lView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
    }

    public void create(View view) {
        // Create a new thread, then start
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (file.createNewFile()) {
                        FileWriter fileWriter = new FileWriter(file);

                        // numbers 1 through 10
                        for (int i = 1; i < 11; i++) {
                            // write contents
                            String content = i + "\n";
                            fileWriter.write(content);

                            // set the progress bar
                            final int temp = i;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(10 * temp);
                                }
                            });
                            // simulate a more difficult task
                            Thread.sleep(250);
                        }
                        // reset the progress bar and exit
                        progressBar.setProgress(0);
                        fileWriter.close();
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }

    public void load(View view) {
        // Create a new thread, then start
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileReader fReader = new FileReader(file);
                    if (fReader.ready()) {
                        BufferedReader br = new BufferedReader(fReader);

                        // read file contents line by line
                        String line;
                        while ((line = br.readLine()) != null) {
                            list.add(line);

                            // increment and set progress bar
                            if (progress < 100) {
                                progress += 10;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(progress);
                                    }
                                });
                            }

                            // simulate a more difficult task with "sleep"
                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // reset the progress bar and close
                        progressBar.setProgress(0);
                        br.close();

                        // update the listview to what is in the list read into
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lView.setAdapter(adapter);
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }

    public void clear(View view) {
        // delete the file
        File file = new File(this.getFilesDir(), fileName);
        file.delete();

        // clear the list, setting the adapter
        list.clear();
        lView.setAdapter(adapter);
    }
}
