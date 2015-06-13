package com.libra.stockanalysisi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.engine.IContinousFallingStocksCallBack;
import com.libra.stockanalysisi.engine.impl.BussisceFacde;

public class MainActivity extends ActionBarActivity implements OnClickListener, IUpdateProgress, OnItemClickListener {
	
	private EditText m_ET;
	
	private View m_Submit;
	
	private ListView m_LV;
	
	private BussisceFacde facde;

	private ProgressDialog m_UpdateDialog;
	
	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		facde = new BussisceFacde(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		m_ET = (EditText) findViewById(R.id.et_continusFallingDays);
		m_Submit = findViewById(R.id.btn_submit);
		m_Submit.setOnClickListener(this);
		m_LV = (ListView) findViewById(R.id.list);
		m_LV.setOnItemClickListener(this);
		mAdapter = new MyAdapter();
		m_LV.setAdapter(mAdapter);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String days = m_ET.getText().toString();
		facde.continuousFalling(Integer.parseInt(days), new IContinousFallingStocksCallBack() {
			
			@Override
			public void continusFallingStocks(BaseStock[] result) {
				// TODO Auto-generated method stub
				mAdapter.setData(result);
				Toast.makeText(MainActivity.this, "计算完成", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void update(int progress) {
		// TODO Auto-generated method stub
		m_UpdateDialog.setMessage("已更新："+progress+"%");
		m_UpdateDialog.setProgress(progress);
		if(progress == 100){
			m_UpdateDialog.dismiss();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
	class MyAdapter extends BaseAdapter{
		
		private BaseStock[] mData;
		
		public MyAdapter(){
			
		}
		
		public void setData(BaseStock[] pData){
			mData = pData;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(mData == null) return 0;
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
			if(convertView == null){
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.detail_listview_content, null);
			}
			if(convertView.getTag() == null){
				ViewHolder holder = new ViewHolder();
				holder.tx = (TextView) convertView.findViewById(R.id.tv);
				convertView.setTag(holder);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.tx.setText(baseStock.getGid() + "("+baseStock.getName()+")");
			return convertView;
		}
		
		class ViewHolder{
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
		
	}
}
