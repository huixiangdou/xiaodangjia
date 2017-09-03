package com.xiaodangjia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.xiaodangjia.adapter.OnRecyclerViewItemClickListener;
import com.xiaodangjia.adapter.RecipeAdapter;
import com.xiaodangjia.basic.BasicActivity;
import com.xiaodangjia.moel.Recipe;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

public class ListActivity extends BasicActivity {
    private int all = 100;//总数据
    private int mInt = 0;//初始数据
    private ProgressBar progressBar;
    private RecipeAdapter recipeAdapter;
    private GetDataRecipe getDataRecipe;
    private String classid;
    private LinearLayoutManager mLinearLayoutManager;
    private LRecyclerView lRecyclerView;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        progressBar = (ProgressBar) findViewById(R.id.progress);

            mLinearLayoutManager = new LinearLayoutManager(context);
            lRecyclerView = (LRecyclerView) findViewById(R.id.list_recycler);
            lRecyclerView.setLayoutManager(mLinearLayoutManager);
            recipeAdapter = new RecipeAdapter(recipeArrayList);
            lRecyclerViewAdapter = new LRecyclerViewAdapter(recipeAdapter);
            lRecyclerView.setAdapter(lRecyclerViewAdapter);


        //添加点击事件
        recipeAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                Recipe recipe = (Recipe) object;
                Intent intent = new Intent(ListActivity.this,ContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("RECIPE",recipe);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //上拉刷新
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                recipeArrayList.clear();
                recipeAdapter = new RecipeAdapter(recipeArrayList);
                lRecyclerViewAdapter.notifyDataSetChanged();
                mInt = 0;
                getDataRecipe.getDatas("byclass","classid",classid,mInt);
            }
        });
        lRecyclerView.setLoadMoreEnabled(true);
        //下拉刷新
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(classid,mInt);
            }
        });

        //接收classid
        Intent intent = getIntent();
        if (intent.hasExtra("CHANNEL_NAME")) {
            classid = intent.getStringExtra("CHANNEL_NAME");
            getDataRecipe = new GetDataRecipe(progressBar, this, this);
            getData(classid, mInt);
        }
    }

    //获取数据
    public void getData(String classId,int mInt){
        if (mInt < all){
            getDataRecipe.getDatas("byclass","classid",classId,mInt);
        }else {
            lRecyclerView.setNoMore(true);
        }
    }

    @Override
    public void onSucess(ArrayList arrayList) {
        mInt = mInt + arrayList.size();
        recipeArrayList.addAll(arrayList);
        lRecyclerView.refreshComplete(10);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
