package com.linkee.disni.expand;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class StickyExpandRecyclerViewAdapter extends RecyclerView.Adapter<StickyExpandRecyclerViewAdapter.StickyExpandViewHolder> {
    private List<StickyItem> stickyItemList=new ArrayList<>();
    @Override
    public int getItemViewType(int position) {
        return stickyItemList.get(position).type;
    }
    @NonNull
    @Override
    public StickyExpandViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        StickyExpandViewHolder holder =new StickyExpandViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(stickyItemList.get(position).delegate.viewHolderResLayout(),null));
        stickyItemList.get(position).delegate.onCreateViewHolder(this,holder,stickyItemList.get(position));
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull StickyExpandViewHolder holder, int position) {
        holder.itemView.setTag(stickyItemList.get(position));
        stickyItemList.get(position).delegate.bindViewHolder(holder.itemView,position,stickyItemList.get(position).data);
    }

    @Override
    public int getItemCount() {
        return stickyItemList.size();
    }
    public void addHeader(StickyHeader header){
        int headIndex =header.index<0?0:stickyItemList.size();
        stickyItemList.add(header);
        if (header.checked){
            stickyItemList.addAll(header.child);
        }
        notifyItemRangeInserted(headIndex,stickyItemList.size()-1-headIndex);
    }

    public void closeHeader(StickyHeader stickyItem) {
        int size = stickyItem.child.size();
        int index = stickyItemList.indexOf(stickyItem);
        if (index>=0){
            for (int i = 0; i < size; i++) {
                stickyItemList.remove(index+1);
            }
            stickyItem.checked=false;
            notifyItemRangeRemoved(index+1,size);
        }
    }

    public void openHeader(StickyHeader stickyItem) {
        int size = stickyItem.child.size();
        int index = stickyItemList.indexOf(stickyItem);
        if (index>=0){
            for (int i = 0; i < size; i++) {
                stickyItemList.add(index+1,stickyItem.child.get(i));
            }
            stickyItem.checked=true;
            notifyItemRangeInserted(index+1,size);
        }
    }


    public class StickyExpandViewHolder extends RecyclerView.ViewHolder{
        public StickyExpandViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
