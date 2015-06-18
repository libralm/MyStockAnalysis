package com.libra.stockanalysisi.view.widget;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

public class ScaleWindowView {
	
	private View m_ScaleView;
	
	private Context m_Context;
	
	private WindowManager m_WM;

	public ScaleWindowView(Context pContext, View pScaleView) {
		super();
		// TODO Auto-generated constructor stub
		m_ScaleView = pScaleView;
		m_Context = pContext;
		initWindow();
	}

	private void initWindow() {
		// TODO Auto-generated method stub
		m_WM = (WindowManager) m_Context.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.MATCH_PARENT;
		params.dimAmount = 0.5f;
		params.alpha = 1f;
		params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
				| WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
		params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
		params.windowAnimations = android.R.style.Animation_Toast;
		m_ScaleView.setLayoutParams(params);
	}
	
	public void show(){
		if(m_ScaleView.getParent() == null){
			m_WM.addView(m_ScaleView, m_ScaleView.getLayoutParams());
		}
	}
	
	public void dismiss(){
		if(m_ScaleView.getParent() != null){
			m_WM.removeView(m_ScaleView);
		}
	}
}
