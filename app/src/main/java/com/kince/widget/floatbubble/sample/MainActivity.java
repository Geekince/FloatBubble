package com.kince.widget.floatbubble.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kince.widget.floatbubble.FloatBubbleView;

public class MainActivity extends AppCompatActivity {

    private FloatBubbleView mFloatBubbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFloatBubbleView = findViewById(R.id.float_bubble);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatBubbleView.startFloatAnim();
            }
        });

    }

}
