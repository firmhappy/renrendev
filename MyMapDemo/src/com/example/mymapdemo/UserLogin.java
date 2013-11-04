package com.example.mymapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends Activity{
	private Button bt;
	private EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userlogin);
		bt=(Button) this.findViewById(R.id.okbutton);
		et=(EditText) this.findViewById(R.id.weightet);
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=getIntent();
				Bundle bundle=new Bundle();
				bundle.putString("weight", et.getText().toString());
				intent.putExtras(bundle);
				UserLogin.this.setResult(1, intent);
				UserLogin.this.finish();
				
			}});
		
	}

}
