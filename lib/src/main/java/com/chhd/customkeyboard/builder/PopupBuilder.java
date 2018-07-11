package com.chhd.customkeyboard.builder;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.chhd.customkeyboard.BottomDialog;
import com.chhd.customkeyboard.CustomKeyboardView;

/**
 * PopupBuilder
 *
 * @author : 陈伟强 (2018/7/10)
 */

public class PopupBuilder {

    private boolean isKeyboardTab = true;

    private OnKeyClickListener onKeyClickListener;

    public PopupBuilder() {
    }

    /**
     * 设置“确定”键是否允许改变焦点
     *
     * @param keyboardTab keyboardTab
     * @return PopupBuilder
     */
    public PopupBuilder setKeyboardTab(boolean keyboardTab) {
        this.isKeyboardTab = keyboardTab;
        return this;
    }

    public PopupBuilder setOnKeyClickListener(OnKeyClickListener onKeyClickListener) {
        this.onKeyClickListener = onKeyClickListener;
        return this;
    }

    public Dialog show(View containerView) {
        Activity activity = (Activity) containerView.getContext();
        CustomKeyboardView contentView = new CustomKeyboardView(activity);
        contentView.setKeyboardTab(isKeyboardTab);
        contentView.setContainerView(containerView);
        final Dialog dialog = new BottomDialog(activity);
        dialog.setContentView(contentView);
        dialog.show();
        contentView.setOnKeyClickListener(new CustomKeyboardView.OnKeyClickListener() {
            @Override
            public void onOkClick(CustomKeyboardView parent) {
                if (onKeyClickListener != null) {
                    onKeyClickListener.onOkClick(dialog, parent);
                }
            }
        });
        return dialog;
    }

    public interface OnKeyClickListener {

        void onOkClick(Dialog dialog, CustomKeyboardView parent);
    }
}
