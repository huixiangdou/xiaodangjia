package com.xiaodangjia.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mobads.AppActivity;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.xiaodangjia.ContentActivity;
import com.xiaodangjia.GetDataRecipe;
import com.xiaodangjia.Interface.GetDataInterface;
import com.xiaodangjia.R;
import com.xiaodangjia.adapter.OnRecyclerViewItemClickListener;
import com.xiaodangjia.adapter.RecipeAdapter;
import com.xiaodangjia.moel.Recipe;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment implements GetDataInterface {
    private int all = 100;
    private int mInt = 0;
    private ProgressBar progressBar;
    private View view;
    private RecipeAdapter recipeAdapter;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private String classId;
    private ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
    private LRecyclerView lRecyclerView = null;
    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private GetDataRecipe getDataRecipe;
    private static String CLASSID = "classid";
    public BasicFragment() {}

    public static BasicFragment newInstance(String classId){
        BasicFragment basicFragment = new BasicFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CLASSID,classId);
        basicFragment.setArguments(bundle);
        return basicFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = getContext();
        view = inflater.inflate(R.layout.fragment_0, null);
        progressBar = view.findViewById(R.id.progress);
        getDataRecipe = new GetDataRecipe(progressBar,getContext(),this);
        classId = getArguments().getString(CLASSID);
        getDataRecipe.getDatas("byclass","classid",classId,mInt);

        mLayoutManager = new LinearLayoutManager(context);
        lRecyclerView = view.findViewById(R.id.recipe_recycler);
        lRecyclerView.setLayoutManager(mLayoutManager);
        recipeAdapter = new RecipeAdapter(recipeArrayList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(recipeAdapter);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        recipeAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                Recipe recipe = (Recipe) object;
                Intent intent = new Intent(context,ContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("RECIPE",recipe);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                recipeArrayList.clear();
                recipeAdapter = new RecipeAdapter(recipeArrayList);
                lRecyclerViewAdapter.notifyDataSetChanged();
                mInt = 0;
                getDataRecipe.getDatas("byclass","classid",classId,mInt);
            }
        });
        lRecyclerView.setLoadMoreEnabled(true);
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData(classId,mInt);
            }
        });

//        AppActivity.setActionBarColorTheme(AppActivity.ActionBarColorTheme.ACTION_BAR_WHITE_THEME);
//        String adPlaceId = "4714557"; //  重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
//        adview = new AdView(getContext(), adPlaceId);
//        RelativeLayout relativeLayout = view.findViewById(R.id.baidu_ad);
//        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        Button button = new Button(getContext());
//        relativeLayout.addView(button,rllp);
        return view;
    }

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
}
