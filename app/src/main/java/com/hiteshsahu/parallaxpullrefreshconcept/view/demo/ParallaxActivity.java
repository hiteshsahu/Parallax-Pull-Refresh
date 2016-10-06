package com.hiteshsahu.parallaxpullrefreshconcept.view.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.hitesh.parallaxrefresh.widget.parallax.ParallaxNestedScrollView;
import com.hitesh.parallaxrefresh.widget.pullrefresh.IOverScrollDecor;
import com.hitesh.parallaxrefresh.widget.pullrefresh.IOverScrollState;
import com.hitesh.parallaxrefresh.widget.pullrefresh.IOverScrollStateListener;
import com.hitesh.parallaxrefresh.widget.pullrefresh.IOverScrollUpdateListener;
import com.hitesh.parallaxrefresh.widget.pullrefresh.VerticalOverScrollBounceEffectDecorator;
import com.hitesh.parallaxrefresh.widget.pullrefresh.adapters.NestedScrollViewOverScrollDecorAdapter;
import com.hiteshsahu.parallaxpullrefreshconcept.R;
import com.hiteshsahu.parallaxpullrefreshconcept.data.TestDataProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParallaxActivity extends AppCompatActivity {

    private static final double HORIZONTAL_DISPLACEMENT = 1000.0;
    private static final double VERTICAL_DISPLACEMNT = 150.0;
    private static final int TRAJECTORY_DURATION = 2000;//2000;
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
    @BindView(R.id.parent)
    ViewGroup parentViewGroup;
    int[] img_coordinates = new int[2];
    private String TAG = ParallaxActivity.class.getSimpleName();
    private int currentGfCount = 0;
    private GlideDrawableImageViewTarget imageViewTarget;
    private String[] urls = {
            //Add Sky
            "http://wallpapercave.com/wp/0e12dgE.png",
            "http://wallpapercave.com/wp/3I32MKI.jpg",
            "http://wallpapercave.com/wp/251fACI.jpg",
            "http://wallpapercave.com/wp/kKsNXp9.jpg",
            "http://wallpapercave.com/wp/jGNTLTN.jpg",
            "http://wallpapercave.com/wp/HfzLYZY.jpg",
            "http://wallpapercave.com/wp/2l8fNqv.jpg",
            "http://wallpapercave.com/wp/gF9n5Lk.jpg",
            "http://wallpapercave.com/wp/cRHOcKE.jpg",
            "http://wallpapercave.com/wp/KBk1YBX.jpg",
            //Tree
            "http://img03.deviantart.net/7245/i/2013/134/3/c/tree_2__png_with_transparency__by_bupaje-d65amxg.png",
            "http://img04.deviantart.net/95e3/i/2013/136/e/a/tree_3__png_with_transparency__by_bupaje-d65gvf3.png",
            "http://img07.deviantart.net/38e2/i/2013/135/b/2/tree_1__png_with_transparency__by_bupaje-d65ctod.png",
            //Hills
            "http://www.pngall.com/wp-content/uploads/2016/06/Mountain-PNG-Picture.png",
            "http://www.pngall.com/wp-content/uploads/2016/06/Mountain-PNG-File.png",
            "http://david-freed.com/wp-content/uploads/2014/04/Slider-Mountain-1440-wide-1.png",
            "http://pre02.deviantart.net/0627/th/pre/f/2013/218/c/8/mountain_landscape_by_regus_ttef-d602k3h.png",
            "http://www.pngall.com/wp-content/uploads/2016/06/Mountain-PNG-Image-180x180.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);

        ButterKnife.bind(this);

        Snackbar.make(parentViewGroup, "This App Download and Cache Images from INTERNET Consider turning WIFI On for better experience", Snackbar.LENGTH_LONG)
                .setAction("OK", null).show();

        // set a global layout listener which will be called when the layout pass is completed and the view is drawn
        parentViewGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        //Remove the listener before proceeding
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            parentViewGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            parentViewGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }

                        // measure your views here
                        fab.getLocationOnScreen(img_coordinates);
                    }
                }
        );

        TestDataProvider.getInstance().generateTestData();

        // Apply over-scroll in 'overshoot mode' - i.e. using the helper.
        IOverScrollDecor decor = new VerticalOverScrollBounceEffectDecorator(new NestedScrollViewOverScrollDecorAdapter(scrollView),
                VerticalOverScrollBounceEffectDecorator.DEFAULT_TOUCH_DRAG_MOVE_RATIO_FWD,
                VerticalOverScrollBounceEffectDecorator.DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK,
                -1f);

        // Over-scroll listeners can be applied in standard form as well.
        decor.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
                Log.e(TAG, "Scroll By" + String.valueOf((int) offset));

                if (offset > 0) {
                    // 'view' is currently being over-scrolled from the top.
                    fab.setRotation(-offset);

//                    calculateHorizontalDisplacemnet = HORIZONTAL_DISPLACEMENT * Math.sin(offset );

                } else if (offset < 0) {
                    // 'view' is currently being over-scrolled from the bottom.

                    //Dont do anything
                } else {
                    // No over-scroll is in-effect.
                    // This is synonymous with having (state == STATE_IDLE).
                }
            }
        });

        decor.setOverScrollStateListener(new IOverScrollStateListener() {
            @Override
            public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {
                switch (newState) {
                    case IOverScrollState.STATE_IDLE:
                        // No over-scroll is in effect.
                        break;
                    case IOverScrollState.STATE_DRAG_START_SIDE:
                        // Dragging started at the left-end.
                        break;
                    case IOverScrollState.STATE_DRAG_END_SIDE:
                        // Dragging started at the right-end.
                        break;
                    case IOverScrollState.STATE_BOUNCE_BACK:
                        if (oldState == IOverScrollState.STATE_DRAG_START_SIDE) {

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                moveit(fab);
                            } else {
                                moveItPreLollipop();
                            }

                            // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                        } else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                            // View is starting to bounce back from the *right-end*.
                        }
                        break;
                }
            }
        });

        Glide.with(getApplicationContext()).load(R.drawable.night_sky).into(sky);
        Glide.with(getApplicationContext()).load(R.drawable.back_mountain_transparent_bg).into(mountain);
        Glide.with(getApplicationContext()).load(R.drawable.coconut).into(tree);

        imageViewTarget = new GlideDrawableImageViewTarget(sky);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentGfCount == 0) {
                    Glide.with(getApplicationContext()).load(R.raw.sky_animated).into(imageViewTarget);
                    currentGfCount++;

                    Snackbar.make(view, "Cloudy Night", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (currentGfCount == 1) {
                    Glide.with(getApplicationContext()).load(R.raw.sky_animated_2).into(imageViewTarget);
                    currentGfCount++;

                    Snackbar.make(view, "Night Time", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (currentGfCount == 2) {
                    Glide.with(getApplicationContext()).load(R.raw.mornig_sky).into(imageViewTarget);
                    currentGfCount = 0;

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

                if (position <= 9) {
                    Glide.with(getApplicationContext()).load(urls[position]).into(sky);
                } else if (position > 9 && position < 13) {
                    Glide.with(getApplicationContext()).load(urls[position]).into(tree);
                } else {
                    Glide.with(getApplicationContext()).load(urls[position]).into(mountain);
                }
            }
        });
    }

    /**
     * Animate Shoot for Lollipop and Higher Android
     *
     * @param view
     */

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void moveit(final View view) {

        float x = view.getX();
        float y = view.getY();
        Path path = new Path();

        path.moveTo(x + 0, y + 0);
        path.quadTo(x + 0, y + 0, x + 1000, y - 500);
        path.quadTo(x + 1000, y - 500, x - 300, y - 500);
        path.quadTo(x - 300, y - 500, x - 300, y - 200);
        path.quadTo(x - 300, y - 500, img_coordinates[0], img_coordinates[1]);
        ObjectAnimator objectAnimator =
                null;

        objectAnimator = ObjectAnimator.ofFloat(view, View.X,
                View.Y, path);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    /**
     * Animate Shoot for pre Lollipop and lower Android
     */
    private void moveItPreLollipop() {
        final ValueAnimator shootAnimation = ValueAnimator.ofFloat(0, 1); // values from 0 to 1
        shootAnimation.setDuration(TRAJECTORY_DURATION); // 1 seconds duration from 0 to 1
        shootAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                float value = ((Float) (animation.getAnimatedValue()))
                        .floatValue();

                Log.e(TAG, " update position" + value);
                // Set translation of your view here. Position can be calculated
                // out of value. This code should move the view in a half circle.
                fab.setTranslationX((float) (HORIZONTAL_DISPLACEMENT * Math.sin(value * Math.PI)));

                //increase height till half the trajectory
                if (value < .5) {
                    fab.setTranslationY((float) (VERTICAL_DISPLACEMNT * Math.cos(value)));
                } else {
                    //decrase for rest
                    fab.setTranslationY((float) ((VERTICAL_DISPLACEMNT - 150.0 * value) * Math.cos(value)));
                }
            }
        });

        shootAnimation.start();
    }

}