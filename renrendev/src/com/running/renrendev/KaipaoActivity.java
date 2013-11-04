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
	private static final String APP_ID = "168802";
	private static final String API_KEY = "e884884ac90c4182a426444db12915bf";
	private static final String SECRET_KEY = "094de55dc157411e8a5435c6a7c134c5";
	
	private TextView tv_distance,tv_time,tv_speed;
	private SeekBar sb_distance;
	private Button bn_rest,bn_stop;
	private RelativeLayout rl_show;
	private ImageView iv_first,iv_second;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
        	rennClient = RennClient.getInstance(this);
    		rennClient.init(APP_ID, API_KEY, SECRET_KEY);
    		rennClient
    				.setScope("read_user_blog read_user_photo read_user_status read_user_album "
    						+ "read_user_comment read_user_share publish_blog publish_share "
    						+ "send_notification photo_upload status_update create_album "
    						+ "publish_comment publish_feed");
    		rennClient.setTokenType("bearer");
        	setContentView(R.layout.kaipao);
        	initView();
        }
        private void initView(){
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
