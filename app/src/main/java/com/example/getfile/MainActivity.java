package com.example.getfile;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private ListView listView;
    private File filepath;
    private File audiofile;
    private String strpath;
    private List<Map<String,Object>> Datalist;
    private Button button;
    private List<String> filelist = new ArrayList<String>();
    private List<Map<String ,Object>> mData;
    MyAdapter adapter = new MyAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =(ListView) findViewById(R.id.listView);


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                //获取SD卡路径
            filepath = Environment.getExternalStorageDirectory();
            strpath = Environment.getExternalStorageDirectory().toString();
            Toast.makeText(MainActivity.this,"you SDCard",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(MainActivity.this,"no SDCard",Toast.LENGTH_LONG).show();
        }
        mData=getData();
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //openFile((String)mData.get(position).get("info"));
                String fff = mData.get(position).get("info").toString();
                openFile(fff);
            }
        });

    }

    private void openFile(String filePath) {
        File ofile = new File(filePath);
        Toast.makeText(MainActivity.this,filePath,Toast.LENGTH_LONG).show();

    }


    private List<Map<String,Object>> getData() {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        String sdstate = Environment.getExternalStorageState();
        if (sdstate.equals(Environment.MEDIA_MOUNTED)){
            try {
                String uri = Environment.getExternalStorageDirectory().toString()+"/Movies";
                File filesss = new File(uri);
              //  File SDFile = Environment.getExternalStorageDirectory();
                File sdpath = new File(filesss.getAbsolutePath());
                if (sdpath.listFiles().length>0){
                    for (File file : sdpath.listFiles()){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("title",file.getName());
                        map.put("filePath",file.getPath());
                        map.put("info",file.getPath());
                        if (file.isDirectory()){
                            map.put("icon",R.mipmap.ex_folder);
                        }else if (file.getName().endsWith(".mp3")){
                            map.put("icon",R.mipmap.mp3);
                        }else if (file.getName().endsWith(".mp4")){
                            map.put("icon",R.mipmap.mp4);
                        } else if (file.getName().endsWith(".jpg")){
                            map.put("icon",R.mipmap.jpeg);
                        }else if (file.getName().endsWith(".txt")){
                            map.put("icon",R.mipmap.text);
                        }else if (file.getName().endsWith(".ppt")){
                            map.put("icon",R.mipmap.pptx_win);
                        }else if (file.getName().endsWith(".doc")||file.getName().endsWith(".docx")||file.getName().endsWith(".wps")){
                            map.put("icon",R.mipmap.docx_win);
                        }else if (file.getName().endsWith(".xls")||file.getName().endsWith(".et")||file.getName().endsWith("xlsx")){
                            map.put("icon",R.mipmap.xlsx_win);
                        }else if (file.getName().endsWith(".rar")){
                            map.put("icon",R.mipmap.rar);
                        }else if (file.getName().endsWith(".zip")){
                            map.put("icon",R.mipmap.zip);
                        }else {
                            map.put("icon",R.mipmap.ex_doc);
                        }
                        list.add(map);
                    }
                }
            }catch (Exception e){

            }
        }
            return list;

    }




    public class ViewHolder{
        public ImageView icon;
        public TextView title;
        public TextView info;
        public Button btn;
       // public TextView size;
    }
    public class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mData.size();
        }


        @Override
        public Object getItem(int position) {
            return position;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null ){
                convertView =View.inflate(MainActivity.this,R.layout.filelist_item,null);
                holder = new ViewHolder();
                holder.icon = (ImageView)convertView.findViewById(R.id.imageviwe);
                holder.title =(TextView)convertView.findViewById(R.id.filename);
                holder.info =(TextView)convertView.findViewById(R.id.filesize);
                holder.btn =(Button)convertView.findViewById(R.id.button);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.icon.setImageResource((Integer)mData.get(position).get("icon"));
            holder.title.setText((String) mData.get(position).get("title"));
            holder.info.setText((String)mData.get(position).get("info"));
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,(String)mData.get(position).get("info"),Toast.LENGTH_LONG).show();
                }
            });
            return convertView;
        }
    }





}
