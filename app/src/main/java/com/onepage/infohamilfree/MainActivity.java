package com.onepage.infohamilfree;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DrawerLayout dlMain;
    ListView lvSettingMenu, lvMainMenu;
    TextView tvActTitle; // actionbar title

    private String[] settingMenu, mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // call setup methods
        setup();
        setupViews();
        setupListener();
    }

    // setup methods
    private void setup() {
        mainMenu = getResources().getStringArray(R.array.menu_utama);
        settingMenu = getResources().getStringArray(R.array.menu_setting);
    }

    private void setupActionBar() {
        ActionBar actionBar;
        LayoutInflater layoutInflater;
        View customActionbar;
        ImageButton ibMenu, ibShare;

        actionBar = getSupportActionBar();
        layoutInflater = LayoutInflater.from(this);
        customActionbar = layoutInflater.inflate(R.layout.actionbar_custom, null);

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(customActionbar);
        actionBar.setDisplayShowCustomEnabled(true);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        ibShare = (ImageButton) findViewById(R.id.ibShare);
        tvActTitle = (TextView) findViewById(R.id.tvActTitle);

        ibShare.setVisibility(View.INVISIBLE);
        tvActTitle.setText(R.string.app_name);

        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dlMain.isDrawerVisible(GravityCompat.START))
                    dlMain.closeDrawer(GravityCompat.START);
                else
                    dlMain.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupViews() {
        setupActionBar();

        dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        lvMainMenu = (ListView) findViewById(R.id.lvMainMenu);
        lvSettingMenu = (ListView) findViewById(R.id.lvSettingMenu);

        lvMainMenu.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mainMenu));
        lvSettingMenu.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, settingMenu));
    }

    private void setupListener() {
        lvMainMenu.setOnItemClickListener(this);
        lvSettingMenu.setOnItemClickListener(this);
    }

    // Listeners
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.lvSettingMenu:
                switch (position) {
                    case 0:
                        finish();
                        startActivity(new Intent(this, MainActivity.class));
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                dlMain.closeDrawers();
                break;
            case R.id.lvMainMenu:
                switch (position) {
                    case 0:
                        startActivity(new Intent(this, TandaHamil.class));
                        break;
                    case 1:
                        break;
                    case 2:
                        startActivity(new Intent(this, MingguHamil.class));
                        break;
                    case 3:
                        startActivity(new Intent(this, TipsPenjagaanIbu.class));
                        break;
                    case 4:
                        startActivity(new Intent(this, TipsPenjagaanBayi.class));
                        break;
                    case 5:
                        startActivity(new Intent(this, SenaraiHospital.class));
                        break;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
