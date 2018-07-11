package com.chhd.customkeyboard.builder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.chhd.customkeyboard.CustomKeyboard;
import com.chhd.customkeyboard.KeyboardUtils;
import com.chhd.customkeyboard.keyboard.ABCKeyboardView;
import com.chhd.customkeyboard.keyboard.BaseKeyboardView;
import com.chhd.customkeyboard.keyboard.NumberKeyboardView;
import com.chhd.customkeyboard.R;


/**
 * InsertBuilder
 *
 * @author : 陈伟强 (2018/7/10)
 */

@SuppressLint("ClickableViewAccessibility")
public class InsertBuilder {

    private EditText editText;

    public InsertBuilder() {
    }

    public void bind(final EditText editText) {
        this.editText = editText;
        KeyboardUtils.disableSoftKeyboard(editText);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText.isEnabled() && event.getAction() == MotionEvent.ACTION_DOWN) {
                    Activity activity = (Activity) v.getContext();
                    if (CustomKeyboard.isShow(activity)) {
                        CustomKeyboard.hide(activity);
                    }
                    insert(editText);
                }
                return false;
            }
        });
    }

    private void insert(final EditText editText) {
        final Activity activity = (Activity) editText.getContext();
        editText.requestFocus();
        BaseKeyboardView keyboardView;
        if (KeyboardUtils.isNumber(editText)) {
            keyboardView = (BaseKeyboardView) View.inflate(activity,
                    R.layout.layout_number_keyboard_view, null);

        } else {
            keyboardView = (BaseKeyboardView) View.inflate(activity,
                    R.layout.layout_abc_keyboard_view, null);
        }
        keyboardView.attachTo(editText);
        ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.addOnLayoutChangeListener(onLayoutChangeListener);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        keyboardView.setOnKeyClickListener(new NumberKeyboardView.OnKeyClickListener() {
            @Override
            public void onOkClick() {
                if (CustomKeyboard.isShow(activity)) {
                    CustomKeyboard.hide(activity);
                }
            }
        });
        rootView.addView(keyboardView, params);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (CustomKeyboard.isShow(activity)) {
                    CustomKeyboard.hide(activity);
                }
                return false;
            }
        });
    }

    private View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {

        @Override
        public void onLayoutChange(View v,
                                   int left, int top, int right, int bottom,
                                   int oldLeft, int oldTop, int oldRight, int oldBottom) {
            ViewGroup rootView = (ViewGroup) v;
            Context context = rootView.getContext();
            String tag = context.getResources().getString(R.string.tag_base_custom_view);
            BaseKeyboardView baseKeyboardView = rootView.findViewWithTag(tag);
            int hasMoved = 0;
            Object heightTag = rootView.getTag(R.id.scroll_height_by_keyboard);
            if (heightTag != null) {
                hasMoved = (int) heightTag;
            }
            if (baseKeyboardView == null) {
                rootView.removeOnLayoutChangeListener(this);
                if (hasMoved > 0) {
                    rootView.getChildAt(0).scrollBy(0, -1 * hasMoved);
                    rootView.setTag(R.id.scroll_height_by_keyboard, 0);
                }
            } else {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                int[] etLocation = new int[2];
                editText.getLocationOnScreen(etLocation);
                int keyboardTop = etLocation[1] + editText.getHeight() + editText.getPaddingTop()
                        + editText.getPaddingBottom() + 1;
                int moveHeight = keyboardTop + baseKeyboardView.getHeight() - rect.bottom;
                if (moveHeight > 0) {
                    rootView.getChildAt(0).scrollBy(0, moveHeight);
                    rootView.setTag(R.id.scroll_height_by_keyboard, hasMoved + moveHeight);
                }
            }
        }
    };
}
