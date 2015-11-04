package com.example.getfile;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jack on 2015/11/4.
 */
public class MyFilter implements FilenameFilter{
    List<String> types;

    /**
     * 构造一个MyFilter对象，其指定文件类型为空。
     */
    protected MyFilter() {
        types = new ArrayList<String>();
    }

    /**
     * 构造一个MyFilter对象，具有指定的文件类型。
     * @param types
     */
    protected MyFilter(List<String> types) {
        super();
        this.types = types;
    }
    @Override
    public boolean accept(File dir, String filename) {
        for (Iterator<String> iterator = types.iterator();iterator.hasNext();){
            String type = (String) iterator.next();
            if (filename.endsWith(type)){
                return true;
            }
        }
        return false;
    }
    /**
     * 添加指定类型的文件。
     *
     * @param type  将添加的文件类型，如".mp3"。
     */
    public void addType(String type) {
        types.add(type);
    }
}
