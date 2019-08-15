package com.example.asus.dconfo_app;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Home_AppActivity extends AppCompatActivity {
    BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__app);
        bottomBar=findViewById(R.id.bar_home_access1);
        //bottomBar.setDefaultTab(R.id.home_administrador);
        //cargarMiddleBar();
    }


}
