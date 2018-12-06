package br.com.garrav.projetogarrav;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SupportiveFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportive_friend);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Amigo Solidário");
    }
}
