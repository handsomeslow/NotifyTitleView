package com.example.jun.notifytitlewidget;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    NotifyTitleView titleView;
    boolean isShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        titleView = (NotifyTitleView)findViewById(R.id.titleView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        titleView.showDeleteView();
        isShow = true;
        titleView.setOnNotifyClickListener(new NotifyTitleView.OnNotifyClickListener() {
            @Override
            public void onDeleteClick(View view) {
                Toast.makeText(MainActivity.this,"点击了删除",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHelpClick(View view) {
                Toast.makeText(MainActivity.this,"点击了帮助",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onShowDeteleClick(View view){
        if (isShow){
            titleView.hideDeleteView();
        } else {
            titleView.showDeleteView();
        }
        isShow =!isShow;
    }

    public void onNotifyViewClick(View view){
        titleView.notifyView();
    }

    public void onShowInfoClick(View view){
        if (isShow){
            titleView.showHelpView();
        } else {
            titleView.hideHelpView();
        }
        isShow =!isShow;
    }

    public void onShowAllViewClick(View view){
        if (isShow){
            titleView.showDeteleAndInfoView();
        } else {
            titleView.hideDeteleAndInfoView();
        }
        isShow =!isShow;
    }
}
