package ru.b7.rtphysics.Handbook;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.b7.rtphysics.BaseActivity;
import ru.b7.rtphysics.Database.Access_API.Finder;
import ru.b7.rtphysics.R;
import ru.b7.rtphysics.ScreenElements.FavoritesClickListener;
import ru.b7.rtphysics.ScreenElements.TagSetter;
import ru.b7.rtphysics.Search.ISearchPagesNames;
import ru.b7.rtphysics.Search.SearchDialog;
import ru.b7.rtphysics.Search.SearchLogic;
import ru.b7.rtphysics.google.SlidingTabLayout;

/**
 * Tab Activity.
 */
public class HandbookTabMainActivity extends BaseActivity implements View.OnClickListener ,ISearchPagesNames {

    public SlidingTabLayout tabs;
    private CharSequence titles[] = {"Статья", "Формулы"};
    private HandbookViewPagerAdapter adapter;
    private ViewPager pager;

    int numberOfTabs = 2;
    static TagSetter currentTag;
    static String currentContent;


    static List<String> resultOfSearch = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public static void HandbookTabCreate(TagSetter tag,String request)
    {
        currentTag = tag;
        currentContent = "Paragraph";
        resultOfSearch.add(new SearchLogic(request)
                .GetLinkedText(GetSearchSpaceStart().get("Paragraph")));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tabs_handbook_main);

        adapter= new HandbookViewPagerAdapter(getSupportFragmentManager(),
                titles, numberOfTabs, this, currentTag, GetResultOfSearch());

        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);
    }

    @Override
    public void onClick(View v) {
        //formulas clickable
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.handbook_article, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_favorite:
                View v = HandbookMenuActivity.getLastView();
                FavoritesClickListener.onClickFavorite(v);

            case R.id.action_settings:
                return true;

            case R.id.action_search:
                if (isSearchRun) {

                    item.setIcon(R.drawable.search);
                    adapter = new HandbookViewPagerAdapter(getSupportFragmentManager(),
                            titles, numberOfTabs, this, currentTag,null);

                    pager.setAdapter(adapter);
                    tabs.setViewPager(pager);

                    return true;
                } else {
                    item.setIcon(R.drawable.closeicon);
                    new SearchDialog(this);
                    return true;

                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void SearchCallBack(SearchDialog result){

        resultOfSearch = result.GetResult();

        if(resultOfSearch.get(0)==null){
            Toast.makeText(this,"Текст не найден",Toast.LENGTH_LONG).show();
            resultOfSearch.clear();
        }
        else {
            Toast.makeText(this, "Совпадение!", Toast.LENGTH_LONG).show();
            adapter = new HandbookViewPagerAdapter(getSupportFragmentManager(),
                    titles, numberOfTabs, this, currentTag,GetResultOfSearch());

            pager.setAdapter(adapter);
            tabs.setViewPager(pager);
        }

    }

    public String GetResultOfSearch(){
        try{
            return resultOfSearch.get(0);
        }finally {
            resultOfSearch.clear();
        }
    }

    @Override
    public List<Map<String, String>> GetSearchSpace() {

        List<Map<String,String>> singleParagraph= new ArrayList<>();
        singleParagraph.add(Finder.GetByID("Articles", currentTag.id));

        return singleParagraph;
    }

    public static Map<String, String> GetSearchSpaceStart() {
        return Finder.GetByID("Articles", currentTag.id);

    }

}
