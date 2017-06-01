package com.marshalchen.ultimaterecyclerview.demo.rvComponents;

import android.animation.Animator;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.demo.R;

import java.security.SecureRandom;
import java.util.List;

import jp.wasabeef.recyclerview.internal.ViewHelper;


public class SimpleAnimationAdapter extends UltimateViewAdapter<RecyclerView.ViewHolder> {
    private List<String> stringList;

    public SimpleAnimationAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = 5;

    private boolean isFirstOnly = true;


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= stringList.size() : position < stringList.size()) && (customHeaderView != null ? position > 0 : true)) {

            ((ViewHolder) holder).textViewSample.setText(stringList.get(customHeaderView != null ? position - 1 : position));
            // ((ViewHolder) holder).itemView.setActivated(selectedItems.get(position, false));
        }
        if (!isFirstOnly || position > mLastPosition) {
            for (Animator anim : getAdapterAnimations(holder.itemView, AdapterAnimationType.ScaleIn)) {
                anim.setDuration(mDuration).start();
                anim.setInterpolator(mInterpolator);
            }
            mLastPosition = position;
        } else {
            ViewHelper.clear(holder.itemView);
        }

    }

    @Override
    public int getAdapterItemCount() {
        return stringList.size();
    }

    /**
     * requirement: FOOTER, HEADER. it does not bind and need to do that in the header binding
     *
     * @param view with no binding view of nothing
     * @return v
     */
    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return new ViewHolder(view, false);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new ViewHolder(view, false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_linear, parent, false);
        return new ViewHolder(v, true);
    }


    public void insert(String string, int position) {
        insertInternal(stringList, string, position);
    }

    public void remove(int position) {
        removeInternal(stringList, position);
    }

    public void clear() {
        clearInternal(stringList);
    }

    public void swapPositions(int from, int to) {
        swapPositions(stringList, from, to);
    }


    @Override
    public long generateHeaderId(int position) {
        URLogs.d("position--" + position + "   " + getItem(position));
        if (getItem(position).length() > 0)
            return getItem(position).charAt(0);
        else return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new ViewHolder(view, false);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.stick_text);
        textView.setText(String.valueOf(getItem(position).charAt(0)));
//        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAffffff"));
        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.stick_img);

        SecureRandom imgGen = new SecureRandom();
        switch (imgGen.nextInt(3)) {
            case 0:
                imageView.setImageResource(R.drawable.scn1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.jr13);
                break;
            case 2:
                imageView.setImageResource(R.drawable.jr16);
                break;
        }

    }
//
//    private int getRandomColor() {
//        SecureRandom rgen = new SecureRandom();
//        return Color.HSVToColor(150, new float[]{
//                rgen.nextInt(359), 1, 1
//        });
//    }


    public class ViewHolder extends UltimateRecyclerviewViewHolder {

        public TextView textViewSample;
        public ImageView imageViewSample;

        public ViewHolder(View itemView, boolean normal) {
            super(itemView);
//            itemView.setOnTouchListener(new SwipeDismissTouchListener(itemView, null, new SwipeDismissTouchListener.DismissCallbacks() {
//                @Override
//                public boolean canDismiss(Object token) {
//                    Logs.d("can dismiss");
//                    return true;
//                }
//
//                @Override
//                public void onDismiss(View view, Object token) {
//                   // Logs.d("dismiss");
//                    remove(getPosition());
//
//                }
//            }));
            if (normal) {
                textViewSample = (TextView) itemView.findViewById(
                        R.id.textview);
                imageViewSample = (ImageView) itemView.findViewById(R.id.imageview);
            }
        }
    }

    public String getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < stringList.size())
            return stringList.get(position);
        else return "";
    }

}
