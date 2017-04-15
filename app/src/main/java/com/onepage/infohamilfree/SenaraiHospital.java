package com.onepage.infohamilfree;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

public class SenaraiHospital extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DrawerLayout dlMain;
    ListView lvSettingMenu, lvSenaraiNegeri;
    TextView tvActTitle; // actionbar title

    private String[] settingMenu, senaraiNegeri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senarai_hospital);

        // call setup methods
        setup();
        setupActionBar();
        setupViews();
        setupListener();
    }

    // setup methods
    private void setup() {
        senaraiNegeri = getResources().getStringArray(R.array.menu_senarai_negeri);
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
        tvActTitle.setText(R.string.tajuk_act_info_minggu_hamil);

        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dlMain != null)
                    if (dlMain.isDrawerVisible(GravityCompat.START))
                        dlMain.closeDrawer(GravityCompat.START);
                    else
                        dlMain.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupViews() {
        ListViewMenuAdapter menuAdapter;

        menuAdapter = new ListViewMenuAdapter(this, senaraiNegeri);

        dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        lvSettingMenu = (ListView) findViewById(R.id.lvSettingMenu);
        lvSenaraiNegeri = (ListView) findViewById(R.id.lvSenaraiNegeri);

        lvSenaraiNegeri.setAdapter(menuAdapter);
        lvSettingMenu.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, settingMenu));
    }

    private void setupListener() {
        lvSenaraiNegeri.setOnItemClickListener(this);
        lvSettingMenu.setOnItemClickListener(this);
    }

    // listener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.lvSettingMenu:
                switch(position) {
                    case 0:
                        finish();
                        startActivity(new Intent(this, MainActivity.class));
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                break;
            case R.id.lvSenaraiNegeri:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.tajuk_alert_free_version)
                        .setMessage(R.string.desc_alert_free_version_senarai_hospital)
                        .setPositiveButton(R.string.nav_alert_free_version_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent gotoPlayStore;

                                gotoPlayStore = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.nav_goto_full_version)));

                                startActivity(gotoPlayStore);
                            }
                        })
                        .setNegativeButton(R.string.nav_alert_free_version_negative, null)
                        .create();

                alertDialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
