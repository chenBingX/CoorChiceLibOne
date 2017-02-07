package com.chenbing.coorchicelibone.AdaptersAndItemViews.Adapters;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.coorchicelibone.AdaptersAndItemViews.BaseItemViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/17
 * Notes:
 */

public class HeaderFooterRvAdapterWrapper extends RecyclerView.Adapter {
  private final RecyclerView.Adapter targetAdapter;
  private RecyclerView.LayoutManager layoutManager;
  private final List<View> headers;
  private final List<View> footers;
  private final int ITEM_HEADER = 1 << 1;
  private final int ITEM_FOOTER = 1 << 2;
  private int currentPosition;


  public HeaderFooterRvAdapterWrapper(@NonNull RecyclerView.Adapter targetAdapter) {
    this.targetAdapter = targetAdapter;
    registerTargetDataObserver(targetAdapter);

    headers = new ArrayList<>();
    footers = new ArrayList<>();
  }

  private void registerTargetDataObserver(RecyclerView.Adapter targetAdapter) {
    targetAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onChanged() {
        super.onChanged();
        notifyDataSetChanged();
      }

      @Override
      public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        notifyDataSetChanged();
      }

      @Override
      public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
        notifyDataSetChanged();
      }

      @Override
      public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        notifyDataSetChanged();
      }

      @Override
      public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        notifyDataSetChanged();
      }

      @Override
      public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        notifyDataSetChanged();
      }
    });
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    targetAdapter.onAttachedToRecyclerView(recyclerView);
    layoutManager = recyclerView.getLayoutManager();
    setupGridHeaderFooter(layoutManager);
  }

  private void setupGridHeaderFooter(RecyclerView.LayoutManager layoutManager) {
    if (layoutManager instanceof GridLayoutManager) {
      final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
      gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
          if (isHeaderPosition(position)) {
            return 1;
          }
          if (isFooterPosition(position)) {
            return 1;
          }
          return gridLayoutManager.getSpanCount();
        }
      });
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = null;
    if (viewType == ITEM_HEADER) {
      itemView = headers.get(currentPosition);
    }
    if (viewType == ITEM_FOOTER) {
      itemView = footers.get(currentPosition - targetAdapter.getItemCount());
    }

    if (itemView != null) {
      if (layoutManager instanceof StaggeredGridLayoutManager) {
        ViewGroup.LayoutParams targetParams = itemView.getLayoutParams();
        StaggeredGridLayoutManager.LayoutParams staggerLayoutParams;
        if (targetParams != null) {
          staggerLayoutParams =
              new StaggeredGridLayoutManager.LayoutParams(targetParams.width, targetParams.height);
        } else {
          staggerLayoutParams = new StaggeredGridLayoutManager.LayoutParams(
              ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        staggerLayoutParams.setFullSpan(true);
        itemView.setLayoutParams(staggerLayoutParams);
      }
      return new BaseItemViewHolder(itemView);
    }
    return targetAdapter.onCreateViewHolder(parent, viewType);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (getItemViewType(position) == ITEM_HEADER || getItemViewType(position) == ITEM_FOOTER) {
      return;
    }
    targetAdapter.onBindViewHolder(holder, position - headers.size());
  }

  @Override
  public int getItemCount() {
    return targetAdapter.getItemCount() + headers.size() + footers.size();
  }

  @Override
  public long getItemId(int position) {
    return targetAdapter.getItemId(position);
  }

  @Override
  public int getItemViewType(int position) {
    currentPosition = position;
    if (hasHeader() && isHeaderPosition(position)) {
      return ITEM_HEADER;
    }
    if (hasFooter() && isFooterPosition(position)) {
      return ITEM_FOOTER;
    }
    return targetAdapter.getItemViewType(position);
  }

  public boolean isHeaderPosition(int position) {
    for (int i = 0; i < headers.size(); i++) {
      if (position == i) {
        return true;
      }
    }
    return false;
  }

  public boolean isFooterPosition(int position) {
    for (int i = targetAdapter.getItemCount(); i < getItemCount() - 1; i++) {
      if (position == i) {
        return true;
      }
    }
    return false;
  }

  public boolean hasHeader() {
    return headers.size() > 0;
  }

  public boolean hasFooter() {
    return footers.size() > 0;
  }

  public void addHeader(View headerView) {
    headers.add(headerView);
    notifyDataSetChanged();
  }

  public void addFooter(View footerView) {
    footers.add(footerView);
    notifyDataSetChanged();
  }

  @Override
  public void setHasStableIds(boolean hasStableIds) {
    targetAdapter.setHasStableIds(hasStableIds);
  }

  @Override
  public void onViewRecycled(RecyclerView.ViewHolder holder) {
    targetAdapter.onViewRecycled(holder);
  }

  @Override
  public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
    return targetAdapter.onFailedToRecycleView(holder);
  }

  @Override
  public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    targetAdapter.onViewAttachedToWindow(holder);
  }

  @Override
  public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
    targetAdapter.onViewDetachedFromWindow(holder);
  }

  @Override
  public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
    targetAdapter.unregisterAdapterDataObserver(observer);
  }

  @Override
  public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
    targetAdapter.onDetachedFromRecyclerView(recyclerView);
  }
}
