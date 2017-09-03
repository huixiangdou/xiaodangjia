package com.xiaodangjia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xiaodangjia.Interface.GetDataInterface;
import com.xiaodangjia.adapter.OnRecyclerViewItemClickListener;
import com.xiaodangjia.adapter.RecipeAdapter;
import com.xiaodangjia.basic.BasicActivity;
import com.xiaodangjia.moel.Recipe;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchActivity extends BasicActivity implements GetDataInterface {
    private static SearchActivity searchActivityInstance;
    private ProgressBar progressBar;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recipeRecyclerView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchActivityInstance = this;
        progressBar = (ProgressBar) findViewById(R.id.progress);

        Intent intent = getIntent();

        if (intent.hasExtra("key")){
            String key = intent.getStringExtra("key");
            GetDataRecipe getdata = new GetDataRecipe(progressBar,this,this);
            getdata.getDatas("search","keyword",key,30);
        }
    }

    public void search_bt(View view){
        EditText text = (EditText) findViewById(R.id.edit);
        String button_search_text = text.getText().toString();

        /**
         *校验输入的搜索关键字
         */
        String regEx = "[^\\-\u4E00-\u9FA5]";
        Pattern p   =   Pattern.compile(regEx);//pattern模式
        Matcher m   =   p.matcher(button_search_text);//进行匹配
        button_search_text = m.replaceAll("").trim();

        /**
         *搜索内容判断
         */
        if(button_search_text == null || button_search_text.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"非法的关键词或关键词为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(button_search_text.length()>16){
            Toast.makeText(getApplicationContext(),"搜索关键字过长,请缩短再试",Toast.LENGTH_SHORT).show();
            return;
        }

        if (searchActivityInstance != null){
            searchActivityInstance.finish();
        }

        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("key",button_search_text);
        startActivity(intent);
    }

    @Override
    public void onSucess(ArrayList arrayList) {
        while (arrayList.size() == 0){
            Toast.makeText(this,"没找到相关菜谱",Toast.LENGTH_LONG).show();
        }
        mLayoutManager = new LinearLayoutManager(this);
        recipeRecyclerView = (RecyclerView) findViewById(R.id.recipe_recycler);
        recipeRecyclerView.setLayoutManager(mLayoutManager);
        recipeAdapter = new RecipeAdapter(arrayList);
        recipeRecyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object object) {
                Recipe recipe = (Recipe) object;
                Intent intent = new Intent(SearchActivity.this,ContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("RECIPE",recipe);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
