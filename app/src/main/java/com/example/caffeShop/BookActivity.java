package com.example.caffeShop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.caffeShop.R;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    EditText id_book, name_book, page_book, price_book, desc_book, search;
    Button addBtn, editBtn, deleteBtn;
    ImageButton searchBtn;
    ListView lv;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> myList;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setContent();
        myList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(arrayAdapter);
        createDB();
        query();
        setClick();
    }




    void createDB(){
        database = openOrCreateDatabase("bookmanager.db", MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE booktbl(id TEXT primary key, name TEXT, page TEXT, price INTEGER, descr TEXT)";
            database.execSQL(sql);
            Log.e("SUCC", "DBTạo thành công");

        }catch (Exception e){
            Log.e("ERORR", "DB Đã tồn tại");
        }
    }

    void setClick(){
        addBtn.setOnClickListener(v -> {
            insert();
        });
        deleteBtn.setOnClickListener(v -> {
            delete();
        });
        editBtn.setOnClickListener(v -> {
            update();
        });
    }
    void insert(){
        String id = id_book.getText().toString();
        String name = name_book.getText().toString();
        String page = page_book.getText().toString();
        int price = Integer.parseInt(price_book.getText().toString());
        String desc = desc_book.getText().toString();
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) || TextUtils.isEmpty(page) || TextUtils.isEmpty(desc)){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("name", name);
            values.put("page", page);
            values.put("price", price);
            values.put("descr", desc);
            String msg = "";
            if (database.insert("booktbl", null, values) == -1) {
                msg = "Thêm thất bại!";

            } else {

                msg = "Thêm thành công!";
                query();
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
    void delete(){
        String id = id_book.getText().toString();
        if (TextUtils.isEmpty(id)){
            Toast.makeText(this, "Vui lòng nhập id để xóa", Toast.LENGTH_SHORT).show();
        }else {
            int n = database.delete("booktbl", "id = ?", new String[]{id});
            String msg = "";
            if (n == 0){
                msg = "xóa thất bại!";
            }else {
                query();
                msg = "xóa thành công!";
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
    void update(){
        String id = id_book.getText().toString();
        String name = name_book.getText().toString();
        int price = Integer.parseInt(price_book.getText().toString());
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);
        int n = database.update("booktbl", values, "id = ?", new String[]{id});
        String msg = "";
        if (n == 0){
            msg = "update thất bại!";
        }else {
            query();
            msg = "update thành công!";
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
    void query(){
        myList.clear();
        Cursor c = database.query("booktbl", null, null, null, null, null, null);
        c.moveToNext();
        String data = "";
        while (!c.isAfterLast()){
            data = c.getString(0)+"-"+c.getString(1)+"-"+c.getString(2)+"-"+c.getString(3)+"-"+c.getString(4);
            c.moveToNext();
            myList.add(data);

        }
        c.close();
        arrayAdapter.notifyDataSetChanged();
    }

    void setContent(){
        id_book = findViewById(R.id.id_book);
        name_book = findViewById(R.id.name_book);
        page_book = findViewById(R.id.page_book);
        price_book = findViewById(R.id.price_book);
        desc_book = findViewById(R.id.desc_book);
        search = findViewById(R.id.search);
        addBtn = findViewById(R.id.addBtn);
        editBtn = findViewById(R.id.editBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        searchBtn = findViewById(R.id.searchBtn);
        lv = findViewById(R.id.lv);


    }
}