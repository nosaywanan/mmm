package com.linkee.disni.expand;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.linkee.disni.R;

import java.util.List;

public class StickyHeader extends StickyItem {
    public List<StickyChild> child;
    public StickyHeader(Object data, List<StickyChild> child,AdapterDelegate delegate) {
        super(StickyItem.TITILE, data,delegate);
        this.child =child;
    }
    public static class HeaderDelegate extends AdapterDelegate<CharSequence>{
        @Override
        public int viewHolderResLayout() {
            return R.layout.item_title;
        }

        @Override
        public void bindViewHolder(View itemView, int position,CharSequence data) {
            TextView textView =itemView.findViewById(R.id.title);
            textView.setText(data);
        }

        @Override
        public void onCreateViewHolder(StickyExpandRecyclerViewAdapter adapter, StickyExpandRecyclerViewAdapter.StickyExpandViewHolder holder, StickyItem stickyItem) {
            holder.itemView.setOnClickListener(v->{
                StickyItem item = (StickyItem) v.getTag();
                boolean isParent = (item instanceof StickyHeader)&&item.type==TITILE;
                if (isParent){
                    boolean open =  item.checked;
                    if (open){
                        adapter.closeHeader((StickyHeader)item);
                    }else {
                        adapter.openHeader((StickyHeader)item);
                    }
                }
            });
        }
    }
}
