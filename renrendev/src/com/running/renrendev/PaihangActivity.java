package com.running.renrendev;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.renn.rennsdk.RennClient;

public class PaihangActivity extends Activity {
	private RennClient rennClient;
	private ListView lv_first, lv_second, lv_third;
	private LinearLayout ll_kaipao, ll_yuepaoyou, ll_paihang, ll_wo;
	private ImageView iv_first,iv_second,iv_third;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paihang);
		initView();
	}

	private void initView() {
		rennClient = RennClient.getInstance(this);

		lv_first = (ListView) findViewById(R.id.lv_first);
		lv_second = (ListView) findViewById(R.id.lv_second);
		lv_third = (ListView) findViewById(R.id.lv_third);
		lv_first.setAdapter(new FirstAdapter());
		lv_second.setAdapter(new SecondAdapter());
		lv_third.setAdapter(new ThirdAdapter());
		ll_kaipao = (LinearLayout) findViewById(R.id.ll_kaipao);
		ll_yuepaoyou = (LinearLayout) findViewById(R.id.ll_yuepaoyou);
		ll_paihang = (LinearLayout) findViewById(R.id.ll_paihang);
		ll_wo = (LinearLayout) findViewById(R.id.ll_wo);
		iv_first=(ImageView)findViewById(R.id.iv_first);
		iv_second=(ImageView)findViewById(R.id.iv_second);
		iv_third=(ImageView)findViewById(R.id.iv_third);
		lv_first.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

		lv_second.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
		lv_third.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private class FirstAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			ViewHolder holderbz;
			// ������ʷ�����View����
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.paihang_item, null);
				holderbz = new ViewHolder();
				holderbz.tv_number = (TextView) view
						.findViewById(R.id.tv_number);
				holderbz.iv_touxiang = (ImageView) view
						.findViewById(R.id.iv_touxiang);
				holderbz.tv_name = (TextView) view.findViewById(R.id.tv_name);
				holderbz.tv_diatance = (TextView) view
						.findViewById(R.id.tv_distanceph);
				view.setTag(holderbz);
			} else {// ΪView��һ����ǣ��Ա㸴��
				view = convertView;
				holderbz = (ViewHolder) view.getTag();
			}
			// �����ݿ��л�ȡ�����ݣ�����ÿһ��ImageView��TextView

			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;// ÿһ�еĶ���
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;// �������ݿ��д洢�ļ�¼����,ÿҳ��ʾ5��
		}
	}

	private class SecondAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			ViewHolder holderzf;
			// ������ʷ�����View����

			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.paihang_item, null);
				holderzf = new ViewHolder();
				holderzf.tv_number = (TextView) view
						.findViewById(R.id.tv_number);
				holderzf.iv_touxiang = (ImageView) view
						.findViewById(R.id.iv_touxiang);
				holderzf.tv_name = (TextView) view.findViewById(R.id.tv_name);
				holderzf.tv_diatance = (TextView) view
						.findViewById(R.id.tv_distanceph);
				view.setTag(holderzf);
			} else {// ΪView��һ����ǣ��Ա㸴��
				view = convertView;
				holderzf = (ViewHolder) view.getTag();
			}
			// �����ݿ��л�ȡ�����ݣ�����ÿһ��ImageView��TextView

			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;// ÿһ�еĶ���
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;// �������ݿ��д洢�ļ�¼���ã�ÿҳ��ʾ5��
		}
	}

	private class ThirdAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			ViewHolder holderzf;
			// ������ʷ�����View����

			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.paihang_item, null);
				holderzf = new ViewHolder();
				holderzf.tv_number = (TextView) view
						.findViewById(R.id.tv_number);
				holderzf.iv_touxiang = (ImageView) view
						.findViewById(R.id.iv_touxiang);
				holderzf.tv_name = (TextView) view.findViewById(R.id.tv_name);
				holderzf.tv_diatance = (TextView) view
						.findViewById(R.id.tv_distanceph);
				view.setTag(holderzf);
			} else {// ΪView��һ����ǣ��Ա㸴��
				view = convertView;
				holderzf = (ViewHolder) view.getTag();
			}
			// �����ݿ��л�ȡ�����ݣ�����ÿһ��ImageView��TextView

			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;// ÿһ�еĶ���
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;// �������ݿ��д洢�ļ�¼����,ÿҳ��ʾ5��
		}

	}

	// View��Ӧ��View����ֻ���ڶ��ڴ��д���һ�ݣ����е�Item�����ø�View
	public static class ViewHolder {
		TextView tv_number;
		ImageView iv_touxiang;
		TextView tv_name;
		TextView tv_diatance;
	}

}
