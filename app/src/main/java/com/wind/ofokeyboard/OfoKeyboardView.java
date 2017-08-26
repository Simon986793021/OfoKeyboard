package com.wind.ofokeyboard;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * Created by zhangcong on 2017/8/24.
 */

public class OfoKeyboardView extends KeyboardView {
    private Context context;
    public OfoKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }
}
