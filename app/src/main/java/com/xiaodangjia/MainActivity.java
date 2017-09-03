package com.xiaodangjia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;
import com.xiaodangjia.adapter.MFragmentAdapter;
import com.xiaodangjia.basic.BasicActivity;
import com.xiaodangjia.fragment.BasicFragment;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BasicActivity{
    private String TAG = "MAINACTIVITY";
    public static final String[] titles = new String[]{"减肥","瘦身","美容","排毒","通乳","补钙","开胃","防癌","白领","司机"};
    private TabLayout mTabLayouot;
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragmentArrayList;
//    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTabLayouot = (TabLayout) findViewById(R.id.main_tl);
        mViewPager = (ViewPager) findViewById(R.id.main_vp);
        mTabLayouot.setupWithViewPager(mViewPager);

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new BasicFragment().newInstance("2"));
        fragmentArrayList.add(new BasicFragment().newInstance("3"));
        fragmentArrayList.add(new BasicFragment().newInstance("6"));
        fragmentArrayList.add(new BasicFragment().newInstance("10"));
        fragmentArrayList.add(new BasicFragment().newInstance("15"));
        fragmentArrayList.add(new BasicFragment().newInstance("24"));
        fragmentArrayList.add(new BasicFragment().newInstance("80"));
        fragmentArrayList.add(new BasicFragment().newInstance("96"));
        fragmentArrayList.add(new BasicFragment().newInstance("133"));
        fragmentArrayList.add(new BasicFragment().newInstance("137"));
        MFragmentAdapter adapter = new MFragmentAdapter(getSupportFragmentManager(),fragmentArrayList, Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
//        adView.destroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.nav_about:
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_search:
                Intent intent1 = new Intent(this,SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_channel:
                Intent intent2 = new Intent(this,ChannelActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_history:
                Intent intent3 = new Intent(this,HistoryActivity.class);
                startActivity(intent3);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
