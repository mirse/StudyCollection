package com.wdz.module_architecture.pattern.combination;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 使用场景：部分、整体场景，树形菜单
 *
 * 文件夹类
 */
public class Folder extends File{
    private static final String TAG = "Folder";
    private List<File> files;

    public Folder(String name) {
        super(name);
        files = new ArrayList<>();

    }

    @Override
    public void display() {
        for (File file:files) {
            file.display();
        }
    }

    public void addFile(File file){
        files.add(file);
    }

    public void deleteFile(File file) {
        files.remove(file);
    }
}
class TextFile extends File{
    private static final String TAG = "TextFile";
    public TextFile(String name) {
        super(name);
    }

    @Override
    public void display() {
        System.out.println("TextFile名称："+getName());
    }
}

class ImageFile extends File{
    private static final String TAG = "ImageFile";
    public ImageFile(String name) {
        super(name);
    }

    @Override
    public void display() {
        System.out.println("ImageFile名称："+getName());
    }
}


