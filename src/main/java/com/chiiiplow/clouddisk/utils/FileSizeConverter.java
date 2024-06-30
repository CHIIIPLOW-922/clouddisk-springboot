package com.chiiiplow.clouddisk.utils;

import java.text.DecimalFormat;

public class FileSizeConverter {

    // 常量定义
    private static final long KB = 1024;
    private static final long MB = KB * 1024;
    private static final long GB = MB * 1024;
    private static final long TB = GB * 1024;

    /**
     * 将字节大小转换为带单位的字符串表示
     *
     * @param bytes 字节大小
     * @return 带单位的字符串表示
     */
    public static String convertBytesToReadableSize(long bytes) {
        if (bytes < KB) {
            return bytes + " B";
        } else if (bytes < MB) {
            return String.format("%.0fKB", (double) bytes / KB);
        } else if (bytes < GB) {
            return String.format("%.0fMB", (double) bytes / MB);
        } else if (bytes < TB) {
            return String.format("%.0fGB", (double) bytes / GB);
        } else {
            return String.format("%.0fTB", (double) bytes / TB);
        }
    }

    public static float calculateUsedSpaceRate(Long used, Long total) {
        if (total == 0) {
            throw new IllegalArgumentException("分母不能为0");
        }

        // 将Long转换为float进行浮点数除法
        float result = (float) used / total * 100;

        // 格式化为两位小数
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedResult = decimalFormat.format(result);

        // 将格式化后的字符串转换回float
        return Float.parseFloat(formattedResult);
    }
}