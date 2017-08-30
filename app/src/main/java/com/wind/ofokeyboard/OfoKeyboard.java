package com.wind.ofokeyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.opengl.Visibility;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by zhangcong on 2017/8/28.
 */

public class OfoKeyboard {
    private Activity activity;
    private Keyboard keyboard;
    private OfoKeyboardView keyboardView;
    private EditText editText;
    public  OfoKeyboard (Activity activity)
    {
        this.activity=activity;
        keyboard=new Keyboard(activity,R.xml.keyboard);
        Log.i(">>>>>",keyboard.getClass().getName().toString()+keyboard.toString());
        keyboardView= (OfoKeyboardView) activity.findViewById(R.id.keyboard_view);
        Log.i(">>>>>>",keyboardView.toString());
    }
    public void attachTo(EditText editText){
        this.editText=editText;
        hideSystemSofeKeyboard(activity,editText);
        showSoftKeyboard();
    }


    private void showSoftKeyboard() {
        if (keyboard == null) {
            keyboard = new Keyboard(activity, R.xml.keyboard);
        } else {
            keyboardView.setKeyboard(keyboard);
            keyboardView.setOnKeyboardActionListener(listener);
        }
    }

    private KeyboardView.OnKeyboardActionListener listener=new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable=editText.getText();
            int start =editText.getSelectionStart();
            if (primaryCode==Keyboard.KEYCODE_DELETE)//key  codes 为-5
            {
                if (editable!=null&&editable.length()>0)
                {
                    if (start>0)
                    {
                        editable.delete(start-1,start);
                    }
                }
            }
            else if (primaryCode==Keyboard.KEYCODE_CANCEL)
            {
                hideKeyBoard();
                if (mCancelClick!=null)
                {
                    mCancelClick.onCancelClick();
                }
            }
            else if (primaryCode==Keyboard.KEYCODE_DONE)
            {
                hideKeyBoard();
                if (mOkClick!=null)
                {
                    mOkClick.onOkClick();
                }
            }
           else {
                Log.i(">>>>>>",primaryCode+"1");
                Log.i(">>>>>>",(char) primaryCode+"2");
                editable.insert(start,Character.toString((char) primaryCode));
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
    };
    public interface OnOkClick {
        void onOkClick();
    }
    public interface OnCancelClcik{
        void onCancelClick();
    }
    public OnOkClick mOkClick;
    public OnCancelClcik mCancelClick;
    public void setOnOkClick(OnOkClick onOkClick)
    {
        this.mOkClick=onOkClick;
    }
    public void setOnCancelClick (OnCancelClcik onCancelClick)
    {
        this.mCancelClick=onCancelClick;
    }
    private void hideKeyBoard() {
        int visibility=keyboardView.getVisibility();
        if (visibility==KeyboardView.VISIBLE)
        {
            keyboardView.setVisibility(KeyboardView.GONE);
        }
    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSofeKeyboard(Context context, EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
