package com.xiaodangjia;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdView;

/**
 * Created by Administrator on 2017/9/1.
 */

public class BaiduAd {

    public AdView getAdView(Context context, String adid){
        AdView adView = new AdView(context,adid);
        return adView;
    }

    public void addAdView(AdView adView,View view){
        RelativeLayout relativeLayout = view.findViewById(R.id.baidu_ad);
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(adView,rllp);
    }
}
