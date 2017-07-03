package zx31401425.zucc.data_base;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private MyDatabaseHelper dbHelper;

    private List<Data> dataList = new ArrayList<Data>();
    private List<String> listforegin = new ArrayList<String>();//下拉列表1的数据
    private List<String> listhome = new ArrayList<String>();//下拉列表2的数据
    private Spinner mySpinner1;
    private Spinner mySpinner2;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private List<String> list1 = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();
    String s1 = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "DateBase", null, 1);  //第二个参数为数据库名
        dbHelper.getWritableDatabase();//创建数据库

        initData();//初始化data
        final DataAdapter apdater = new DataAdapter(MainActivity.this, R.layout.data_item, dataList);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(apdater);

        init();//数据库中导入
        apdater.notifyDataSetChanged(); //更新LIST

//        Button createDatabase = (Button) findViewById(R.id.create_database);  //创建数据库
//        createDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dbHelper.getWritableDatabase();
//            }
//        });
//        list1.add("北京");
//        list1.add("上海");
//        list1.add("深圳");
//        list1.add("福州");
//        list1.add("厦门");
        init2();//更新列表
        mySpinner1 = (Spinner) findViewById(R.id.Spinner_foregin);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(adapter1);
        mySpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, ("您选择的是：" + adapter1.getItem(arg2)), Toast.LENGTH_LONG).show();
                if(!adapter1.getItem(arg2).equals("all")){
                    s1=adapter1.getItem(arg2);
                    init3(adapter1.getItem(arg2));
                    mySpinner2.setAdapter(adapter2);
                    checkforegin( adapter1.getItem(arg2));
                    apdater.notifyDataSetChanged();
                }
                else{
                    init();
                    apdater.notifyDataSetChanged();
                }
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });

        mySpinner2 = (Spinner) findViewById(R.id.Spinner_home);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(adapter2);
        mySpinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, ("您选择的是：" + adapter2.getItem(arg2)), Toast.LENGTH_LONG).show();
                /* 将mySpinner 显示*/
                if(!adapter2.getItem(arg2).equals("all")){
                    checkhome2(s1,adapter2.getItem(arg2));
                    apdater.notifyDataSetChanged();
                }
                else {
                    checkhome1(s1);
                    apdater.notifyDataSetChanged();
                }


                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });
        Button addData = (Button) findViewById(R.id.add_data);    //添加数据
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一组数据
                values.put("foregin", "A");
                values.put("home", "B");
                values.put("num", 2.5);
                db.insert("datebase", null, values);
                //开始组装第二组数据
                values.put("foregin", "C");
                values.put("home", "A");
                values.put("num", 4.5);
                db.insert("datebase", null, values);
//                Data a = new Data("a","c",44.5);
//                dataList.add(a);
//                apdater.notifyDataSetChanged();
                init();
                init2();
                apdater.notifyDataSetChanged();

            }
        });

        Button updateData = (Button) findViewById(R.id.update_date);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("num", "10086");
                db.update("datebase", values, "home = ?", new String[]{"A"});
                init();
                apdater.notifyDataSetChanged();
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_date);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("datebase", "num = ?", new String[]{"10086"});
                init();
                apdater.notifyDataSetChanged();
            }
        });

//        Button queryButton = (Button) findViewById(R.id.query_date);
//        queryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                Cursor cursor = db.query("datebase",null,null,null,null,null,null);
//                Data a;
//                if(cursor.moveToFirst()){
//                    do{
//                        String foregin = cursor.getString(cursor.getColumnIndex("foregin"));
//                        String home = cursor.getString(cursor.getColumnIndex("home"));
//                        double num = cursor.getDouble(cursor.getColumnIndex("num"));
//                        a = new Data(foregin,home,num);
//                        dataList.add(a);
//                    }while (cursor.moveToNext());
//                }
//                cursor.close();
//            }
//        });

    }

    private void checkforegin(String s) {
        dataList.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("datebase",null ,"foregin = ?",new String[]{s}, null, null, null);
        Data a;
        if (cursor.moveToFirst()) {
            do {
                String home = cursor.getString(cursor.getColumnIndex("home"));
                double num = cursor.getDouble(cursor.getColumnIndex("num"));
                a = new Data(s, home, num);
                dataList.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    private void checkhome1(String s1) {
        dataList.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("datebase",null ,"foregin = ?",new String[]{s1}, null, null, null);
        Data a;
        if (cursor.moveToFirst()) {
            do {
                String home = cursor.getString(cursor.getColumnIndex("home"));
                double num = cursor.getDouble(cursor.getColumnIndex("num"));
                a = new Data(s1, home, num);
                dataList.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void checkhome2(String s1,String s) {
        dataList.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("datebase",null ,"foregin = ? and home = ?",new String[]{s1,s}, null, null, null);
        Data a;
        if (cursor.moveToFirst()) {
            do {
                double num = cursor.getDouble(cursor.getColumnIndex("num"));
                a = new Data(s1, s, num);
                dataList.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void init() {
        dataList.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("datebase", null, null, null, null, null, null);
        Data a;
        if (cursor.moveToFirst()) {
            do {
                String foregin = cursor.getString(cursor.getColumnIndex("foregin"));
                String home = cursor.getString(cursor.getColumnIndex("home"));
                double num = cursor.getDouble(cursor.getColumnIndex("num"));
                a = new Data(foregin, home, num);
                dataList.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void init2() {       //添加下拉列表
        list1.clear();
        list1.add("all");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(true, "datebase", new String[]{"foregin"}, null, null, null, null, null, null);
        Data a;
        if (cursor.moveToFirst()) {
            do {
                String foregin = cursor.getString(cursor.getColumnIndex("foregin"));
                list1.add(foregin);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void init3(String s) {       //添加下拉列表
        list2.clear();
        list2.add("all");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(true, "datebase", new String[]{"home"}, "foregin = ?", new String[]{s}, null, null, null, null);
        Data a;
        if (cursor.moveToFirst()) {
            do {
                String home = cursor.getString(cursor.getColumnIndex("home"));
                list2.add(home);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void initData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //开始组装第一组数据
        values.put("foregin", "B");
        values.put("home", "C");
        values.put("num", 2.5);
        db.insert("datebase", null, values);
        //开始组装第二组数据
        values.put("foregin", "C");
        values.put("home", "B");
        values.put("num", 4.5);
        db.insert("datebase", null, values);

    }
}
