package com.test.templatechooser.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;

import org.apache.commons.lang3.StringUtils;

/**
 * This util class works with color.
 * */
public class ColorUtils {

    /**
     * Parse a color string to int.
     * It also verifies colors with length 4, like #222, #FA4 etc.
     *
     * @param colorString Color string.
     * */
    public static int parseColor(String colorString) {
        if(colorString.length() == 4 && colorString.charAt(0) == '#') {
            colorString = "#" +
                    StringUtils.repeat(colorString.substring(1, 2), 2) +
                    StringUtils.repeat(colorString.substring(2, 3), 2) +
                    StringUtils.repeat(colorString.substring(3, 4), 2);
        }

        return Color.parseColor(colorString);
    }

    /**
     * Parse a color string to ColorStateList.
     *
     * @param colorString Color string.
     * @return A ColorStateList containing a single color.
     * */
    public static ColorStateList parseToColorStateList(String colorString) {
        int color = parseColor(colorString);
        return ColorStateList.valueOf(color);
    }

}
