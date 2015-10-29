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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    ListView listView;
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
                        map.put("info",file.getPath());
                        if (file.isDirectory()){
                            map.put("icon",R.mipmap.ex_folder);
                        }else if (file.getName().endsWith(".mp3")){
                            map.put("icon",R.mipmap.mp3);
                        }else if (file.getName().endsWith(".mp4")){
                            map.put("icon",R.mipmap.mp4);
                        }
                        list.add(map);
                    }
                }
            }catch (Exception e){

            }
        }
            return list;

//            Datalist = new ArrayList<Map<String, Object>>();
//            Map<String,Object> map = null;
//            for (File file:home.listFiles()){
//                map = new HashMap<String, Object>();
//                map.put("title",file.getName());
//                map.put("info",file.getPath());
////                if (file.isDirectory()){
////                    map.put("icon",R.mipmap.ex_folder);
////                }else {
////                    map.put("icon", R.mipmap.ex_doc);
////
////                }
//                Datalist.add(map);
//                MyAdapter adapter = new MyAdapter();
//                listView.setAdapter(adapter);
//            }

            //dadf
//        File f = new File(strpath);
//        File[] files = f.listFiles();
//        Datalist = new ArrayList<Map<String, Object>>();
//        Map<String,Object> map = null;
//        if (files !=null){
//            for (int i=0;i<files.length;i++){
//                map = new HashMap<String, Object>();
//                map.put("title",files[i].getName());
//                map.put("info",files[i].getPath());
//                if (files[i].isDirectory()){
//                    map.put("icon",R.mipmap.ex_folder);
//                }else {
//                    map.put("icon",R.mipmap.ex_doc);
//                    Datalist.add(map);
//                }
//            }
//        }
//        MyAdapter adapter = new MyAdapter();
//        listView.setAdapter(adapter);
    }




    public class ViewHolder{
        public ImageView icon;
        public TextView title;
        public TextView info;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null ){
                convertView =View.inflate(MainActivity.this,R.layout.filelist_item,null);
                holder = new ViewHolder();
                holder.icon = (ImageView)convertView.findViewById(R.id.imageviwe);
                holder.title =(TextView)convertView.findViewById(R.id.filename);
                holder.info =(TextView)convertView.findViewById(R.id.filesize);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.icon.setImageResource((Integer)mData.get(position).get("icon"));
            holder.title.setText((String)mData.get(position).get("title"));
            holder.info.setText((String)mData.get(position).get("info"));
            return convertView;
        }
    }




}
