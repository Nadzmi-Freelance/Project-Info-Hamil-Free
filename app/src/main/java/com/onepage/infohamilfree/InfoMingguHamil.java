package com.onepage.infohamilfree;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class InfoMingguHamil extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    DrawerLayout dlMain;
    ListView lvSettingMenu;
    LinearLayout llMingguSebelum, llMingguBerikut;
    TextView tvDescBayi, tvDescIbu;
    TextView tvActTitle; // actionbar title
    ImageView ivGambarInfo;

    private String[] settingMenu;
    private String descIbu, descBayi;
    private int descImg;

    String title; // for actionbar
    int position; // current minggu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_minggu_hamil);

        // call setup methods
        setup();
        setupViews();
        setupListener();
    }

    // setup methods
    private void setup() {
        Bundle prevAct;
        TypedArray imgList;

        prevAct = getIntent().getExtras();

        position = prevAct.getInt("mingguPos");
        title = prevAct.getString("title");

        imgList = getResources().obtainTypedArray(R.array.desc_img_minggu_hamil);
        descIbu = getResources().getStringArray(R.array.desc_ibu_minggu_hamil)[position];
        descBayi = getResources().getStringArray(R.array.desc_bayi_minggu_hamil)[position];
        descImg = imgList.getResourceId(position, -1);

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

        tvActTitle.setText(title);

        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dlMain.isDrawerVisible(GravityCompat.START))
                    dlMain.closeDrawer(GravityCompat.START);
                else
                    dlMain.openDrawer(GravityCompat.START);
            }
        });

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareContent;
                Intent shareIntent;

                shareIntent = new Intent();

                shareContent = title + "\n\n" +
                        "Info Ibu:\n" + descIbu + "\n\n" +
                        "Info Bayi:\n" + descBayi + "\n\n" +
                        "-- From Info Hamil (Test) --";

                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
                shareIntent.setType("text/plain");

                startActivity(Intent.createChooser(shareIntent, "Share info to..."));
            }
        });
    }

    private void setupViews() {
        setupActionBar();

        dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        lvSettingMenu = (ListView) findViewById(R.id.lvSettingMenu);
        llMingguSebelum = (LinearLayout) findViewById(R.id.llMingguSebelum);
        llMingguBerikut = (LinearLayout) findViewById(R.id.llMingguBerikut);
        tvDescBayi = (TextView) findViewById(R.id.tvDescBayi);
        tvDescIbu = (TextView) findViewById(R.id.tvDescIbu);
        ivGambarInfo = (ImageView) findViewById(R.id.ivGambarInfo);

        lvSettingMenu.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, settingMenu));

        ivGambarInfo.setImageResource(descImg);
        tvDescIbu.setText(descIbu);
        tvDescBayi.setText(descBayi);
    }

    private void setupListener() {
        lvSettingMenu.setOnItemClickListener(this);

        llMingguSebelum.setOnClickListener(this);
        llMingguBerikut.setOnClickListener(this);
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
        }
    }

    @Override
    public void onClick(View v) {
        Intent otherAct;
        int curPos;

        curPos = position;
        otherAct = new Intent(this, InfoMingguHamil.class);
        switch(v.getId()) {
            case R.id.llMingguSebelum:
                curPos -= 1;
                break;
            case R.id.llMingguBerikut:
                curPos += 1;
                break;
        }

        if(curPos <= 0)
            curPos = 0;
        else if(curPos > getResources().getStringArray(R.array.menu_info_minggu_kehamil).length - 1)
            curPos = getResources().getStringArray(R.array.menu_info_minggu_kehamil).length - 1;

        if((curPos == 0) || (curPos == 9) || (curPos == 19) || (curPos == 29) || (curPos == 39)) {
            otherAct.putExtra("title", getResources().getStringArray(R.array.menu_info_minggu_kehamil)[position]);
            otherAct.putExtra("mingguPos", curPos);
            startActivity(otherAct);
            finish();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.tajuk_alert_free_version)
                    .setMessage(R.string.desc_alert_free_version_minggu_hamil)
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
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
