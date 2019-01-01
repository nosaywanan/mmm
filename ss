package com.hikvision.skin;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NiksViewGroup  extends NiksView{
    public NiksViewGroup(NiksView parent, ViewGroup viewGroup) {
        super(parent, viewGroup);
        initChild(viewGroup);
    }
    private NiksView[] mChilds;
    private void initChild(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        mChilds = new NiksView[childCount];
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            boolean viewGroupFlag = (view instanceof  ViewGroup)&& !(view instanceof RecyclerView)&&!(view instanceof ViewPager);
            mChilds[i] = viewGroupFlag ?new NiksViewGroup(this, (ViewGroup) view)
                    :new NiksView(this,view);
        }
    }

    public int getChildCount() {
        return mChilds.length;
    }

    public NiksView getChildAt(int i) {
        return mChilds[i];
    }
}
