package com.digvijaysingh.kiwi.Utils;

import java.util.ArrayList;

public class LimitedSizeQueue<String> extends ArrayList<String> {

    private int maxSize;

    public LimitedSizeQueue(int size){
        this.maxSize = size;
    }

    public boolean add(String k){
        boolean r = super.add(k);
        if (size() > maxSize){
            removeRange(0, size() - maxSize - 1);
        }
        return r;
    }

    public String getYongest() {
        return get(size() - 1);
    }

    public String getOldest() {
        return get(0);
    }
}