package com.chenbing.coorchicelibone.CustemViews.filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenbing.iceweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coorchice on 2017/11/14.
 */

public class MagicAdapter<T> extends RecyclerView.Adapter {

    private AdapterRule<T> rule;
    private List<T> datas = new ArrayList<T>();
    private LayoutInflater inflater;
    private Context context;

    public MagicAdapter(Context context, List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        inflater = LayoutInflater.from(context);
    }

    public MagicAdapter(Context context, List<T> datas, AdapterRule<T> rule) {
        if (rule == null) {
            throw new NullPointerException("AdapterRule can't be null!");
        }
        this.rule = rule;
        this.datas.clear();
        this.datas.addAll(datas);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (rule == null) {
            throw new NullPointerException("You have to give a AdapterRule first!");
        }

        if (viewType != -1) {
            return new BaseItemViewHolder(inflater.inflate(viewType, parent, false));
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (rule == null) {
            throw new NullPointerException("You have to give a AdapterRule first!");
        }

        View itemView = holder.itemView;
        rule.bindData(this, itemView, datas, position);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (rule != null) {
            return rule.layout(position, datas);
        } else {
            return -1;
        }

    }

    public MagicAdapter setRule(AdapterRule<T> rule) {
        if (rule == null) {
            throw new NullPointerException("AdapterRule can't be null!");
        }
        this.rule = rule;
        notifyDataSetChanged();
        return this;
    }

    public MagicAdapter setDatas(List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
        return this;
    }

    public List<T> getDatas() {
        return datas;
    }

    public MagicAdapter setDataAndRule(List<T> datas, AdapterRule<T> rule) {
        if (rule == null) {
            throw new NullPointerException("AdapterRule can't be null!");
        }
        this.rule = rule;
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
        return this;
    }
}
