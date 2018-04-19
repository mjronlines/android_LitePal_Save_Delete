package com.yc.day;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yc.day.SqlLit.DayLilst;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private ListView lv;
    private List<DayLilst> allDb;
    private DayAdapter dayAdapter;
    private MainActivity Mycontent;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Mycontent = MainActivity.this;


        lv = (ListView) findViewById(R.id.Daylv);
        et = (EditText) findViewById(R.id.editText);
        initData();
    }

    private void initData() {
        allDb = DataSupport.findAll(DayLilst.class);
        dayAdapter = new DayAdapter();
        lv.setAdapter(dayAdapter);
    }

    public void selectAll(View view) {
        System.out.println("查询所有");
        allDb = DataSupport.findAll(DayLilst.class);
        System.out.println(allDb.size());
        dayAdapter.notifyDataSetChanged();

    }

    public void saveOnce(View view) {
        System.out.println("保存一条数据");
        Random random = new Random();
        DayLilst days = new DayLilst();
        Editable text = et.getText();
        days.setTitle(text.toString());
        days.save();

        initGetAllDb();
    }

    private void initGetAllDb() {
        allDb = DataSupport.findAll(DayLilst.class);
//        System.out.println(allDb.size());
        dayAdapter.notifyDataSetChanged();

        lv.setSelection(allDb.size());
    }

    private class DayAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return allDb.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View views = View.inflate(Mycontent, R.layout.lv_item, null);
            TextView tt = (TextView) views.findViewById(R.id.tt);
            final TextView del = (TextView) views.findViewById(R.id.del);
            final long index = allDb.get(i).getId();
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int delete = DataSupport.delete(DayLilst.class, index);
                    System.out.println(delete);

                    initGetAllDb();

                }
            });
            tt.setText(allDb.get(i).getTitle());
            return views;
        }
    }
}
