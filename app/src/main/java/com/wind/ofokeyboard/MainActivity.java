package com.wind.ofokeyboard;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= (EditText) findViewById(R.id.et_numberplate);
        final OfoKeyboard keyboard=new OfoKeyboard(MainActivity.this);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard.attachTo(editText);

            }
        });
    }
}
