package com.example.customtextview;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * 一个TextView中包含一个可以点击的KeyWord(关键词),并通过点击关键词，在对应关键词位置正上方展示关键词对应的解释等逻辑
 * @author DuGuang
 *
 */
public class MainActivity extends Activity {

	private KeyWordTextView mTvKeyWord;
	private ShowHideView mShowHideView;
	private DotView mDotView;
	private int mStatusBarHeight; // 手机状态栏高度
	private int mTitleBarHeight;	//手机标题栏的高度

	private RelativeLayout mLlMain;
	private String mHide = "爱";
	private String mStartStr;	//关键词前面的字段
//	private String mStartStr = null;
	private String mKeyWord = "love"; //关键词
	private String mEndStr ;	//关键词后面的字段
//	private String mEndStr = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		mTvKeyWord = (KeyWordTextView) findViewById(R.id.mTvKeyWord);
		mLlMain = (RelativeLayout) findViewById(R.id.mLlMain);
		
		mShowHideView = new ShowHideView(this);
		mDotView = new DotView(this);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		mShowHideView.setLayoutParams(params);
		mDotView.setLayoutParams(params);
		mShowHideView.setVisibility(View.INVISIBLE);
		
		mLlMain.addView(mShowHideView);
		mLlMain.addView(mDotView);
	}

	/**
	 * 填充数据
	 */
	private void initData() {
		mStartStr = "Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! ";
		mEndStr = " Whatever is worth doing is worth doing well.";
		
		//这里开启子线程是为获取状态栏和标题栏的高度
		mTvKeyWord.post(new Runnable() {
			
			@Override
			public void run() {
				getBarHeight();
				mTvKeyWord.setAllString(mStartStr, mKeyWord, mEndStr, mHide,
						mStatusBarHeight, mTitleBarHeight, mShowHideView,  mDotView);
			}
		});
		
	

	}

	/**
	 * 获取手机状态栏和标题栏的高度
	 */
	private void getBarHeight() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		Window window = getWindow();
		// 状态栏的高度
		mStatusBarHeight = frame.top;
		// 标题栏跟状态栏的总体高度
		int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		// 标题栏的高度：用上面的值减去状态栏的高度及为标题栏高度
		mTitleBarHeight = contentViewTop - mStatusBarHeight;
		Log.i("dg", mStatusBarHeight + "..." + contentViewTop + "..."
				+ mTitleBarHeight);

	}

}
