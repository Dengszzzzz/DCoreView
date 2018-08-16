package com.fykj.dcoreview.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by administrator on 2018/7/27.
 */

//枚举 可以解决  参数的类型太泛了造成的类型不安全问题
//android 不推荐使用枚举，可用替代  @IntDef/@StringDef + @interface来进行限定参数
//注释类
//1.SOURCE:在源文件中有效（即源文件保留）
//2.CLASS:在class文件中有效（即class保留）
//3.RUNTIME:在运行时有效（即运行时保留）
@Documented  //表示开启Doc文档
@IntDef({Sex.MAN,Sex.WOMEN})   //限定只有 MAN 和 WOMEN，其他会报错提示，但还是能编译运行
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD}) //表示注解作用范围，参数注解，成员注解，方法注解
@Retention(RetentionPolicy.SOURCE)
public @interface Sex {
    int MAN = 2;
    int WOMEN = 3;
    int UNKNOW = 4;
}
