package com.yihuan.sharecalendar.ui.view.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/**
 * Created by Ronny on 2016/11/30.
 */

public class RvDividerItem extends RecyclerView.ItemDecoration {

	public static final int[] ATTRS = new int[] { android.R.attr.listDivider // android系统默认的listview样式的分界线
		,com.yihuan.sharecalendar.R.attr.recyclerview_divider
	};//在主题出更换
	private Drawable mDrawable;
	private int mOrientation;

	public RvDividerItem(Context context, int orientation) {
		final TypedArray a = context.obtainStyledAttributes(ATTRS);// 根据属性获取
		mDrawable = a.getDrawable(0);
		a.recycle();
		setOrientation(orientation);
	}

	private void setOrientation(int orientation) {
		if (orientation != LinearLayoutManager.HORIZONTAL
				&& orientation != LinearLayoutManager.VERTICAL) {
			throw new IllegalArgumentException("invalid orientation");
		}
		mOrientation = orientation;
	}

	// 绘制分割先，在onDrawChild前执行
	@Override
	public void onDraw(Canvas c, RecyclerView parent, State state) {
		if (mOrientation == LinearLayoutManager.VERTICAL) {
			drawVertical(c, parent);
		} else {
			drawHorizontal(c, parent);
		}
	}

	/**
	 * 绘制水平分割线
	 * 
	 * @param c
	 * @param parent
	 */
	private void drawVertical(Canvas c, RecyclerView parent) {
		final int left = parent.getPaddingLeft();// 获取左位置
		final int right = parent.getWidth() - parent.getPaddingRight();// 获取右位置
		// 接下来获取y轴坐标范围
		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();// 获取子控件的参数信息
			// params.bottomMargin是获取该控件相对下部视图的margin间距，child.getBottom是获取子控件的底部坐标
			final int top = params.bottomMargin + child.getBottom();
			final int bottom = top + mDrawable.getIntrinsicHeight();
			mDrawable.setBounds(left, top, right, bottom);
			mDrawable.draw(c);
		}
	}

	/**
	 * 绘制垂直分割线
	 * 
	 * @param c
	 * @param parent
	 */
	private void drawHorizontal(Canvas c, RecyclerView parent) {
		final int top = parent.getPaddingTop();
		final int bottom = parent.getHeight() - parent.getPaddingBottom();
		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			final int left = child.getWidth() + params.rightMargin;
			final int right = left + mDrawable.getIntrinsicHeight();
			mDrawable.setBounds(left, top, right, bottom);
			mDrawable.draw(c);
		}
	}

	// 绘制分割线，在onDrawChild后执行
	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, State state) {
		super.onDrawOver(c, parent, state);
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
			State state) {
		if(mOrientation == LinearLayoutManager.VERTICAL){
			outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
		}else{
			outRect.set(0,0,mDrawable.getIntrinsicWidth(),0);
		}
	}
}
