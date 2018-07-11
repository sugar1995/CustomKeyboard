package com.chhd.customkeyboard.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.util.AttributeSet;

import com.chhd.customkeyboard.R;

import java.util.List;

/**
 * ABCKeyboardView
 *
 * @author : 陈伟强 (2018/7/11)
 */

public class ABCKeyboardView extends BaseKeyboardView {

    private boolean isShift = false;

    public ABCKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ABCKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setKeyboard(new Keyboard(getContext(), R.xml.keyboard_abc));
        setOnKeyboardActionListener(this);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        super.onKey(primaryCode, keyCodes);
        if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            // 大写
            isShift = !isShift;
            toggleShift();
            invalidateAllKeys();
        } else if (primaryCode == KEYCODE_NUMBER) {
            isShift = false;
            post(new Runnable() {
                @Override
                public void run() {
                    setKeyboard(new Keyboard(getContext(), R.xml.keyboard_number_abc));
                }
            });
        } else if (primaryCode == KEYCODE_ABC) {
            isShift = false;
            post(new Runnable() {
                @Override
                public void run() {
                    setKeyboard(new Keyboard(getContext(), R.xml.keyboard_abc));
                }
            });
        }
    }

    private void toggleShift() {
        Keyboard.Key key = getKey(Keyboard.KEYCODE_SHIFT);
        int resId = isShift ?
                R.drawable.ic_twotone_font_download_24dp : R.drawable.ic_outline_font_download_24dp;
        key.icon = getResources().getDrawable(resId);
        List<Keyboard.Key> keyList = getKeyboard().getKeys();
        for (Keyboard.Key item : keyList) {
            if (item.label != null && isWord(item.label.toString())) {
                if (isShift) {
                    item.label = item.label.toString().toUpperCase();
                } else {
                    item.label = item.label.toString().toLowerCase();
                }
                item.codes[0] = item.label.charAt(0);
            }
        }
    }


    private boolean isWord(String str) {
        String word = "abcdefghijklmnopqrstuvwxyz";
        if (word.contains(str.toLowerCase())) {
            return true;
        }
        return false;
    }
}
