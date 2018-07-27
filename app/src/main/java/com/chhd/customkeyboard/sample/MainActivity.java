package com.chhd.customkeyboard.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        CustomKeyboard
                .popup()
                .setOnKeyClickListener(new PopupBuilder.OnKeyClickListener() {
                    @Override
                    public void onOkClick(Dialog dialog, CustomKeyboardView parent) {
                        EditText etCount = parent.findViewById(R.id.et_count);
                        EditText etName = parent.findViewById(R.id.et_name);
                        EditText etPrice = parent.findViewById(R.id.et_price);
                        if (TextUtils.isEmpty(etCount.getText())) {
                            Toast.makeText(instance, "请输入数量", Toast.LENGTH_SHORT).show();
                            etCount.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(etName.getText())) {
                            Toast.makeText(instance, "请输入名称", Toast.LENGTH_SHORT).show();
                            etName.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(etPrice.getText())) {
                            Toast.makeText(instance, "请输入价格", Toast.LENGTH_SHORT).show();
                            etPrice.requestFocus();
                            return;
                        }
                        dialog.dismiss();
                    }
                })
                .show(headerView);
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
