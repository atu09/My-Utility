package atirek.pothiwala.utility.listener;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 * Created by Atirek Pothiwala on 2/4/2019.
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 2;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean isLoading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;
    // Sets the delay time to load more data
    private int secondsToLoadMore = 2;

    private RecyclerView.LayoutManager layoutManager;

    private Handler handler;

    private Runnable loaderRunnable = new Runnable() {
        @Override
        public void run() {
            onLoadMore(currentPage, 0, null);
        }
    };

    public void setSecondsToLoadMore(int secondsToLoadMore) {
        this.secondsToLoadMore = secondsToLoadMore;
    }

    public void handleLoadingMore() {
        this.currentPage--;
        if (this.currentPage < 0){
            this.currentPage = 0;
        }
        handler.postDelayed(loaderRunnable, secondsToLoadMore * 1000);
    }

    public EndlessScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.handler = new Handler();
        this.layoutManager = layoutManager;

        if (layoutManager instanceof GridLayoutManager) {
            visibleThreshold = visibleThreshold * ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            visibleThreshold = visibleThreshold * ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(@NonNull RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = layoutManager.getItemCount();

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            isLoading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!isLoading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            isLoading = true;
        }
    }

    // Call this method whenever performing new searches
    public void resetState() {
        handler.removeCallbacks(loaderRunnable);
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.isLoading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

}