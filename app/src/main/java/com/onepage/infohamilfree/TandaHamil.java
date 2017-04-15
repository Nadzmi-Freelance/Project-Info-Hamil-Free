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

import com.onepage.infohamilfree.adapter.ListViewMenuAdapter;

public class TandaHamil extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DrawerLayout dlMain;
    ListView lvSettingMenu, lvTanda;
    TextView tvActTitle; // actionbar title

    private String[] settingMenu, tandaMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanda_hamil);

        // call setup methods
        setup();
        setupViews();
        setupListener();
    }

    // setup methods
    private void setup() {
        tandaMenu = getResources().getStringArray(R.array.menu_tanda_hamil);
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
        tvActTitle.setText(R.string.tajuk_act_tanda_hamil);

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
        ListViewMenuAdapter menuAdapter;

        setupActionBar();

        menuAdapter = new ListViewMenuAdapter(this, tandaMenu);

        dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        lvSettingMenu = (ListView) findViewById(R.id.lvSettingMenu);
        lvTanda = (ListView) findViewById(R.id.lvTanda);

        lvSettingMenu.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, settingMenu));
        lvTanda.setAdapter(menuAdapter);
    }

    private void setupListener() {
        lvSettingMenu.setOnItemClickListener(this);
        lvTanda.setOnItemClickListener(this);
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
            case R.id.lvTanda: // TODO: 4/5/2017 - implement link untuk setiap tanda
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
