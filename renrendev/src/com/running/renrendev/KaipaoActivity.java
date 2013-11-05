package com.running.renrendev;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.renn.rennsdk.RennClient;

public class KaipaoActivity extends Activity{
	private RennClient rennClient;
	
	private TextView tv_distance,tv_time,tv_speed;
	private SeekBar sb_distance;
	private Button bn_rest,bn_stop;
	private RelativeLayout rl_show;
	private ImageView iv_first,iv_second;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.kaipao);
        	initView();
        }
        private void initView(){
        	rennClient = RennClient.getInstance(this);
        	tv_distance=(TextView)findViewById(R.id.tv_diatance);
        	tv_time=(TextView)findViewById(R.id.tv_time);
        	tv_speed=(TextView)findViewById(R.id.tv_speed);
        	sb_distance=(SeekBar)findViewById(R.id.sb_distance);
        	bn_rest=(Button)findViewById(R.id.bn_rest);
        	bn_stop=(Button)findViewById(R.id.bn_stop);
        	rl_show=(RelativeLayout)findViewById(R.id.rl_show);
        	iv_first=(ImageView)findViewById(R.id.iv_first);
        	iv_second=(ImageView)findViewById(R.id.iv_second);
        }

}
