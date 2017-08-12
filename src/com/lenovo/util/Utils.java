package com.lenovo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/23.
 */
public class Utils {


    /**
     * 根据一给定的方案决定另一个方案
     *
     * @param nums 第一个投资方案
     * @return 第二个投资方案
     */
    public static List<String> quedingfangan(List<String> nums) {
        List<String> all = getAll(1000);
        all.removeAll(nums);
        return all;
    }

    /**
     * 规则所产生的所有结果
     *
     * @param num
     * @return
     */
    private static List<String> getAll(int num) {

        List<String> strs = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int m = 0; m <= 9; m++) {
                    String str = "" + i + j + m;
                    strs.add(str);
                }
            }
        }
        return strs;
    }

    public static List<String> getArrayToList(String str) {
        String[] touzhus = str.split(" ");
        List<String> strs = new ArrayList<>();
        for (String s : touzhus) {
            strs.add(s);
        }
        return strs;

    }
    public static boolean isEmpty(Object o)
    {
        if(null==o) return true;
        return false;
    }

    /**
     * 加
     * @param a
     * @param b
     * @return
     */
    public static double add(double a,double b){
        BigDecimal a1 = new BigDecimal(a);
        BigDecimal b1 = new BigDecimal(b);
        BigDecimal add = a1.add(b1);
        return add.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 乘
     * @param a
     * @param b
     * @return
     */
    public static double mul(double a,double b){
        BigDecimal a1 = new BigDecimal(a);
        BigDecimal b1 = new BigDecimal(b);
        BigDecimal add = a1.multiply(b1);
        return add.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 减
     * @param a
     * @param b
     * @return
     */
    public static double sub(double a,double b){
        BigDecimal a1 = new BigDecimal(a);
        BigDecimal b1 = new BigDecimal(b);
        BigDecimal add = a1.subtract(b1);
        return add.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String get2Value(double value) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        return df.format(value);
    }
    public static String getFromAssets() {
        try {

            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(new File("D:/JavaSpace/src/com/lenovo/conf/touzhu.txt")));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line+" ";
            }
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
