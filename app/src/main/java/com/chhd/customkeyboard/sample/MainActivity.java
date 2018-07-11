package com.chhd.customkeyboard.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chhd.customkeyboard.CustomKeyboard;
import com.chhd.customkeyboard.CustomKeyboardView;
import com.chhd.customkeyboard.builder.InsertBuilder;
import com.chhd.customkeyboard.builder.PopupBuilder;

public class MainActivity extends AppCompatActivity {

    private MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        findViewById(R.id.btn_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });

        EditText et1 = findViewById(R.id.et1);
        EditText et2 = findViewById(R.id.et2);

        bind(et1);
        bind(et2);
    }

    private void popup() {
        View headerView = View.inflate(instance, R.layout.keyboard_header_xianyu, null);
        Dialog dialog = CustomKeyboard
                .popup()
                .setOnKeyClickListener(new PopupBuilder.OnKeyClickListener() {
                    @Override
                    public void onOkClick(Dialog dialog, CustomKeyboardView parent) {
//                        dialog.dismiss();
                        EditText editText = parent.findViewById(R.id.et_name);
                        editText.requestFocus();
                    }
                })
                .show(headerView);
        EditText etLast = dialog.findViewById(R.id.et_last);
        etLast.setVisibility(View.GONE);
    }

    private void bind(EditText editText) {
        CustomKeyboard
                .insert()
                .setOnKeyClickListener(new InsertBuilder.OnKeyClickListener() {
                    @Override
                    public void onOkClick() {
                    }
                })
                .bind(editText);
    }
}
