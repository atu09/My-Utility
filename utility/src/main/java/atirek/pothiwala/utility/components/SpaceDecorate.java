package atirek.pothiwala.utility.components;

import android.content.Context;
import android.graphics.Rect;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;

class SpaceDecorate extends RecyclerView.ItemDecoration {

    private final Context context;
    private int spaceVertical = 0, spaceHorizontal = 0, marginHorizontal = 0, marginVertical = 0;

    public SpaceDecorate(Context context) {
        this.context = context;
    }

    public void setHorizontalSpace(@DimenRes int spaceHorizontal) {
        this.spaceHorizontal = context.getResources().getDimensionPixelSize(spaceHorizontal);
    }

    public void setVerticalSpace(@DimenRes int spaceVertical) {
        this.spaceVertical = context.getResources().getDimensionPixelSize(spaceVertical);
    }

    public void setHorizontalMargin(@DimenRes int marginHorizontal) {
        this.marginHorizontal = context.getResources().getDimensionPixelSize(marginHorizontal);
    }

    public void setVerticalMargin(@DimenRes int marginVertical) {
        this.marginVertical = context.getResources().getDimensionPixelSize(marginVertical);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter() == null) {
            return;
        }
        int position = parent.getChildAdapterPosition(view);
        int total = parent.getAdapter().getItemCount();

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            int column = position % spanCount;
            int row = Math.abs(position / spanCount);

            setGridItemOffsets(outRect, position, column, row, spanCount, total);
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            int column = lp.getSpanIndex();
            int row = Math.abs(position / spanCount);

            setGridItemOffsets(outRect, position, column, row, spanCount, total);
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) parent.getLayoutManager());

            setLinearItemOffsets(outRect, position, total, layoutManager.getOrientation());
        }
    }

    private void setGridItemOffsets(Rect outRect, int position, int column, int row, int spanCount, int total) {
        int lastRow = Math.abs(position / total);

        if (column == 0) {
            outRect.left = marginHorizontal;
            outRect.right = spaceHorizontal / 2;
        } else if (column == spanCount - 1) {
            outRect.left = spaceHorizontal / 2;
            outRect.right = marginHorizontal;
        } else {
            outRect.left = spaceHorizontal / 2;
            outRect.right = spaceHorizontal / 2;
        }

        if (row == 0) {
            outRect.top = marginVertical;
            outRect.bottom = spaceVertical / 2;
        } else if (row == lastRow) {
            outRect.top = spaceVertical / 2;
            outRect.bottom = marginVertical;
        } else {
            outRect.top = spaceVertical / 2;
            outRect.bottom = spaceVertical / 2;
        }

    }

    private void setLinearItemOffsets(Rect outRect, int position, int total, int orientation) {
        int lastPosition = total - 1;

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (position == 0) {
                outRect.left = marginHorizontal;
            } else {
                outRect.left = 0;
            }
            if (position == lastPosition) {
                outRect.right = marginHorizontal;
            } else {
                outRect.right = spaceHorizontal;
            }
            outRect.top = marginVertical;
            outRect.bottom = marginVertical;
        }

        if (orientation == LinearLayoutManager.VERTICAL) {
            if (position == 0) {
                outRect.top = marginVertical;
            } else {
                outRect.top = spaceVertical;
            }
            if (position == lastPosition) {
                outRect.bottom = marginVertical;
            } else {
                outRect.bottom = 0;
            }
            outRect.left = marginHorizontal;
            outRect.right = marginHorizontal;

        }
    }
}


