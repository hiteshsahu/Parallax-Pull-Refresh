package com.hiteshsahu.parallaxpullrefreshconcept;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hiteshsahu.parallaxpullrefreshconcept.data.CenterRepository;
import com.hiteshsahu.parallaxpullrefreshconcept.data.TestDataModel;


public class TestDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;

    OnCardClickListner onCardClickListner;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TestDataAdapter() {

    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder viewHolder;
        context = parent.getContext();

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recycle_view, parent, false);

        // set the view's size, margins, paddings and layout parameters
        viewHolder = new TestViewHolder(v);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TestViewHolder imageHolder = (TestViewHolder) holder;

        imageHolder.getMediaName().setText(getItem(position).getHeading());
        imageHolder.getMediaDesc().setText(getItem(position).getDetail());

        imageHolder.getImageTitle().setText(getItem(position).getHeading());

        Glide.with(context).load(Uri.parse(
                getItem(position).getImageUrl()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((TestViewHolder) holder).getMediaImage());


        // Here you apply the animation when the view is bound
        setAnimation(((TestViewHolder) holder).getContainer(), position);

        ((TestViewHolder) holder).getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return CenterRepository.getInstance().getListOfItems().size();
    }


    public TestDataModel getItem(int position) {
        return CenterRepository.getInstance().getItemAt(position);
    }

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

}
