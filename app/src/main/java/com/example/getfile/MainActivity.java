package com.example.getfile;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
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

import java.io.FilenameFilter;
import java.lang.reflect.Type;
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
    private Button tupian,wendang,shipin,all;
    private List<String> filelist = new ArrayList<String>();
    private List<Map<String ,Object>> mData;
    private MyAdapter adapter = new MyAdapter();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =(ListView) findViewById(R.id.listView);
        tupian = (Button) findViewById(R.id.tupian);
        wendang = (Button)findViewById(R.id.wendang);
        shipin =(Button)findViewById(R.id.shipin);
        all = (Button)findViewById(R.id.button2);


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

        tupian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = showpic();
                listView.setAdapter(adapter);
            }
        });

        wendang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = showfile();
                listView.setAdapter(adapter);
            }
        });

        shipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = showvideo();
                listView.setAdapter(adapter);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData=getData();
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //openFile((String)mData.get(position).get("info"));
                String fff = mData.get(position).get("info").toString();
                openFile(fff);
            }
        });


    }

    private List<Map<String,Object>> showvideo() {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        String sdstate = Environment.getExternalStorageState();
        if (sdstate.equals(Environment.MEDIA_MOUNTED)){
            try {
                String sdpath = Environment.getExternalStorageDirectory().toString()+"/Movies";
                File home = new File(sdpath);
                MyFilter filenameFilter = new MyFilter();
                filenameFilter.addType(".mp4");
                filenameFilter.addType(".avi");
                filenameFilter.addType(".rmvb");
                filenameFilter.addType(".mkv");
                File[] filess = home.listFiles(filenameFilter);
                if (filess.length>0){
                    for (File file : filess){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("title",file.getName());
                        map.put("icon",R.mipmap.mp4);
                       list.add(map);
                    }
                }
            }catch (Exception e){

            }
        }
        return list;
    }

    private List<Map<String,Object>> showfile() {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        String sdstate = Environment.getExternalStorageState();
        if (sdstate.equals(Environment.MEDIA_MOUNTED)){
            try {
                String sdpath = Environment.getExternalStorageDirectory().toString()+"/Movies";
                File home = new File(sdpath);
                MyFilter filenameFilter = new MyFilter();
                filenameFilter.addType(".doc");
                filenameFilter.addType(".docx");
                filenameFilter.addType(".ppt");
                filenameFilter.addType(".txt");
                filenameFilter.addType(".xls");
                File[] filess = home.listFiles(filenameFilter);
                if (filess.length>0){
                    for (File file : filess){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("title",file.getName());
                        map.put("icon",R.mipmap.ex_doc);
                        list.add(map);
                    }
                }
            }catch (Exception e){

            }
        }
        return list;
    }

    private List<Map<String,Object>> showpic() {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        String sdstate = Environment.getExternalStorageState();
        if (sdstate.equals(Environment.MEDIA_MOUNTED)){
            try {
                String sdpath = Environment.getExternalStorageDirectory().toString()+"/Movies";
                File home = new File(sdpath);
                MyFilter filenameFilter = new MyFilter();
                filenameFilter.addType(".jpg");
                filenameFilter.addType(".jpge");
                filenameFilter.addType(".png");
                filenameFilter.addType(".bmp");
                File[] filess = home.listFiles(filenameFilter);
                if (filess.length>0){
                    for (File file : filess){
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("title",file.getName());
                        map.put("icon",R.mipmap.jpeg);
                        list.add(map);
                    }
                }
            }catch (Exception e){

            }
        }
        return list;
    }

//    private class fileFilter implements FilenameFilter{
//
//        @Override
//        public boolean accept(File dir, String filename) {
//
//            return (filename.endsWith(".mp4"));
//        }
//    }

    private void openFile(String filePath) {
        File ofile = new File(filePath);
       // Toast.makeText(MainActivity.this,filePath,Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME 类型
        String type = getMIMEType(ofile);
        //设置intent的data和type属性
        intent.setDataAndType(/*uri*/Uri.fromFile(ofile), type);
        //跳转
        startActivity(intent);
    }

    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex<0){
            return type;
        }
        //获取问价的后缀名
        String end = fName.substring(dotIndex,fName.length()).toLowerCase();
        if (end =="")return type;
        //在MINE和文件类型的匹配表中找到对应的MINE类型。
        for (int i =0;i<MIME_MapTable.length;i++){
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }

        return type;
    }

    private final String[][] MIME_MapTable={
            //{后缀名， MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",    "image/bmp"},
            {".c",  "text/plain"},
            {".class",  "application/octet-stream"},
            {".conf",   "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls",    "application/vnd.ms-excel"},
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",   "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h",  "text/plain"},
            {".htm",    "text/html"},
            {".html",   "text/html"},
            {".jar",    "application/java-archive"},
            {".java",   "text/plain"},
            {".jpeg",   "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",   "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",   "video/mp4"},
            {".mpga",   "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop",   "text/plain"},
            {".rc", "text/plain"},
            {".rmvb",   "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh", "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            {".xml",    "text/plain"},
            {".z",  "application/x-compress"},
            {".zip",    "application/x-zip-compressed"},
            {"",        "*/*"}
    };


    private List<Map<String,Object>> getData() {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        String sdstate = Environment.getExternalStorageState();
        if (sdstate.equals(Environment.MEDIA_MOUNTED)){
            try {
                String uri = Environment.getExternalStorageDirectory().toString()+"/Movies";
                File filesss = new File(uri);
              //  File SDFile = Environment.getExternalStorageDirectory();
                File sdpath = new File(filesss.getAbsolutePath());
                if (filesss.listFiles().length>0){
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
              //  holder.info =(TextView)convertView.findViewById(R.id.filesize);
                holder.btn =(Button)convertView.findViewById(R.id.button);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.icon.setImageResource((Integer) mData.get(position).get("icon"));
            holder.title.setText((String) mData.get(position).get("title"));
           // holder.info.setText((String)mData.get(position).get("info"));
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
