package com.javaSummary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by administrator on 2018/9/10.
 */
public class AlgorithmUtils {

    /**
     * 水仙花数
     * 比如：3位数水仙花数   abc = a^3 + b^3 + c^3相等，就是水仙花数
     * 如果是4位  abcd = a^4 + b^4 + c^4 + d^4。
     * 以此类推
     * 考虑到大于1千万的数，要用long类型
     * @param count  位数
     * @param limitNum  最大限制数
     */
    public static void onTestNarcissus(int count,long limitNum){
        if(count<3)return;
        //1.比如10位，则需要分别找3,4,5。。。10位的水仙花数
        for(int i = 3;i <= count;i++){
            long a[] = new long[i];
            long num = (long) Math.pow(10,i-1);
            long lastNum = (long) (Math.pow(10,i));
            System.out.println(i + "位水仙花数如下：\t");
            //2.比如3位，则是从100~999中取值判断是否水仙花数
            while (num < lastNum && num < limitNum){
                long sum = 0;
                for(int j = 0;j<i;j++){
                    a[j] = (long) ((num/Math.pow(10,j))%10);
                }
                for(int j = 0;j<i;j++){
                    sum = (long) (sum + Math.pow(a[j],i));
                }
                if(num == sum){
                    System.out.println(num + "\t");
                }
                num++;
            }
            System.out.println("\n");
        }
    }


    public static void onTestNarcissus2(int count, final long limitNum){
        if(count<3)return;
        //用多线程加快计算速度
        ExecutorService service = Executors.newFixedThreadPool(count>6? count/2:2);
        for(int i = 3;i <= count;i++){
            final int finalI = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    long a[] = new long[finalI];
                    long num = (long) Math.pow(10, finalI -1);
                    long lastNum = (long) (Math.pow(10, finalI));
                    System.out.println(finalI + "位水仙花数如下：\t");
                    while (num < lastNum && num < limitNum){
                        long sum = 0;
                        for(int j = 0; j< finalI; j++){
                            a[j] = (long) ((num/Math.pow(10,j))%10);
                        }
                        for(int j = 0; j< finalI; j++){
                            sum = (long) (sum + Math.pow(a[j], finalI));
                        }
                        if(num == sum){
                            System.out.println(num + "\t");
                        }
                        num++;
                    }
                    System.out.println("\n");
                }
            });
        }
    }

}
