package com.mc.info.lumc;

import android.support.v7.widget.RecyclerView;
import android.widget.Filterable;

/**
 * Created by BurgerMan on 1/11/2017.
 */

public abstract class AbstractAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> implements Filterable {
    abstract void sortBy(Sort sort);
}
