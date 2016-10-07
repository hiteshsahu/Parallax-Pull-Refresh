package com.hitesh.parallaxrefresh.widget.refreshwidget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Path;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParallaxRefreshWidget extends CoordinatorLayout {

    private static final double HORIZONTAL_DISPLACEMENT = 1000.0;
    private static final double VERTICAL_DISPLACEMENT = 150.0;
    private static final int TRAJECTORY_DURATION = 2000;
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
    //Recyclerview
    @BindView(R.id.demo_recycle_view)
    RecyclerView recycler;
    private int[] defaultBackground = new int[3];
    private String TAG = ParallaxRefreshWidget.class.getSimpleName();
    private int currentGfCount = 0;
    private GlideDrawableImageViewTarget imageViewTarget;
    private int[] droneLaunchCoordinates = new int[2];

    public ParallaxRefreshWidget(Context context) {
        super(context);
        init(context);
    }

    public ParallaxRefreshWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallaxRefreshWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Fill any type of data you want in this recyclerview
     *
     * @return
     */
    public RecyclerView getRecycler() {
        return recycler;
    }

    /**
     * use this method to change background of first layer
     * or use @updateFirstLayer method
     *
     * @return
     */
    public ImageView getParallaxLayer1() {
        return parallaxLayer1;
    }

    /**
     * use this method to change background of second layer
     * or use @updateSecondLayer method
     *
     * @return
     */
    public ImageView getParallaxLayer2() {
        return parallaxLayer2;
    }

    /**
     * use this method to change background of third layer
     * or use @updateThirdLayer method
     *
     * @return
     */
    public ImageView getParallaxLayer3() {
        return parallaxLayer3;
    }

    /**
     * Add any event on drone
     *
     * @return
     */
    public ImageView getDrone() {
        return drone;
    }

    public GlideDrawableImageViewTarget getGifTarget() {
        return imageViewTarget;
    }

    protected void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.parallax_refresh_layout, this, true);
        ButterKnife.bind(this);

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

        //you can use Gifs on first layer of parallax it looks awesome
        imageViewTarget = new GlideDrawableImageViewTarget(parallaxLayer1);

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

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

    }

    /**
     * Call this method to initialize layers with default values
     *
     * @param layer1Resource
     * @param layer2Resource
     * @param layer3Resource
     */
    public void initializeLayers(int layer1Resource, int layer2Resource, int layer3Resource) {
        //add images on parallax layers
        Glide.with(getContext()).load(layer1Resource).into(parallaxLayer1);
        Glide.with(getContext()).load(layer2Resource).into(parallaxLayer2);
        Glide.with(getContext()).load(layer3Resource).into(parallaxLayer3);

        defaultBackground[0] = layer1Resource;
        defaultBackground[1] = layer2Resource;
        defaultBackground[2] = layer3Resource;

    }

    public void updateFirstLayer(String firstLayerUrl) {
        if (null != firstLayerUrl)
            Glide.with(getContext())
                    .load(firstLayerUrl)
                    .placeholder(defaultBackground[0])
                    .into(parallaxLayer1);

    }

    public void updateSecondLayer(String secondLayerUrl) {
        if (null != secondLayerUrl)
            Glide.with(getContext())
                    .load(secondLayerUrl)
                    .placeholder(defaultBackground[1])
                    .into(parallaxLayer2);

    }

    public void updateThirdLayer(String thirdLayerUrl) {
        if (null != thirdLayerUrl)
            Glide.with(getContext())
                    .load(thirdLayerUrl)
                    .placeholder(defaultBackground[2])
                    .into(parallaxLayer3);

    }

    public void updateAllLayers(String firstLayerUrl,
                                String secondLayerUrl,
                                String thirdLayerUrl) {

        updateFirstLayer(firstLayerUrl);

        updateThirdLayer(thirdLayerUrl);

        updateSecondLayer(secondLayerUrl);
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

        //fly to extream right
        path.quadTo(x + 0, y + 0, x + 2000, y - 1000);

        //fly low
        path.quadTo(x + 2000, y - 1000, x + 1000, y - 500);


        //rotate
        drone.setRotation(180);

        //come back and go left
        path.quadTo(x + 1000, y - 500, x - 300, y - 300);

        //fly low
        path.quadTo(x - 300, y - 500, x - 300, y - 200);

        //prepare comeback
        path.quadTo(x - 300, y - 200, x - 300, droneLaunchCoordinates[1] - drone.getHeight());

        //come back to launch position
        path.quadTo(x - 300, droneLaunchCoordinates[1] - drone.getHeight(), droneLaunchCoordinates[0] + drone.getWidth() / 2, droneLaunchCoordinates[1] - drone.getHeight() / 2);

        ObjectAnimator objectAnimator
                = ObjectAnimator.ofFloat(view, View.X,
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
                    drone.setTranslationY((float) (VERTICAL_DISPLACEMENT * Math.cos(value)));
                } else {
                    //decrase for rest
                    drone.setTranslationY((float) ((VERTICAL_DISPLACEMENT - 150.0 * value) * Math.cos(value)));
                }
            }
        });

        shootAnimation.start();
    }


}