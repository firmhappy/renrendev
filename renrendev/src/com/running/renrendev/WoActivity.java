package com.running.renrendev;

import com.renn.rennsdk.RennClient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WoActivity extends Activity{
	private RennClient rennClient;
	
	private ImageView iv_touxiang,iv_sex;
	private TextView tv_name,tv_level,tv_gongli,tv_qianka,tv_chengjiu;
	private ProgressBar pb_level;
	private LinearLayout ll_kaipao,ll_yuepaoyou,ll_paihang,ll_wo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wo);
		initView();
	}
	private void initView(){
		rennClient.getInstance(this);
		iv_touxiang=(ImageView)findViewById(R.id.iv_touxiang);
		iv_sex=(ImageView)findViewById(R.id.iv_sex);
		tv_name=(TextView)findViewById(R.id.tv_name);
		tv_level=(TextView)findViewById(R.id.tv_level);
		tv_gongli=(TextView)findViewById(R.id.tv_gongli);
		tv_qianka=(TextView)findViewById(R.id.tv_qianka);
		tv_chengjiu=(TextView)findViewById(R.id.tv_chengjiu);
		pb_level=(ProgressBar)findViewById(R.id.pb_level);
		ll_kaipao=(LinearLayout)findViewById(R.id.ll_kaipao);
		ll_yuepaoyou=(LinearLayout)findViewById(R.id.ll_yuepaoyou);
		ll_paihang=(LinearLayout)findViewById(R.id.ll_paihang);
		ll_wo=(LinearLayout)findViewById(R.id.ll_wo);
	}

}
