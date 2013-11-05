package com.running.renrendev;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.RennClient.LoginListener;

public class MainActivity extends Activity implements OnClickListener{
	private RennClient rennClient;
	private static final String APP_ID = "168802";
	private static final String API_KEY = "e884884ac90c4182a426444db12915bf";
	private static final String SECRET_KEY = "094de55dc157411e8a5435c6a7c134c5";
	private Button bn_login,bn_startrun,bn_weather,bn_distance,bn_mode;
	private ImageButton bn_setting;
	private ImageView iv_touxiang;
	private LinearLayout ll_kaipao,ll_yuepaoyou,ll_paihang,ll_wo;
	private LinearLayout ll_login,ll_touxiang;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rennClient = RennClient.getInstance(this);
		rennClient.init(APP_ID, API_KEY, SECRET_KEY);
		rennClient
				.setScope("read_user_blog read_user_photo read_user_status read_user_album "
						+ "read_user_comment read_user_share publish_blog publish_share "
						+ "send_notification photo_upload status_update create_album "
						+ "publish_comment publish_feed");
		rennClient.setTokenType("bearer");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
	}
	private void initView(){
		bn_login=(Button)findViewById(R.id.bn_login);
		bn_login.setOnClickListener(this);
		bn_startrun=(Button)findViewById(R.id.bn_startrun);
		bn_startrun.setOnClickListener(this);
		bn_weather=(Button)findViewById(R.id.bn_weather);
		bn_weather.setOnClickListener(this);
		bn_distance=(Button)findViewById(R.id.bn_distance);
		bn_distance.setOnClickListener(this);
		bn_mode=(Button)findViewById(R.id.bn_mode);
		bn_mode.setOnClickListener(this);
		bn_setting=(ImageButton)findViewById(R.id.bn_setting);
		bn_setting.setOnClickListener(this);
		iv_touxiang=(ImageView)findViewById(R.id.iv_touxiang);
		iv_touxiang.setOnClickListener(this);
		ll_kaipao=(LinearLayout)findViewById(R.id.ll_kaipao);
		ll_kaipao.setOnClickListener(this);
		ll_yuepaoyou=(LinearLayout)findViewById(R.id.ll_yuepaoyou);
		ll_yuepaoyou.setOnClickListener(this);
		ll_paihang=(LinearLayout)findViewById(R.id.ll_paihang);
		ll_paihang.setOnClickListener(this);
		ll_wo=(LinearLayout)findViewById(R.id.ll_wo);
		ll_wo.setOnClickListener(this);
		ll_login=(LinearLayout)findViewById(R.id.ll_login);
		ll_touxiang=(LinearLayout)findViewById(R.id.ll_touxiang);
		if(rennClient.isLogin()){
			ll_login.setVisibility(View.GONE);
			ll_touxiang.setVisibility(View.VISIBLE);
		}else {
			ll_login.setVisibility(View.VISIBLE);
			ll_touxiang.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bn_login:
			rennClient.setLoginListener(new LoginListener() {
				@Override
				public void onLoginSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "µÇÂ¼³É¹¦",
							Toast.LENGTH_SHORT).show();
					ll_login.setVisibility(View.GONE);
					ll_touxiang.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoginCanceled() {
					ll_login.setVisibility(View.VISIBLE);
					ll_touxiang.setVisibility(View.GONE);
				}
			});
			rennClient.login(this);
			break;
		}
		if (rennClient.isLogin() == false)
			return;
		switch (v.getId()) {
		case R.id.iv_touxiang:
			
			break;
		case R.id.bn_startrun:
			Intent kaipaointent=new Intent(MainActivity.this,KaipaoActivity.class);
			startActivity(kaipaointent);
			break;
		case R.id.bn_setting:
			
			break;
		case R.id.bn_weather:
			
			break;
		case R.id.bn_distance:
			
			break;
		case R.id.bn_mode:
			
			break;
		case R.id.ll_kaipao:
			
			break;
		case R.id.ll_yuepaoyou:
			
			break;
		case R.id.ll_paihang:
			Intent paihangintent=new Intent(MainActivity.this, PaihangActivity.class);
			startActivity(paihangintent);
			break;
		case R.id.ll_wo:
			Intent woinetnt=new Intent(MainActivity.this, WoActivity.class);
			startActivity(woinetnt);
			break;
		
		}
		
	}

}
