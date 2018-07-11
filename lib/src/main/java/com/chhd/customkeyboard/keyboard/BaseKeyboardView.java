package com.chhd.customkeyboard.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.chhd.customkeyboard.R;

import java.util.List;

/**
 * BaseKeyboardView
 *
 * @author : 葱花滑蛋 (2018/7/11)
 */

public abstract class BaseKeyboardView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {

    protected final int KEYCODE_INVALID = -9999;
    protected final int KEYCODE_NUMBER = 123123;
    protected final int KEYCODE_ABC = 789789;

    protected EditText editText;

    public void attachTo(EditText editText) {
        this.editText = editText;
    }

    protected boolean isOkTab = false;

    public void setOkTab(boolean okTab) {
        isOkTab = okTab;
    }

    private boolean isVibrate = true;

    public void setVibrate(boolean isVibrate) {
        this.isVibrate = isVibrate;
    }

    protected OnKeyClickListener onKeyClickListener;

    public void setOnKeyClickListener(OnKeyClickListener onKeyClickListener) {
        this.onKeyClickListener = onKeyClickListener;
    }

    public BaseKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Keyboard.Key key = getKey(Keyboard.KEYCODE_DONE);
        if (isOkTab) {
            key.label = null;
            key.icon = getResources().getDrawable(R.drawable.ic_baseline_keyboard_tab_24dp);
        } else {
            key.label = "确定";
            key.icon = null;
        }
        super.onDraw(canvas);

        for (Keyboard.Key item : getKeyboard().getKeys()) {
            drawNotTouchKey(item, canvas);
        }
    }

    private void drawNotTouchKey(Keyboard.Key key, Canvas canvas) {
        if (!isValidKey(key)) {
            drawKeyBackground(R.drawable.bg_not_touch, canvas, key);
        }
    }

    private boolean isValidKey(Keyboard.Key key) {
        return key != null && (!TextUtils.isEmpty(key.label) || key.icon != null);
    }

    protected void drawKeyBackground(int drawableId, Canvas canvas, Keyboard.Key key) {
        Drawable npd = getContext().getResources().getDrawable(
                drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }
        npd.setBounds(key.x, key.y, key.x + key.width, key.y
                + key.height);
        npd.draw(canvas);
    }

    protected Keyboard.Key getKey(int code) {
        List<Keyboard.Key> keyList = getKeyboard().getKeys();
        for (Keyboard.Key key : keyList) {
            if (key.codes[0] == code) {
                return key;
            }
        }
        return null;
    }

    @Override
    public void onPress(int primaryCode) {
        Keyboard.Key key = getKey(primaryCode);
        if (isValidKey(key)) {
            vibrate();
        }
    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            // 大写
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            // 返回
        } else if (primaryCode == Keyboard.KEYCODE_DONE) {
            // 确定
            if (onKeyClickListener != null) {
                onKeyClickListener.onOkClick();
            }
        } else if (primaryCode == KEYCODE_INVALID) {
            // 无效
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
            // 回退
            if (editText != null && editText.getText().length() > 0) {
                int start = editText.getSelectionStart();
                if (start > 0) {
                    editText.getText().delete(start - 1, start);
                }
            }
        } else if (primaryCode == KEYCODE_NUMBER) {
            // 字母键盘
        } else if (primaryCode == KEYCODE_ABC) {
            // 数字键盘
        } else {
            if (editText != null) {
                int start = editText.getSelectionStart();
                char c = (char) primaryCode;
                editText.getText().insert(start, c + "");
            }
        }
    }

    protected void vibrate() {
        if (isVibrate) {
            Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                int millis = getContext().getResources().getInteger(R.integer.vibrate_millis);
                vibrator.vibrate(millis);
            }
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public interface OnKeyClickListener {

        void onOkClick();
    }
}
