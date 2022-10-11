package com.hacktiv8.finpro1todolist;

import static com.hacktiv8.finpro1todolist.DatabaseHelper.KEY_ID;
import static com.hacktiv8.finpro1todolist.DatabaseHelper.TABLE_TASK;
import static com.hacktiv8.finpro1todolist.DatabaseHelper.TITLE_TASK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DatabaseHelper db;
    List<ToDoList> todoList;
    RecyclerView recyclerView;
    ToDoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.todo_rv);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadData();
//        updateUI();
    }

    private void loadData(){
        todoList = db.getAllData();
        adapter = new ToDoListAdapter(this, todoList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_task:
                final EditText taskAdd = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task").setMessage("What do you want to do next?").setView(taskAdd)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskAdd.getText());
                                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TITLE_TASK, task);
                                db.addTask(taskAdd.getText().toString());
                                db.close();
                                loadData();
                                //updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.show();
                return true;
            default:
                Toast.makeText(this, "Data Gagal di Input", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void updateUI() {
//        ArrayList<String> taskList = new ArrayList<>();
//         db.getReadableDatabase();
//        Cursor cursor = db.query(false, TABLE_TASK,
//                new String[]{KEY_ID, TITLE_TASK},
//                null, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            int idx = cursor.getColumnIndex(TITLE_TASK);
//            taskList.add(cursor.getString(idx));
//        }
//
//        if (arrayAdapter == null) {
//            arrayAdapter = new ArrayAdapter<>(this, R.layout.task_item, R.id.title_task, taskList);
//            recyclerView.setAdapter(arrayAdapter);
//        } else {
//            arrayAdapter.clear();
//            arrayAdapter.addAll(taskList);
//            arrayAdapter.notifyDataSetChanged();
//        }
//
//        cursor.close();
//        db.close();
//    }
}