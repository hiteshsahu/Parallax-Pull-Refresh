package com.hiteshsahu.parallaxpullrefreshconcept.view.demo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hitesh.parallaxrefresh.widget.refreshwidget.ParallaxRefreshWidget;
import com.hiteshsahu.parallaxpullrefreshconcept.R;
import com.hiteshsahu.parallaxpullrefreshconcept.data.TestDataProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoActivity extends AppCompatActivity {

    @BindView(R.id.demo_root)
    ViewGroup parentViewGroup;
    @BindView(R.id.parallex_refresh_widget)
    ParallaxRefreshWidget parallaxRefreshWidget;
    private int currentGfCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        ButterKnife.bind(this);

        //This app uses images from internet to avoid huge apk size and souce size
        Snackbar.make(parentViewGroup, "This App Download and Cache Images from INTERNET Consider turning WIFI On for better experience", Snackbar.LENGTH_LONG)
                .setAction("OK", null).show();


        //fill data for app
        TestDataProvider.getInstance().generateTestData();

        //add images on parallax layers
        parallaxRefreshWidget.initializeLayers(R.drawable.test_layer_sky, R.drawable.test_layer_mountain, R.drawable.test_layer_tree);


        //change background of parallax layer 1
        parallaxRefreshWidget.getDrone().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentGfCount == 0) {
                    Glide.with(getApplicationContext()).load(R.raw.sky_animated).into(parallaxRefreshWidget.getGifTarget());
                    currentGfCount++;

                    Snackbar.make(view, "Cloudy Night", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (currentGfCount == 1) {
                    Glide.with(getApplicationContext()).load(R.raw.sky_animated_2).into(parallaxRefreshWidget.getGifTarget());
                    currentGfCount++;

                    Snackbar.make(view, "Night Time", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (currentGfCount == 2) {
                    Glide.with(getApplicationContext()).load(R.raw.mornig_sky).into(parallaxRefreshWidget.getGifTarget());
                    currentGfCount = 0;

                    Snackbar.make(view, "Morning Time", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        RecyclerView recycler = parallaxRefreshWidget.getRecycler();
        //setup recycle view
        recycler.setHasFixedSize(false);
        recycler.setNestedScrollingEnabled(false);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        TestDataAdapter rcAdapter = new TestDataAdapter();
        recycler.setAdapter(rcAdapter);
        rcAdapter.setOnCardClickListner(new TestDataAdapter.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {

                if (position <= 9) {

                    //first 9 items are skys images , update sky layer
                    parallaxRefreshWidget.updateFirstLayer(
                            (TestDataProvider.testImageUrls[position]));

                } else if (position > 9 && position < 13) {

                    //next3 items are trees , update tree layer
                    parallaxRefreshWidget.updateThirdLayer
                            (TestDataProvider.testImageUrls[position]);
                } else {
                    //other items are mountains , update mountain layer
                    parallaxRefreshWidget.updateSecondLayer(TestDataProvider.testImageUrls[position]);
                }
            }
        });
    }


}