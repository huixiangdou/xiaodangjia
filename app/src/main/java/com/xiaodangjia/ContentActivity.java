package com.xiaodangjia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaodangjia.adapter.MaterialAdapter;
import com.xiaodangjia.adapter.ProcessAdapter;
import com.xiaodangjia.moel.Material;
import com.xiaodangjia.moel.Process;
import com.xiaodangjia.moel.Recipe;
import com.headerfooter.songhang.library.SmartRecyclerAdapter;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {
    private Recipe recipe;
    private ProcessAdapter processAdapter;
    private MaterialAdapter materialAdapter;
    private StaggeredGridLayoutManager materialLinearLayout;
    private LinearLayoutManager processLinearLayout;
    private RecyclerView materialRecycler;
    private RecyclerView processRecycler;
    private View view;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("RECIPE");

        TextView name_tv = (TextView) findViewById(R.id.name_tv);
        TextView tag_tv = (TextView) findViewById(R.id.tag_tv);
        TextView cookingtime_tv = (TextView) findViewById(R.id.cookingtime_tv);
        TextView content_tv = (TextView) findViewById(R.id.content_tv);

        name_tv.setText(recipe.getName());
        cookingtime_tv.setText("烹饪时长: " + recipe.getCookingtime());
        tag_tv.setText("标签: " + recipe.getTag());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            content_tv.setText(Html.fromHtml(recipe.getContent(),Html.FROM_HTML_MODE_COMPACT));
        }else {
            content_tv.setText(Html.fromHtml(recipe.getContent()));
        }

        ArrayList<Material> materialArrayList = recipe.getMaterialArrayList();
        materialLinearLayout = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        materialAdapter = new MaterialAdapter(materialArrayList);
        SmartRecyclerAdapter smartRecyclerAdapter = new SmartRecyclerAdapter(materialAdapter);
        materialRecycler = (RecyclerView) findViewById(R.id.maerial_recycler);
        materialRecycler.setLayoutManager(materialLinearLayout);
        materialRecycler.setAdapter(smartRecyclerAdapter);

        ArrayList<Process> processArrayList = recipe.getProcessArrayList();
        processAdapter = new ProcessAdapter(processArrayList);
        processLinearLayout = new LinearLayoutManager(this);
        processRecycler = (RecyclerView) findViewById(R.id.process_recycler);
        processRecycler.setHasFixedSize(true);
        processRecycler.setLayoutManager(processLinearLayout);
        processRecycler.setAdapter(processAdapter);

//        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.content_ll);
//        Button button = new Button(this);
//        button.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        button.setText("123123");
//        linearLayout.addView(button);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
