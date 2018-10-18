package com.yihuan.sharecalendar.ui.view.other;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ronny on 2018/1/23.
 */

public class GridDividerItem extends RecyclerView.ItemDecoration {
    int column;
    int space;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        int pos = parent.getChildLayoutPosition(view);
        int total = parent.getChildCount();
        if (isFirstRow(pos)) {
            outRect.top = 0;
        }
        if (isLastRow(pos, total)) {
            outRect.bottom = 5;
        }
        if (column != Integer.MAX_VALUE) {
            float avg = (column - 1) * space * 1.0f / column;
            outRect.left = (int) (pos % column * (space - avg));
            outRect.right = (int) (avg - (pos % column * (space - avg)));
        }
    }

    boolean isFirstRow(int pos) {
        return pos < column;
    }

    boolean isLastRow(int pos, int total) {
        return total - pos <= column;
    }

    boolean isFirstColumn(int pos) {
        return pos % column == 0;
    }

    boolean isSecondColumn(int pos) {
        return isFirstColumn(pos - 1);
    }

    boolean isEndColumn(int pos) {
        return isFirstColumn(pos + 1);
    }

    boolean isNearEndColumn(int pos) {
        return isEndColumn(pos + 1);
    }

    public GridDividerItem(int space, int spanCount) {
        this.space = space;
        this.column = spanCount;
    }
}
