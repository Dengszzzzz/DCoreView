package com.javaSummary;

import android.os.Environment;

import com.javaSummary.bean.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by administrator on 2018/9/6.
 */
public class FileHelper {

    private String fileName;  //文件路径，不仅是文件名

    public FileHelper() {
        fileName = Environment.getExternalStorageDirectory() + "/001/DCoreView/personFile.out";
    }

    public FileHelper(String filePath) {
        this.fileName = filePath;
    }

    public void saveObjToFile(Person person){
        try {
            //FileOutputStream 的构造函数，一种传入File，一种传入文件路径
            //FileOutputStream fos = new FileOutputStream(new File(fileName));
            FileOutputStream fos = new FileOutputStream(fileName);  //这个构造函数内部也是new File(filePath)
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(person);   //将Person对象p写入到oos中
            oos.close();      //关闭文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Person readObjFromFile(){
        ObjectInputStream ois = null;
        try {
            //读数据
            ois = new ObjectInputStream(new FileInputStream(fileName));
            Person person = (Person) ois.readObject();
            return person;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
