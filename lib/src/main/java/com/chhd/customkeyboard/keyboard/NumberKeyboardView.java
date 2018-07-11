package com.chhd.customkeyboard.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.Keyboard;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.chhd.customkeyboard.KeyboardUtils;
import com.chhd.customkeyboard.R;

/**
 * NumberKeyboardView
 *
 * @author : 葱花滑蛋 (2018/7/5)
 */

public class NumberKeyboardView extends BaseKeyboardView {

    @Override
    public void attachTo(EditText editText) {
        super.attachTo(editText);
        int inputType = editText.getInputType();
        if (inputType == KeyboardUtils.NUMBER_TYPE_SIGNED_DECIMAL) {
            getKey(45).label = "-";
            getKey(46).label = ".";
        } else if (inputType == KeyboardUtils.NUMBER_TYPE_SIGNED) {
            getKey(45).label = "-";
            getKey(46).label = "";
        } else if (inputType == KeyboardUtils.NUMBER_TYPE_DECIMAL) {
            getKey(45).label = "";
            getKey(46).label = ".";
        } else {
            getKey(45).label = "";
            getKey(46).label = "";
        }
        invalidateAllKeys();
    }

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setKeyboard(new Keyboard(getContext(), R.xml.keyboard_number));
        setOnKeyboardActionListener(this);
    }
}
