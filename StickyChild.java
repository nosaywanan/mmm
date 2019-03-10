package com.linkee.disni.expand;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.linkee.disni.R;

public class StickyChild extends StickyItem {
    public StickyChild(Object data,AdapterDelegate delegate) {
        super(StickyItem.CHILD, data,delegate);
    }
    public static class ChildDelegate extends AdapterDelegate<CharSequence>{
        @Override
        public int viewHolderResLayout() {
            return R.layout.item_child;
        }
        @Override
        public void bindViewHolder(View itemView, int position,CharSequence data) {
            TextView textView =itemView.findViewById(R.id.child_name);
            textView.setText(data);
        }

    }
}
