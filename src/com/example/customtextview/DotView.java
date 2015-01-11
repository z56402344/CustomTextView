package com.example.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 画一个点，用来效验ShowHideView显示的位置是否正确
 * @author DuGuang
 *
 */
public class DotView extends View{

	
	private static final String TAG = DotView.class.getSimpleName();
	private Context mContext;
	
	private float mX;
	private float mY;
	@SuppressLint("NewApi")
	public DotView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
	}

	public DotView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public DotView(Context context) {
		super(context);
		this.mContext = context;
	}

	public void setPosition(int x, int y){
		mX = x;
		mY = y;
		invalidate();
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint  = new Paint();
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(20);
		paint.setColor(mContext.getResources().getColor(R.color.blue));
		
		Log.i(TAG, "mX >>> "+mX);
		Log.i(TAG, "mY >>> "+mY);
		canvas.drawPoint(mX, mY, paint);
	}
	
}
