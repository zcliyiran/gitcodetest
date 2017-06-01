package com.marshalchen.ultimaterecyclerview.demo.multiitemdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.marshalchen.ultimaterecyclerview.UltimateDifferentViewTypeAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.demo.R;

import java.util.ArrayList;
import java.util.List;

public class MultiViewTypesRecyclerViewAdapter extends UltimateDifferentViewTypeAdapter {
    private List<String> mDataset;

    private enum SwipedState {
        SHOWING_PRIMARY_CONTENT,
        SHOWING_SECONDARY_CONTENT
    }

    private List<SwipedState> mItemSwipedStates;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends UltimateRecyclerviewViewHolder {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MultiViewTypesRecyclerViewAdapter(List<String> dataSet) {
        mDataset = dataSet;
        mItemSwipedStates = new ArrayList<>();
        for (int i = 0; i < dataSet.size(); i++) {
            mItemSwipedStates.add(i, SwipedState.SHOWING_PRIMARY_CONTENT);
        }

        putBinder(SampleViewType.SAMPLE1, new Sample1Binder(this, dataSet));
        putBinder(SampleViewType.SAMPLE2, new Sample2Binder(this, dataSet));
        putBinder(SampleViewType.SAMPLE3, new Sample1Binder(this, dataSet));
        //  ((Sample2Binder) getDataBinder(SampleViewType.SAMPLE2)).addAll(dataSet);
    }

    public void insert(String string, int position) {
        insertInternal(mDataset, string, position);
    }

    public void remove(int position) {
        removeInternal(mDataset, position);
    }


    /**
     * requirement: FOOTER, HEADER. it does not bind and need to do that in the header binding
     *
     * @param view with no binding view of nothing
     * @return v
     */
    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_linear, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override {@link #onBindViewHolder} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public Enum getEnumFromPosition(int position) {
        if (position % 2 == 1) {
            return SampleViewType.SAMPLE1;
        } else {
            return SampleViewType.SAMPLE2;
        }
    }

    @Override
    public Enum getEnumFromOrdinal(int ordinal) {
        return SampleViewType.values()[ordinal];
    }

    enum SampleViewType {
        SAMPLE1, SAMPLE2, SAMPLE3
    }


}