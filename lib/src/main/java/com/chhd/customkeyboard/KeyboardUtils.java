package com.chhd.customkeyboard;

import android.os.Build;
import android.text.InputType;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * KeyboardUtils
 *
 * @author : 陈伟强 (2018/7/10)
 */

public class KeyboardUtils {

    public static final int NUMBER_TYPE_SIGNED_DECIMAL =
            InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED + InputType.TYPE_NUMBER_FLAG_DECIMAL;
    public static final int NUMBER_TYPE_SIGNED = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED;
    public static final int NUMBER_TYPE_DECIMAL = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL;
    public static final int NUMBER_TYPE_DEFAULT = InputType.TYPE_CLASS_NUMBER;

    private KeyboardUtils() {
    }

    public static void disableSoftKeyboard(EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus =
                        cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
    }

    public static boolean isNumber(EditText editText) {
        int inputType = editText.getInputType();
        return inputType == NUMBER_TYPE_SIGNED_DECIMAL ||
                inputType == NUMBER_TYPE_SIGNED ||
                inputType == NUMBER_TYPE_DECIMAL ||
                inputType == NUMBER_TYPE_DEFAULT;
    }
}
