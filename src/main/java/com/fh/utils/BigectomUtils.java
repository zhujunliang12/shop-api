package com.fh.utils;

import java.math.BigDecimal;

public class BigectomUtils {

    public static BigDecimal muil(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.multiply(b2).setScale(2);
    }

    public static BigDecimal jia(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.add(b2);
    }


}
