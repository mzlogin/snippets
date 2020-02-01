package org.mazhuang.junit;

/**
 * author: mazhuang
 * date: 2018/3/30
 */
public class Test {
    public static void main(String[] args) {
        Test test = new Test();

        System.out.println(test.sum(""));
    }

    private double sum(String key) {
        double startvalue = 0;
        double endvalue = 1;
        if (null != key || !key.contains("") || key.length() > 0) {
            if (key.equals("0.1 以下及 0.1")) {
                startvalue = 0;
                endvalue = 0.100;
            }

            if (key.equals("0.2 以下及 0.1")) {
                startvalue = 0.101;
                endvalue = 0.200;
            }

            if (key.equals("0.3 以下及 0.3")) {
                startvalue = 0.201;
                endvalue = 0.300;
            }
        }

        return startvalue + endvalue;
    }

}
