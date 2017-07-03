package zx31401425.zucc.data_base;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this,"DateBase",null,1);  //第二个参数为数据库名

        Button createDatabase = (Button) findViewById(R.id.create_database);  //创建数据库
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.add_data);    //添加数据
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase  db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一组数据
                values.put("foregin","A");
                values.put("home","B");
                values.put("num",2.5);
                db.insert("datebase",null,values);
                //开始组装第二组数据
                values.put("foregin","C");
                values.put("home","A");
                values.put("num",4.5);
                db.insert("datebase",null,values);
            }
        });

        Button updateData = (Button) findViewById(R.id.update_date);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("num","10086");
                db.update("datebase",values,"home = ?",new String[]{"A"});
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_date);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("datebase","num = ?",new String[] {"10086"});
            }
        });

        Button queryButton = (Button) findViewById(R.id.query_date);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("datebase",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String foregin = cursor.getString(cursor.getColumnIndex("foregin"));
                        String home = cursor.getString(cursor.getColumnIndex("home"));
                        double num = cursor.getDouble(cursor.getColumnIndex("num"));
                        Log.d("MainActivity",foregin+home+num);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
