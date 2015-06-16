package com.libra.stockanalysisi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.engine.IContinousStateStocksCallBack;
import com.libra.stockanalysisi.engine.IUpdateProgress;
import com.libra.stockanalysisi.engine.impl.BussisceFacde;

public class MainActivity extends ActionBarActivity implements 
		IUpdateProgress, OnItemClickListener, OnEditorActionListener {

	private EditText m_FallingET,m_RiseET;


	private ListView m_LV;

	private BussisceFacde facde;

	private ProgressDialog m_UpdateDialog,m_CaculateProgress;

	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		facde = new BussisceFacde(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	m_FallingET = (EditText) findViewById(R.id.et_continusFallingDays);
	m_FallingET.setOnEditorActionListener(this);
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
			facde.updateData(this);
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

		private BaseStock[] mData;

		public MyAdapter() {

		}

		public void setData(BaseStock[] pData) {
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
			BaseStock baseStock = mData[position];
			if (convertView == null) {
				convertView = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.detail_listview_content, null);
			}
			if (convertView.getTag() == null) {
				ViewHolder holder = new ViewHolder();
				holder.tx = (TextView) convertView.findViewById(R.id.tv);
				convertView.setTag(holder);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tx.setText(baseStock.getGid() + "(" + baseStock.getName()
					+ ")");
			return convertView;
		}

		class ViewHolder {
			TextView tx;
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
		Toast.makeText(this, "更新数据出错", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		int id = v.getId();
		String days =v.getText().toString();
		if(id == R.id.et_continusFallingDays){
			facde.continuousFalling(Integer.parseInt(days),
					new IContinousStateStocksCallBack() {

						@Override
						public void continusFallingStocks(BaseStock[] result) {
							// TODO Auto-generated method stub
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
			facde.continuousRise(Integer.parseInt(days),
					new IContinousStateStocksCallBack() {

						@Override
						public void continusFallingStocks(BaseStock[] result) {
							// TODO Auto-generated method stub
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
}
