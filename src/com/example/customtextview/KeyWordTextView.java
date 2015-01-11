package com.example.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 一个TextView中包含一个可以点击的KeyWord(关键词),并通过点击关键词，在对应关键词位置正上方展示关键词对应的解释等逻辑
 * 
 * @author DuGuang
 * @date 2015.1.11
 * 
 */
public class KeyWordTextView extends TextView {

	protected static final String TAG = KeyWordTextView.class.getSimpleName();
	private Context mContext;
	private String mHide; // 关键词的提示内容
	private ShowHideView mShowHideView;	//show关键词解释
	private DotView mDotView;	//画一个圆点，效验mShowHideView展示的位置是否正确

	private String mStartStr, mKeyWord, mEndStr;

	int mYStartTop;// 关键词第一个字符顶部y坐标
	int mYStartBottom;// 关键词第一个字符底部y坐标
	float mXStartLeft;// 关键词第一个字符左边x坐标
	float mXStartRight;// 关键词第一个字符右边x坐标

	int mYEndTop;// 关键词最后一个字符顶部y坐标
	int mYEndBottom;// 关键词最后一个字符底部y坐标
	float mXEndLeft;// 关键词最后一个字符左边x坐标
	float mXEndRight;// 关键词最后一个字符右边x坐标

	private Layout mLayout;
	private int mStartPosition; // 关键词起始的位置
	private int mEndPosition; // 关键词结束的位置
	
	private int mStatusBarHeight; //手机状态栏高度
	private int mTitleBarHeight;	//手机标题栏的高度
	private SpannableString mSpStr;	//用于给KeyWord单独加颜色和下划线的类

	@SuppressLint("NewApi")
	public KeyWordTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
	}

	public KeyWordTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public KeyWordTextView(Context context) {
		super(context);
		this.mContext = context;
	}
	
	/**
	 * 填充段落内容
	 * 
	 * @param startStr
	 *            
	 * @param keyWord
	 *           
	 * @param endStr
	 *            
	 */
	/**
	 * @param startStr	
	 * @param keyWord	 
	 * @param endStr	
	 * @param hide	
	 * @param mDotView 
	 * @param mTitleBarHeight 
	 * @param mShowHideView 
	 */
	/**
	 * @param startStr	关键词之前显示的内容，如果没有则传null
	 * @param keyWord	关键词，可以点击的词
	 * @param endStr	关键词之后显示的内容，如果没有则传null
	 * @param hide		展示关键词的解释字段
	 * @param statusBarHeight	状态栏的高度
	 * @param titleBarHeight	标题栏的高度
	 * @param showHideView	展示关键词的View
	 * @param dotView	画一个圆点，效验mShowHideView展示的位置是否正确
	 */
	public void setAllString(String startStr, String keyWord, String endStr, String hide, int statusBarHeight, int titleBarHeight,
			ShowHideView showHideView, DotView dotView) {
		this.mStartStr = startStr;
		this.mKeyWord = keyWord;
		this.mEndStr = endStr;
		
		this.mHide = hide;
		this.mStatusBarHeight = statusBarHeight;
		this.mShowHideView = showHideView;
		this.mTitleBarHeight = titleBarHeight;
		this.mDotView = dotView;
		
		setKeyWordClick();
		initData();
		getTextLayout();
	}

	/**
	 * 填充相关数据
	 */
	private void initData() {
		// 计算关键词的起始位置
		mStartPosition = StringUtil.isNotEmpty(mStartStr) ? mStartStr.length() + 1 : 1;
		
		//计算关键词结束所在位置
		mEndPosition = StringUtil.isNotEmpty(mEndStr) ?mStartPosition + mKeyWord.length()+1 : mStartPosition + mKeyWord.length() -1 ;
	
		setText(mStartStr);
		append(mSpStr);
		append(StringUtil.isNotEmpty(mEndStr) ? mEndStr : "");
		setMovementMethod(LinkMovementMethod.getInstance());// 相当于注册关键词的点击事件（开始响应点击事件）
	}

	/**
	 * 获取对应TextView的layout属性
	 */
	private void getTextLayout() {
		// view变化监听器ViewTreeObserver介绍
		ViewTreeObserver vto = getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mLayout = getLayout();
				Rect bound = new Rect();
				int line = mLayout.getLineForOffset(mStartPosition);

				mLayout.getLineBounds(line, bound);

				mYStartTop = bound.top;
				mYStartBottom = bound.bottom;

				mXStartLeft = mLayout.getPrimaryHorizontal(mStartPosition);
				mXStartRight = mLayout.getSecondaryHorizontal(mStartPosition);

			}
		});
	}
	
	/**
	 * 设置关键词的点击事件
	 */
	private void setKeyWordClick() {
		mSpStr = new SpannableString(mKeyWord);
		mSpStr.setSpan(new ClickableSpan() {
			@Override
			public void updateDrawState(TextPaint ds) {
				super.updateDrawState(ds);
				 ds.setColor(Color.BLUE); //设置文字颜色
				ds.setUnderlineText(true); // 设置下划线
			}

			@Override
			public void onClick(View widget) {

				Log.d("", "onTextClick........被点击了");
				Log.i(TAG, "mYStartTop >>> " + mYStartTop);
				Log.i(TAG, "mYStartBottom >>> " + mYStartBottom);
				Log.i(TAG, "mXStartLeft >>> " + mXStartLeft);
				Log.i(TAG, "mXStartRight >>> " + mXStartRight);
				
				int[] location1 = new int[2];
//				getLocationInWindow(location1);
				getLocationOnScreen(location1);
				Log.i(TAG, "location1[0] >>> " + location1[0]);
				Log.i(TAG, "location1[1] >>> " + location1[1]);
				
				getKeyWordEndInfo();
//				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//				params.leftMargin = 100;
//				params.topMargin = 100;
//				mdot.setLayoutParams(params);
				
				RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				params2.leftMargin = (int)(mXStartLeft+(mXEndRight - mXStartLeft)/2)- mShowHideView.getWidth()/2;
				params2.topMargin = (int)location1[1] - mStatusBarHeight - mTitleBarHeight + mYStartTop - mShowHideView.getHeight();
				mShowHideView.setLayoutParams(params2);
				mShowHideView.setPosition(mHide);
				
				mDotView.setPosition((int)(mXStartLeft+(mXEndRight - mXStartLeft)/2), (int)location1[1] - mStatusBarHeight - mTitleBarHeight + mYStartTop);
				
			}
		}, 0, mKeyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
	}
	
	/**
	 * 获取关键词最后一个字符的X,Y的相关信息
	 */
	public void getKeyWordEndInfo(){
		mLayout = getLayout();
		Rect bound = new Rect();
		int line = mLayout.getLineForOffset(mEndPosition);

		mLayout.getLineBounds(line, bound);

		mYEndTop = bound.top;
		mYEndBottom = bound.bottom;

		mXEndLeft = mLayout.getPrimaryHorizontal(mEndPosition);
		mXEndRight = mLayout.getSecondaryHorizontal(mEndPosition);
		
		Log.i(TAG, "mYEndTop >>> " + mYEndTop);
		Log.i(TAG, "mYEndBottom >>> " + mYEndBottom);
		Log.i(TAG, "mXEndLeft >>> " + mXEndLeft);
		Log.i(TAG, "mXEndRight >>> " + mXEndRight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}

}
