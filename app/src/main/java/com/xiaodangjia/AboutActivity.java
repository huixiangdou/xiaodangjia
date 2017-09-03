package com.xiaodangjia;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;

public class AboutActivity extends AppCompatActivity {
    private TextView aboutTextView;
    private TextView versionTextView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutTextView = (TextView) findViewById(R.id.about_tv);
        versionTextView = (TextView) findViewById(R.id.version_tv);

        String versionName = null;

        try {
            versionName = this.getPackageManager().getPackageInfo(this.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }

        StringBuilder text = new StringBuilder();
        versionTextView.setText("版本号:"+versionName+".");
        text.append("更新日期:2017-08-29\n");
        text.append("官网:www.xiaodangjia.com\n");
        text.append("开发者:好菜谱\n");
        text.append("微信:好菜谱\n");
        text.append("微信公众号:好菜谱\n");
        aboutTextView.setText(text.toString());
    }

    public void about_bt(View view){
        //检查更新
        BDAutoUpdateSDK.uiUpdateAction(AboutActivity.this, new MyUICheckUpdateCallback());
    }

    //回调接口
    private class MyUICheckUpdateCallback implements UICheckUpdateCallback{
        @Override
        public void onNoUpdateFound() {

        }

        @Override
        public void onCheckComplete() {
            Toast.makeText(AboutActivity.this,"您的版本为最新哦",Toast.LENGTH_LONG).show();
        }
    }
}
