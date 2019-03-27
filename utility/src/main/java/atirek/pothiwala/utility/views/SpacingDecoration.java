package atirek.pothiwala.utility.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class SpacingDecoration extends RecyclerView.ItemDecoration {

    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private boolean mIncludeEdge;

    public SpacingDecoration(Context context, @DimenRes int horizontal_spacing, @DimenRes int vertical_spacing, boolean includeEdge) {
        mHorizontalSpacing = context.getResources().getDimensionPixelSize(horizontal_spacing);
        mVerticalSpacing = context.getResources().getDimensionPixelSize(vertical_spacing);
        mIncludeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // Only handle the vertical situation
        int position = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            int column = position % spanCount;
            getGridItemOffsets(outRect, position, column, spanCount);
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            int column = lp.getSpanIndex();
            getGridItemOffsets(outRect, position, column, spanCount);
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) parent.getLayoutManager());

            if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (position == 0) {
                    outRect.left = mHorizontalSpacing;
                }
                outRect.right = mHorizontalSpacing;
            } else {
                outRect.left = mHorizontalSpacing;
                outRect.right = mHorizontalSpacing;
            }

            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                if (position == 0 && mIncludeEdge) {
                    outRect.top = mVerticalSpacing;
                } else if (!mIncludeEdge && position > 0) {
                    outRect.top = mVerticalSpacing;
                }
                outRect.bottom = mVerticalSpacing;
            } else {
                //outRect.top = mVerticalSpacing;
                //outRect.bottom = mVerticalSpacing;
            }
        }
    }

    private void getGridItemOffsets(Rect outRect, int position, int column, int spanCount) {
        if (mIncludeEdge) {
            outRect.left = mHorizontalSpacing * (spanCount - column) / spanCount;
            outRect.right = mHorizontalSpacing * (column + 1) / spanCount;
            if (position < spanCount) {
                outRect.top = mVerticalSpacing;
            }
            outRect.bottom = mVerticalSpacing;
        } else {
            outRect.left = mHorizontalSpacing * column / spanCount;
            outRect.right = mHorizontalSpacing * (spanCount - 1 - column) / spanCount;
            if (position >= spanCount) {
                outRect.top = mVerticalSpacing;
            }
        }
    }
}
