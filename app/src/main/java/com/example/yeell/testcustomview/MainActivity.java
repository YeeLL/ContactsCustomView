package com.example.yeell.testcustomview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomView customView = (CustomView) findViewById(R.id.customView);
        final TextView textView = (TextView) findViewById(R.id.text);
        customView.setOnTouchBarListener(new CustomView.OnTouchBarListener() {
            @Override
            public void currentPostion(int position) {

            }

            @Override
            public void currentStr(String str) {
                textView.setText(str);
            }

            @Override
            public void show() {
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void hide() {
                textView.setVisibility(View.INVISIBLE);
            }
        });
    }
}
