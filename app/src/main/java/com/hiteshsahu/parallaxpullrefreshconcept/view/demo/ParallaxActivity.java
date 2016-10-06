package com.hiteshsahu.parallaxpullrefreshconcept.view.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
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
import com.hitesh.parallaxrefresh.widget.pullrefresh.VerticalOverScrollBounceEffectDecorator;
import com.hitesh.parallaxrefresh.widget.pullrefresh.adapters.NestedScrollViewOverScrollDecorAdapter;
import com.hitesh.parallaxrefresh.widget.pullrefresh.listeners.IOverScrollDecor;
import com.hitesh.parallaxrefresh.widget.pullrefresh.listeners.IOverScrollState;
import com.hitesh.parallaxrefresh.widget.pullrefresh.listeners.IOverScrollStateListener;
import com.hitesh.parallaxrefresh.widget.pullrefresh.listeners.IOverScrollUpdateListener;
import com.hiteshsahu.parallaxpullrefreshconcept.R;
import com.hiteshsahu.parallaxpullrefreshconcept.data.TestDataProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParallaxActivity extends AppCompatActivity {

    private static final double HORIZONTAL_DISPLACEMENT = 1000.0;
    private static final double VERTICAL_DISPLACEMNT = 150.0;
    private static final int TRAJECTORY_DURATION = 2000;//2000;
    @BindView(R.id.parallax_header)
    ParallaxNestedScrollView parallaxedPullRefreshView;
    @BindView(R.id.root)
    ViewGroup parentViewGroup;
    //Parallax Layers
    @BindView(R.id.parallax_layer_bottom)
    ImageView parallaxLayer1;
    @BindView(R.id.parallax_layer_intermediate)
    ImageView parallaxLayer2;
    @BindView(R.id.parallax_layer_top)
    ImageView parallaxLayer3;
    //ImageView drone
    @BindView(R.id.drone)
    ImageView drone;
    //Recyclerview for demo
    @BindView(R.id.demo_recycle_view)
    RecyclerView recycler;
    private String TAG = ParallaxActivity.class.getSimpleName();
    private int currentGfCount = 0;
    private GlideDrawableImageViewTarget imageViewTarget;
    private int[] droneLaunchCoordinates = new int[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        ButterKnife.bind(this);

        //This app uses images from internet to avoid huge apk size and souce size
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

                        // find drone coordinates
                        drone.getLocationOnScreen(droneLaunchCoordinates);
                    }
                }
        );

        //fill data for app
        TestDataProvider.getInstance().generateTestData();

        //you can use Gifs on first layer of parallax it looks awesome
        imageViewTarget = new GlideDrawableImageViewTarget(parallaxLayer1);

        //add images on parallax layers
        Glide.with(getApplicationContext()).load(R.drawable.night_sky).into(parallaxLayer1);
        Glide.with(getApplicationContext()).load(R.drawable.back_mountain_transparent_bg).into(parallaxLayer2);
        Glide.with(getApplicationContext()).load(R.drawable.coconut).into(parallaxLayer3);

        // Apply over-scroll in 'overshoot mode'  for nested scrollview
        IOverScrollDecor overScrollDecor = new VerticalOverScrollBounceEffectDecorator(new NestedScrollViewOverScrollDecorAdapter(parallaxedPullRefreshView),
                VerticalOverScrollBounceEffectDecorator.DEFAULT_TOUCH_DRAG_MOVE_RATIO_FWD,
                VerticalOverScrollBounceEffectDecorator.DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK,
                -1f);

        // Over-scroll listeners can be applied in standard form as well.
        overScrollDecor.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
                Log.e(TAG, "Scrolled By" + String.valueOf((int) offset));

                if (offset > 0) {
                    // 'view' is currently being over-scrolled from the top.

                    //convert Linear Motion of overscroll into
                    // Rotation motion of drone
                    drone.setRotation(-offset);

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

        overScrollDecor.setOverScrollStateListener(new IOverScrollStateListener() {
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

                        // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                        if (oldState == IOverScrollState.STATE_DRAG_START_SIDE) {

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                shootDrone(drone);
                            } else {
                                shootDronePreLollipop();
                            }

                        } else {
                            // i.e. (oldState == STATE_DRAG_END_SIDE)
                            // View is starting to bounce back from the *right-end*.
                        }
                        break;
                }
            }
        });

        //change background of parallax layer 1
        drone.setOnClickListener(new View.OnClickListener() {
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
                    Glide.with(getApplicationContext())
                            .load(TestDataProvider.testImageUrls[position])
                            .placeholder(R.drawable.night_sky)
                            .into(parallaxLayer1);

                } else if (position > 9 && position < 13) {

                    //next3 items are trees , update tree layer
                    Glide.with(getApplicationContext())
                            .load(TestDataProvider.testImageUrls[position])
                            .placeholder(R.drawable.coconut)
                            .into(parallaxLayer3);
                } else {
                    //other items are mountains , update mountain layer
                    Glide.with(getApplicationContext())
                            .load(TestDataProvider.testImageUrls[position])
                            .placeholder(R.drawable.back_mountain_transparent_bg)
                            .into(parallaxLayer2);
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
    private void shootDrone(final View view) {

        float x = view.getX();
        float y = view.getY();
        Path path = new Path();

        //TODO refine motion
        path.moveTo(x + 0, y + 0);
        path.quadTo(x + 0, y + 0, x + 1000, y - 500);
        path.quadTo(x + 1000, y - 500, x - 300, y - 500);
        path.quadTo(x - 300, y - 500, x - 300, y - 200);
        path.quadTo(x - 300, y - 500, droneLaunchCoordinates[0], droneLaunchCoordinates[1]);
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
    private void shootDronePreLollipop() {
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
                drone.setTranslationX((float) (HORIZONTAL_DISPLACEMENT * Math.sin(value * Math.PI)));

                //increase height till half the trajectory
                if (value < .5) {
                    drone.setTranslationY((float) (VERTICAL_DISPLACEMNT * Math.cos(value)));
                } else {
                    //decrase for rest
                    drone.setTranslationY((float) ((VERTICAL_DISPLACEMNT - 150.0 * value) * Math.cos(value)));
                }
            }
        });

        shootAnimation.start();
    }


}