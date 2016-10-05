package com.hiteshsahu.parallaxpullrefreshconcept;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.hiteshsahu.parallaxpullrefreshconcept.data.CenterRepository;
import com.hiteshsahu.parallaxpullrefreshconcept.data.TestDataModel;
import com.nirhart.parallaxscroll.views.ParallaxNestedScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ParallaxActivity extends AppCompatActivity {

    @BindView(R.id.parallax_header)
    ParallaxNestedScrollView scrollView;
    @BindView(R.id.mountain_bg)
    ImageView mountain;
    @BindView(R.id.sky_bg)
    ImageView sky;
    @BindView(R.id.coconut_bg)
    ImageView tree;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.testData)
    RecyclerView recycler;

    int blax = 0;
    private GlideDrawableImageViewTarget imageViewTarget;

    String[] urls = {
            "http://wallpapercave.com/wp/0e12dgE.png",
            "http://wallpapercave.com/wp/3I32MKI.jpg",
            "http://wallpapercave.com/wp/251fACI.jpg",
            "http://wallpapercave.com/wp/kKsNXp9.jpg",
            "http://wallpapercave.com/wp/jGNTLTN.jpg",
            "http://wallpapercave.com/wp/HfzLYZY.jpg",
            "http://wallpapercave.com/wp/2l8fNqv.jpg",
            "http://wallpapercave.com/wp/gF9n5Lk.jpg",
            "http://wallpapercave.com/wp/cRHOcKE.jpg",
            "http://wallpapercave.com/wp/KBk1YBX.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);

        ButterKnife.bind(this);

        generateTestData();

        OverScrollDecoratorHelper.setUpOverScroll(scrollView);

        Glide.with(getApplicationContext()).load(R.drawable.night_sky).into(sky);
        Glide.with(getApplicationContext()).load(R.drawable.back_mountain_transparent_bg).into(mountain);
        Glide.with(getApplicationContext()).load(R.drawable.coconut).into(tree);

        imageViewTarget = new GlideDrawableImageViewTarget(sky);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (blax == 0) {
                    Glide.with(getApplicationContext()).load(R.raw.sky_animated).into(imageViewTarget);
                    blax++;

                    Snackbar.make(view, "Day time", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (blax == 1) {
                    Glide.with(getApplicationContext()).load(R.raw.sky_animated_2).into(imageViewTarget);
                    blax++;

                    Snackbar.make(view, "Night Time", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (blax == 2) {
                    Glide.with(getApplicationContext()).load(R.raw.mornig_sky).into(imageViewTarget);
                    blax = 0;

                    Snackbar.make(view, "Morning Time", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        recycler.setHasFixedSize(true);
        recycler.setNestedScrollingEnabled(false);

        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        TestDataAdapter rcAdapter = new TestDataAdapter();
        recycler.setAdapter(rcAdapter);
        rcAdapter.setOnCardClickListner(new TestDataAdapter.OnCardClickListner() {
            @Override
            public void OnCardClicked(View view, int position) {


            }
        });
    }

    private void generateTestData() {
        for (int i = 0; i < 10; i++) {
            CenterRepository.getInstance().getListOfItems().add(new TestDataModel("Test Heading" + i,
                    "¯\\_(ツ)_/¯" + i, urls[i]));
        }
    }
}
