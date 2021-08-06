package com.wdz.module_architecture.pattern.combination;

public class TestCombination {
    public static void main(String[] args) {
        Folder mainFolder = new Folder("总文件夹");
        Folder childFolder = new Folder("子文件夹");
        TextFile textFile = new TextFile("文本文件");
        ImageFile imageFile = new ImageFile("图像文件");
        mainFolder.addFile(childFolder);
        childFolder.addFile(textFile);
        childFolder.addFile(imageFile);
        mainFolder.display();
    }
}
