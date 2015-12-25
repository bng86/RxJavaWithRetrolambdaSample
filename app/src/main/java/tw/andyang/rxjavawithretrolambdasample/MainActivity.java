package tw.andyang.rxjavawithretrolambdasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tw.andyang.rxjavawithretrolambdasample.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit();
    }

}
