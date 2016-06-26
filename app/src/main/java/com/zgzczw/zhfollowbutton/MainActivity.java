package com.zgzczw.zhfollowbutton;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button mTestBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTestBt = (Button) findViewById(R.id.test_bt);
        mTestBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTestBt.getText().toString().equals("关注")) {
                    mTestBt.setText("未关注");
                } else {
                    mTestBt.setText("关注");
                }
            }
        });
    }
}
