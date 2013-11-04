package com.example.mymapdemo;

import java.io.File;

import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.RennClient.LoginListener;
import com.renn.rennsdk.RennExecutor.CallBack;
import com.renn.rennsdk.RennResponse;
import com.renn.rennsdk.exception.RennException;
import com.renn.rennsdk.param.PutStatusParam;
import com.renn.rennsdk.param.UploadPhotoParam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultAndPublish extends Activity {
	public class AgainClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(ResultAndPublish.this,MainActivity.class);
			ResultAndPublish.this.startActivity(intent);

		}

	}

	public class PublishClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (rennclient.isLogin() == false) {
				rennclient.setLoginListener(new LoginListener() {

					@Override
					public void onLoginCanceled() {
						// TODO Auto-generated method stub
						Toast.makeText(ResultAndPublish.this, "登录失败了哦~",
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onLoginSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(ResultAndPublish.this, "恭喜您！登录成功！",
								Toast.LENGTH_SHORT).show();

					}
				});
				rennclient.login(ResultAndPublish.this);
			}
			PutStatusParam putstatusparam = new PutStatusParam();
			putstatusparam.setContent(tv.getText().toString());
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(ResultAndPublish.this);
				mProgressDialog.setCancelable(true);
				mProgressDialog.setMessage("别着急，我在很努力的发送呢！O(∩_∩)O");
				mProgressDialog.show();
			}
			try {
				rennclient.getRennService().sendAsynRequest(putstatusparam,
						new CallBack() {

							@Override
							public void onFailed(String arg0, String arg1) {
								// TODO Auto-generated method stub
								Toast.makeText(ResultAndPublish.this,
										"很遗憾，状态发布失败了哦~", Toast.LENGTH_SHORT)
										.show();
								if (mProgressDialog != null) {
									mProgressDialog.dismiss();
									mProgressDialog = null;
								}

							}

							@Override
							public void onSuccess(RennResponse arg0) {
								Toast.makeText(ResultAndPublish.this,
										"恭喜你，状态发布成功了！！！", Toast.LENGTH_SHORT)
										.show();
								if (mProgressDialog != null) {
									mProgressDialog.dismiss();
									mProgressDialog = null;
								}

							}
						});
			} catch (RennException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(file.exists()){
			UploadPhotoParam uploadphotoparam=new UploadPhotoParam();
			uploadphotoparam.setFile(file);
			uploadphotoparam.setDescription(tv.getText().toString());
			try {
				rennclient.getRennService().sendAsynRequest(uploadphotoparam, new CallBack(){

					@Override
					public void onFailed(String arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(ResultAndPublish.this, "照片发送失败了哦", Toast.LENGTH_SHORT).show();
						
					}

					@Override
					public void onSuccess(RennResponse arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(ResultAndPublish.this, "照片发送成功了哦", Toast.LENGTH_SHORT).show();
						file.delete();
					}});
			} catch (RennException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}}

	}

	private TextView tv;
	private Button againbt, publishbt;
	private static final String APP_ID = "243258";

	private static final String API_KEY = "d439d58345624449ad430898f63e4c88";

	private static final String SECRET_KEY = "24c0323a5e09494bbb7fea50411d3a23";
	private RennClient rennclient;
	private ProgressDialog mProgressDialog;
	private String bitmapurl;
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		rennclient = RennClient.getInstance(ResultAndPublish.this);
		rennclient.init(APP_ID, API_KEY, SECRET_KEY);
		rennclient
				.setScope("read_user_blog read_user_photo read_user_status read_user_album "
						+ "read_user_comment read_user_share publish_blog publish_share "
						+ "send_notification photo_upload status_update create_album "
						+ "publish_comment publish_feed");
		setContentView(R.layout.result);
		tv = (TextView) this.findViewById(R.id.resulttv);
		againbt = (Button) this.findViewById(R.id.againbutton);
		publishbt = (Button) this.findViewById(R.id.button2);
		rennclient.setTokenType("mac");
		String resultstr = getIntent().getExtras().getString("result");
		bitmapurl=getIntent().getExtras().getString("bitmapurl");
		file=new File(bitmapurl);
		tv.setText(resultstr);
		publishbt.setOnClickListener(new PublishClickListener());
		againbt.setOnClickListener(new AgainClickListener());

	}

}
