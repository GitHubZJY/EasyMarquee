package com.zjy.marqueelib;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/12/25
 * Author: Yang
 * Describe: adapter for marquee view
 */
public abstract class MarqueeAdapter<T> {

    private List<View> mViewList;
    private List<T> mDataList;
    private DataObserver<T> mObserver;

    public List<View> getChildViews() {
        return mViewList;
    }

    public View getChildAt(int position) {
        if (mViewList == null || position >= mViewList.size()) {
            return null;
        }
        return mViewList.get(position);
    }

    public void notifyItemChanged(int position, T data) {
        checkInitList();
        View itemView = null;
        if (position < mViewList.size()) {
            itemView = mViewList.get(position);
        }
        if (itemView != null && position < mDataList.size()) {
            mDataList.set(position, data);
            onBindView(position, itemView, data);
        }
    }

    public void notifyItemInsert(int position, T data) {
        checkInitList();
        View itemView = null;
        if (position < mViewList.size()) {
            itemView = onCreateView(position, data);
        }
        if (itemView != null && position < mDataList.size()) {
            mDataList.add(position, data);
            onBindView(position, itemView, data);
        }
    }

    public void setData(List<T> data) {
        checkInitList();
        if (data.size() == 1) {
            //兼容只有一个数据的场景过渡动画
            data.add(data.get(0));
        }
        mDataList.clear();
        mDataList.addAll(data);
        for (int index = 0; index < mDataList.size(); index++) {
            View itemView;
            if (index < mViewList.size()) {
                itemView = mViewList.get(index);
            } else {
                itemView = onCreateView(index, data.get(index));
                mViewList.add(itemView);
            }
            onBindView(index, itemView, mDataList.get(index));
        }
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (mObserver != null) {
            mObserver.onDataChange();
        }
    }

    public int getViewIndex(View view) {
        if (view == null) {
           return -1;
        }
        return mViewList.indexOf(view);
    }

    private void checkInitList() {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (mViewList == null) {
            mViewList = new ArrayList<>();
        }
    }

    public void registerDataSetObserver(DataObserver<T> observer) {
        mObserver = observer;
    }

    public void unRegisterDataSetObserver() {
        mObserver = null;
    }

    public abstract View onCreateView(int position, T data);

    public abstract void onBindView(int position, View view, T data);

}
