package com.linkee.disni.expand;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AdapterDelegate<T> {
     public abstract @LayoutRes int viewHolderResLayout();

     public   void bindViewHolder(View itemView, int position,T data){}

     public  void onCreateViewHolder(StickyExpandRecyclerViewAdapter adapter, StickyExpandRecyclerViewAdapter.StickyExpandViewHolder holder, StickyItem stickyItem){}
}
