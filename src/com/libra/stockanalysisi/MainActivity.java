package com.libra.stockanalysisi;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import cn.aigestudio.datepicker.interfaces.OnDateSelected;
import cn.aigestudio.datepicker.views.DatePicker;

import com.libra.stockanalysisi.bean.Stock;
import com.libra.stockanalysisi.engine.IContinousStateStocksCallBack;
import com.libra.stockanalysisi.engine.IUpdateProgress;
import com.libra.stockanalysisi.engine.impl.AppBussinessFacdeService;
import com.libra.stockanalysisi.engine.impl.StockBussisceFacde;
import com.libra.stockanalysisi.view.widget.ScaleWindowView;

public class MainActivity extends ActionBarActivity implements 
		IUpdateProgress, OnItemClickListener, OnEditorActionListener, OnClickListener {

	private EditText m_FallingET,m_RiseET;


	private ListView m_LV;

	private StockBussisceFacde m_Facde;

	private ProgressDialog m_UpdateDialog,m_CaculateProgress;
	
	private ScaleWindowView m_WindowView;

	private MyAdapter mAdapter;
	
	private final int FALLING_STATE = 1;
	
	private final int RISE_STATE = 0;
	
	private int m_CurState = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AppBussinessFacdeService appService = new AppBussinessFacdeService(this);
		m_Facde = (StockBussisceFacde) appService.getFacdeService(AppBussinessFacdeService.STOCK_FACDE_SERVICE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	m_FallingET = (EditText) findViewById(R.id.et_continusFallingDays);
	m_FallingET.setOnEditorActionListener(this);
	m_FallingET.setOnClickListener(this);
	m_RiseET  = (EditText) findViewById(R.id.et_continusRiseDays);
	m_RiseET.setOnEditorActionListener(this);
		m_LV = (ListView) findViewById(R.id.list);
		m_LV.setOnItemClickListener(this);
		mAdapter = new MyAdapter();
		m_LV.setAdapter(mAdapter);
		initCaculateProgress();
	}

	private void initCaculateProgress() {
		// TODO Auto-generated method stub
		m_CaculateProgress = new ProgressDialog(this);
		m_CaculateProgress.setCancelable(false);
		m_CaculateProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			m_UpdateDialog = new ProgressDialog(this);
			m_UpdateDialog.setCancelable(false);
			m_UpdateDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			m_UpdateDialog.show();
			m_Facde.updateData(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void update(int progress) {
		// TODO Auto-generated method stub
		m_UpdateDialog.setMessage("已更新：" + progress + "%");
		m_UpdateDialog.setProgress(progress);
		if (progress == 100) {
			m_UpdateDialog.dismiss();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	class MyAdapter extends BaseAdapter {

		private Stock[] mData;
		
		DecimalFormat mDF = new DecimalFormat("#0.00");

		public MyAdapter() {

		}

		public void setData(Stock[] pData) {
			mData = pData;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mData == null)
				return 0;
			return mData.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Stock baseStock = mData[position];
			if (convertView == null) {
				convertView = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.detail_listview_content, null);
			}
			if (convertView.getTag() == null) {
				ViewHolder holder = new ViewHolder();
				holder.tx_Gid = (TextView) convertView.findViewById(R.id.tv_Gid);
				holder.tx_Name = (TextView) convertView.findViewById(R.id.tv_StockName);
				holder.tx_NowPri = (TextView) convertView.findViewById(R.id.tv_NowPri);
				holder.tv_Percent = (TextView) convertView.findViewById(R.id.tv_Percent);
				convertView.setTag(holder);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tx_Gid.setText(baseStock.getGid());
			holder.tx_Name.setText(baseStock.getName());
			holder.tx_NowPri.setText(baseStock.getNowPri()+"");
			if(m_CurState == FALLING_STATE){				
				if(baseStock.getNowPri() == 0){
					holder.tv_Percent.setText("停牌");
					holder.tx_NowPri.setText(mDF.format(baseStock.getYestodEndPri())+"");
					holder.tv_Percent.setBackgroundColor(Color.GRAY);
				} else{					
					holder.tv_Percent.setBackgroundColor(Color.parseColor("#FF2E8B57"));
					holder.tv_Percent.setText(mDF.format((baseStock.getNowPri()-baseStock.getYestodEndPri())/baseStock.getYestodEndPri()*100)+"%");
				}
			} else if(m_CurState == RISE_STATE){
				holder.tv_Percent.setBackgroundColor(Color.RED);
				holder.tv_Percent.setText(mDF.format((baseStock.getNowPri()-baseStock.getYestodEndPri())/baseStock.getYestodEndPri()*100)+"%");
			}
			return convertView;
		}

		class ViewHolder {
			TextView tx_Gid,tx_Name,tx_NowPri,tv_Percent;
		}
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		m_UpdateDialog.dismiss();
		Toast.makeText(this, "已更新至最新数据", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailure(Throwable pThrowable) {
		// TODO Auto-generated method stub
		m_UpdateDialog.dismiss();
		Toast.makeText(this, "更新数据出错", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		int id = v.getId();
		String days =v.getText().toString();
		if(id == R.id.et_continusFallingDays){
			m_Facde.continuousFalling(Integer.parseInt(days),
					new IContinousStateStocksCallBack() {

						@Override
						public void continusStatesStocks(Stock[] result) {
							// TODO Auto-generated method stub
							m_CurState = FALLING_STATE;
							mAdapter.setData(result);
							Toast.makeText(MainActivity.this,
									"一共" + result.length + "只股票下跌",
									Toast.LENGTH_SHORT).show();
							m_CaculateProgress.dismiss();
						}

						@Override
						public void onFailure(Throwable pThrowable) {
							// TODO Auto-generated method stub
							Toast.makeText(MainActivity.this, "网络错误",
									Toast.LENGTH_SHORT).show();
							m_CaculateProgress.dismiss();
						}
					});
		} else if(id == R.id.et_continusRiseDays){
			m_Facde.continuousRise(Integer.parseInt(days),
					new IContinousStateStocksCallBack() {

						@Override
						public void continusStatesStocks(Stock[] result) {
							// TODO Auto-generated method stub
							m_CurState = RISE_STATE;
							mAdapter.setData(result);
							Toast.makeText(MainActivity.this,
									"一共" + result.length + "只股票上涨",
									Toast.LENGTH_SHORT).show();
							m_CaculateProgress.dismiss();
						}

						@Override
						public void onFailure(Throwable pThrowable) {
							// TODO Auto-generated method stub
							Toast.makeText(MainActivity.this, "网络错误",
									Toast.LENGTH_SHORT).show();
							m_CaculateProgress.dismiss();
						}
					});
		}
		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
	    hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		m_CaculateProgress.show();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		DatePicker picker = new DatePicker(this);
		Date vailidBeginDay = new Date(115, 5, 12);
		Date vailidEndingDay = new Date();
		picker.setVailidDays(vailidBeginDay,vailidEndingDay,Color.GREEN);
		final ScaleWindowView view = new ScaleWindowView(this, picker);
		picker.setOnDateSelected(new OnDateSelected() {
			
			@Override
			public void selected(List<String> date) {
				// TODO Auto-generated method stub
				view.dismiss();
			}
		});
		view.show();
	}
}
