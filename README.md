## 依赖
```java
dependencies {
    compile 'com.conghuahuadan.android:customkeyboard:1.0.6'
}
```

## 使用
```java
// 弹窗式
CustomKeyboard
        .popup()
        .setOnKeyClickListener(new PopupBuilder.OnKeyClickListener() {
            @Override
            public void onOkClick(Dialog dialog, CustomKeyboardView parent) {
                dialog.dismiss();
            }
        })
        .show(headerView);
```
```java
// 嵌入式
CustomKeyboard
        .insert()
        .setOnKeyClickListener(new InsertBuilder.OnKeyClickListener() {
            @Override
            public void onOkClick() {
            }
        })
        .bind(editText);
```

## 参考
> * [NumberKeyboard](https://github.com/xuejinwei/NumberKeyboard)
> * [CustomizeKeyboard](https://github.com/StomHong/CustomizeKeyboard)