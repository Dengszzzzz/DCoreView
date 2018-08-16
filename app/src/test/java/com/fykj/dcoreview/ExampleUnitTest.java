package com.fykj.dcoreview;

import com.fykj.dcoreview.annotations.Sex;
import com.fykj.dcoreview.annotations.SexTest;
import com.fykj.dcoreview.bean.ClazzBean;
import com.fykj.dcoreview.bean.ReflectTestBean;
import com.fykj.dcoreview.bean.TagBean;
import com.fykj.dcoreview.utils.TimeUtils;
import com.fykj.dcoreview.utils.cryptoUtils.DesUtils;
import com.socks.library.KLog;

import org.junit.Test;

import java.io.File;
import java.io.FileDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        // assertEquals(4, 2 + 2);


        try {
            String content = "我该怎么做？";
            String encodeStr = DesUtils.encode(DesUtils.DES_KEY, content.getBytes());
            System.out.println("加密：" + encodeStr);
            String decodeStr = DesUtils.decode(DesUtils.DES_KEY, encodeStr);
            System.out.println("解密：" + decodeStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试TimeUtils
     */
    @Test
    public void testTimeUtils() {
        long timestamp = 1532506463657L;
        System.out.println(timestamp);
        //long时间戳转时间格式
        String time1 = TimeUtils.convertTime(TimeUtils.FORMAT_DATE_EN, timestamp);
        System.out.println("long时间戳转时间格式:" + time1);
        //计算离当前时间间隔
        String time2 = TimeUtils.intervalTime(timestamp, true);
        System.out.println("计算上一个时间离当前时间间隔:" + time2);
        String time3 = TimeUtils.intervalTime(1533506463657L, true);
        System.out.println("计算下一个时间离当前时间间隔:" + time3);
        //将String类型时间转为long类型时间
        System.out.println("将String类型时间转为long类型时间:" + TimeUtils.covertToLong(TimeUtils.FORMAT_TIME_EN, "2018-07-25 16:43:00"));
        //
        String timeFormat = "%1$s年%2$s月%3$s日 %4$s时%5$s分 （%6$s）";
        System.out.println("星期几：" + TimeUtils.convertDayOfWeek(timeFormat, timestamp));

    }


    /**
     * 测试类的反射
     * 知识点：Class.newInstance  Constructor.newInstance
     */
    @Test
    public void testReflect() {

        Class class_reflect = null;
        //获取Class对象的三种方式
        //1.通过实例变量方式
        // ReflectTestBean testBean = new ReflectTestBean();
        // class_reflect = testBean.getClass();
        //2.通过类名方式，只会加载ReflectTestBean类，并不会触发其类构造器的初始化
         class_reflect = ReflectTestBean.class;
        //3.通过Class.forName(String classname)方式
       /* try {
            class_reflect = Class.forName("com.fykj.dcoreview.bean.ReflectTestBean");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/


        //1.获取类字段
        Field[] fields = class_reflect.getDeclaredFields();
        for(Field field:fields){
            System.out.println("1.类字段：" + field.getName());
        }
        //2.获取类方法
        Method[] methods = class_reflect.getDeclaredMethods();
        for(Method method:methods){
            System.out.println("2.类方法:" + method);
        }
        //3.获取对应的实例构造器，并生成类实例
        try {
            //Constructor.newInstance() 可以根据传入的参数，调用任意构造构造函数。
            //Constructor.newInstance() 在特定的情况下，可以调用私有的构造函数。
            Constructor constructor  = class_reflect.getConstructor(String.class,int.class);
            try {
                System.out.println("3.获取对应的实例构造器，并生成类实例:" + constructor.newInstance("Tom", 3).toString());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //4.通过newInstance()方法生成类实例
        try {
            //Class.newInstance() 只能够调用无参的构造函数，即默认的构造函数；
            //Class.newInstance() 要求被调用的构造函数是可见的，也即必须是public类型的;
            ReflectTestBean reflectTestBean = (ReflectTestBean) class_reflect.newInstance();
            reflectTestBean.setName("newInstant");
            reflectTestBean.setAge(4);
            System.out.println("4.通过newInstance()方法生成类实例:" + reflectTestBean.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



}