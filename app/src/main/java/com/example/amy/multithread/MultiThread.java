package com.example.amy.multithread;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiThread extends ActionBarActivity {

    private String fileName = "numbers.txt";
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_thread, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void create() {
        File file = new File(this.getFilesDir(), fileName);
        try {
            FileOutputStream ostream = openFileOutput(fileName, Context.MODE_PRIVATE);

            // write contents
            for (int i = 1; i < 11; i++) {
                String content = i + "\n";
                ostream.write(content.getBytes());
                Thread.sleep(250);
            }
            ostream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void load() {
        ListView view = (ListView)findViewById(R.id.listView);
        try {
            FileReader fReader = new FileReader(new File(this.getFilesDir(), fileName));
            BufferedReader br = new BufferedReader(fReader);

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);

                list.add(line);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            view.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
    }
}
