package com.linkee.disni.expand;

public abstract class StickyItem {
    public static final int TITILE=0;
    public static final int CHILD=1;
    public static final int CHILD_MERGE=2;
    public int type;
    public Object data;
    public boolean checked;
    public int index=-1;
    public AdapterDelegate delegate;
    public StickyItem(int type,Object data,AdapterDelegate delegate){
        this.type =type;
        this.data =data;
        this.delegate=delegate;
    }
}
