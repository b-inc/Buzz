package com.binc.catapultanimationtut;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

public class  MainActivity extends ListActivity {
    ListView lv;
    EditText et;
    EditText et_key;
    EditText et_val;
    Button btn;
    Button btn_put;
    MyAdapter myAdapter;
    String[][] inp;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        inp = new String[][]{{"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},{"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Aa", "Bb"},
                {"Cc", "Dd"}};

        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = et.getText().toString();
            }
        });

        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = et_key.getText().toString();
                String val = et_val.getText().toString();
                //call DB method
            }
        });


        lv = getListView();
        myAdapter = new MyAdapter(inp, this);

        lv.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter {

        String[][] input;
        Context mContext;
        LayoutInflater inflater;

        MyAdapter(String[][] inp, Context ctx){
            input = inp;
            mContext = ctx;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return inp.length;
        }

        @Override
        public Object getItem(int position) {
            return input[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if(convertView == null){
                vh = new ViewHolder(input[position].length, input[position]);

                convertView = inflater.inflate(R.layout.item, parent, false);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
            ll.removeAllViews();

            for(int i = 0; i < input[position].length; i++){
                TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                ll.addView(vh.col_values[i], params);
            }
            return convertView;
        }

        class ViewHolder {
            int count;
            TextView[] col_values;
            ViewHolder(int cnt, String[] row){
                count = cnt;
                col_values = new TextView[row.length];
                setup(row);
            }
            void setup(String[] in){
                for(int i = 0; i < in.length; i++){
                    col_values[i] = new TextView(MainActivity.this);
                    col_values[i].setWidth(100);
                    col_values[i].setHeight(100);
                    col_values[i].setText(in[i]);
                }
            }
        }
    }
}
