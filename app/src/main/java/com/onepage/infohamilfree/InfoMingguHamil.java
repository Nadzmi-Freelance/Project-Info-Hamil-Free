package com.onepage.infohamilfree;

import android.content.Intent;
import android.content.res.TypedArray;
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

        otherAct = new Intent(this, InfoMingguHamil.class);
        switch(v.getId()) {
            case R.id.llMingguSebelum:
                position -= 1;
                break;
            case R.id.llMingguBerikut:
                position += 1;
                break;
        }

        if(position <= 0)
            position = 0;
        else if(position > getResources().getStringArray(R.array.menu_info_minggu_kehamil).length - 1)
            position = getResources().getStringArray(R.array.menu_info_minggu_kehamil).length - 1;

        otherAct.putExtra("title", getResources().getStringArray(R.array.menu_info_minggu_kehamil)[position]);
        otherAct.putExtra("mingguPos", position);
        startActivity(otherAct);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
