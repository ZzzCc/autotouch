package com.easysidebar.utils;

import com.easysidebar.bean.ItemSort;

import java.util.Comparator;

public class PinyinComparator<T extends ItemSort> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
