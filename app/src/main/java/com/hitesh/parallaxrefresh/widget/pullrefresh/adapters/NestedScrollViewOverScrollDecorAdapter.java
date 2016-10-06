package com.hitesh.parallaxrefresh.widget.pullrefresh.adapters;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ScrollView;

import com.hitesh.parallaxrefresh.widget.pullrefresh.VerticalOverScrollBounceEffectDecorator;

/**
 * An adapter that enables over-scrolling over a {@link ScrollView}.
 * <br/>Seeing that {@link ScrollView} only supports vertical scrolling, this adapter
 * should only be used with a {@link VerticalOverScrollBounceEffectDecorator}. For horizontal
 * over-scrolling,
 *
 * @author hitesh
 */
public class NestedScrollViewOverScrollDecorAdapter implements IOverScrollDecoratorAdapter {

    protected final NestedScrollView mView;

    public NestedScrollViewOverScrollDecorAdapter(NestedScrollView view) {
        mView = view;
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public boolean isInAbsoluteStart() {
        return !mView.canScrollVertically(-1);
    }

    @Override
    public boolean isInAbsoluteEnd() {
        return !mView.canScrollVertically(1);
    }
}
