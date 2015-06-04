package com.example.amy.multithread;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

    public void create(View view) throws IOException {
        try {
            // open the file
            File file = new File(this.getFilesDir(), fileName);

            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);

                // write contents
                for (int i = 1; i < 11; i++) {
                    String content = i + "\n";
                    fileWriter.write(content);
                    //Thread.sleep(250);
                }
                fileWriter.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void load(View view) {
        try {
            FileReader fReader = new FileReader(new File(this.getFilesDir(), fileName));
            boolean cont = true;
            while (cont) {
                if (fReader.ready()) {
                    BufferedReader br = new BufferedReader(fReader);

                    String line;
                    while ((line = br.readLine()) != null) {
                        list.add(line);
                        //Thread.sleep(250);
                    }
                    br.close();
                    cont = false;
                }
            }

            ListView lView = (ListView)findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            lView.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear(View view) {
        // delete the file
        File file = new File(this.getFilesDir(), fileName);
        file.delete();

        // clear the list
        list.clear();
        ListView lView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        //adapter.addAll(list);
        lView.setAdapter(adapter);
    }
}
