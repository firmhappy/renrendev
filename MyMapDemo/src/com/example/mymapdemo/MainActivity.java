package com.example.mymapdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public class ReListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this, UserLogin.class);
			MainActivity.this.startActivityForResult(intent, 1);

		}

	}

	public class BitBtClickListener implements OnClickListener {// 截屏

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mMapView.getCurrentMap();
			mMapView.regMapViewListener(mBMapMan, new MKMapViewListener() {

				@Override
				public void onClickMapPoi(MapPoi arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onGetCurrentMap(Bitmap bitmap) {
					// TODO Auto-generated method stub
					File file = new File(urlstr);
					FileOutputStream out;
					try {
						out = new FileOutputStream(file);
						if (bitmap
								.compress(Bitmap.CompressFormat.JPEG, 70, out)) {
							out.flush();
							out.close();
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void onMapAnimationFinish() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onMapLoadFinish() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onMapMoveFinish() {
					// TODO Auto-generated method stub

				}
			});

		}

	}

	/*
	 * 地图部分的mainActivity，提供的功能： 一、定位User位置，并在地图上显示
	 * 二、User选择Begin后，开始计算时间并测距，同时给出实时速度 开始定位后，每隔1s计算一次速度，每隔2s定位
	 * 
	 * 目前这部分仅仅是初步的一个设计，目前存在一个已知的BUG：
	 * 有时候在软件第一次定位完成时，出现卡定位的现象，LocationListener不响应。
	 * 目前不知道问题的原因，我已经向百度地图API官方提交了bug报告，正等待回复 在地图上，还有一部分细节没有添加，比如方向箭头之类的。
	 * 
	 * 为了保证地图管理器不意外终止，以后要将软件设计常亮，决不能出现自动锁屏，会导致GPS停用，造成数据的接收中断，和地图管理器stop
	 */
	public class StopButtonListener implements OnClickListener {
		// StopButton的监听器。点击stop后将记录下的数据传送到人人Activity

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			getUser();
			isBegin = false;
			handler.removeCallbacks(speed);
			Double weight;
			String resultstr;
			Intent intent = new Intent(MainActivity.this,
					ResultAndPublish.class);
			Bundle bundle = new Bundle();
			if (weightstr != null) {
				weight = Double.parseDouble(weightstr);
				kcal1 = weight * movementdata.getTime() * 30
						/ (400 / movementdata.getSpeed() / 60) / 3600;
				kcal = kcal1;

				resultstr = "我在" + addstr + "跑了 " + movementdata.getDistance()
						+ " m\n平均速度 " + movementdata.getSpeed() + "m/s"
						+ "消耗了:" + kcal + "千卡";
			} else
				resultstr = "我在" + addstr + "跑了 " + movementdata.getDistance()
						+ " m\n平均速度 " + movementdata.getSpeed() + "m/s";
			bundle.putString("result", resultstr);
			bundle.putString("bitmapurl", urlstr);
			intent.putExtras(bundle);
			MainActivity.this.startActivity(intent);
		}

	}

	public class BtListener implements OnClickListener {
		// BeginButton的监听器

		@Override
		public void onClick(View arg0) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			graphicsoverlay.removeAll();
			isBegin = true;
			if (!mLocationClient.isStarted()) {
				// 再次唤醒LocationClient时，重新启动定位
				mLocationClient.start();
				mLocationClient.requestLocation();
			}
			// new一个新的movementdata
			movementdata = new MovementData();
			// 将Speed线程加入Handler
			handler.post(speed);

		}

	}

	protected String key = "52429ae754349e5ec3debcb7d2192f5b";// 百度地图API申请的KEY，绝对不能改！！！！！

	/*
	 * 以下三个类，分别是定位客户端、定位监听器、定位设置
	 */
	public LocationClient mLocationClient;
	private BDLocationListener mLocationListener;
	private LocationClientOption option = new LocationClientOption();
	/*
	 * 以下四个类，分别是地图管理、地图View、我的位置图层、定位数据
	 */
	private BMapManager mBMapMan;
	private MapView mMapView;
	private MyLocationOverlay mLocationOverlay;
	private LocationData locData = new LocationData();

	private boolean isFirst = true, isBegin = false;// 标记是否是第一次定位，与定位功能是否开启
	private String urlstr = "/mnt/sdcard/test.png", weightstr = null,
			addstr = null;
	private double kcal1, kcal2, kcal;

	public MovementData movementdata;// 存储运动数据，从此类可以获得速度，距离数据。

	private TextView tv, tvr;
	private Button bt, btstop, bitbt, rebt;
	private Handler handler = new Handler();
	private Speed speed = new Speed();// 计算速度的线程
	private GeoPoint[] geopoints = new GeoPoint[2];
	private GraphicsOverlay graphicsoverlay;// 路线图层
	private SharedPreferences sp;// 用于获取用户信息

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(MainActivity.this);
		mBMapMan.init(key, null);
		setContentView(R.layout.activity_main);
		mMapView = (MapView) this.findViewById(R.id.bmapsView);
		graphicsoverlay = new GraphicsOverlay(mMapView);
		mMapView.getOverlays().add(graphicsoverlay);
		mLocationOverlay = new MyLocationOverlay(mMapView);
		mLocationClient = new LocationClient(getApplicationContext());

		tv = (TextView) this.findViewById(R.id.dstv);
		tvr = (TextView) this.findViewById(R.id.rtv);
		bt = (Button) this.findViewById(R.id.beginbt);
		btstop = (Button) this.findViewById(R.id.stopbt);
		bitbt = (Button) this.findViewById(R.id.bitmapbt);
		rebt = (Button) this.findViewById(R.id.rebt);

		tv.setText("Distance:" + 0 + "m\n" + "Speed:" + 0 + "m/s");
		bt.setOnClickListener(new BtListener());
		btstop.setOnClickListener(new StopButtonListener());
		bitbt.setOnClickListener(new BitBtClickListener());
		rebt.setOnClickListener(new ReListener());

		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);
		/*
		 * 设置定位间隔为1000ms，返回最多3个POI信息点（暂时没有用到）
		 */
		option.setOpenGps(true);
		option.setScanSpan(1000);
		option.setAddrType("all");
		option.disableCache(false);
		option.setPoiNumber(3);
		option.setCoorType("bd09ll");
		mLocationClient.setLocOption(option);
		mLocationOverlay.enableCompass();

		mMapView.setBuiltInZoomControls(true);
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
				(int) (116.404 * 1E6));
		mMapView.getController().setCenter(point);
		mMapView.getController().setZoom(19);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().build());

		// 开启定位
		mLocationClient.start();
		mLocationClient.requestLocation();
		tv.setText("NULL");
	}

	public class MyLocationListener implements BDLocationListener {
		/*
		 * 定位监听器，每隔一定时间，触发此监听器
		 */

		@Override
		public void onReceiveLocation(BDLocation location) {
			// 回调函数，返回当前locatin，包含位置信息
			GeoPoint old = null;
			String city=location.getCity();
			if(city.endsWith("市"))
				city=city.substring(0, city.indexOf('市'));
			Weather weather = new Weather(city, MainActivity.this);
			tv.setText(weather.getWeather());
			tvr.setText("Radius:" + location.getRadius());
			addstr = location.getAddrStr();
			if (location.getRadius() >= 70) {
				Toast.makeText(MainActivity.this,
						"当前定位精度:" + location.getRadius() + "m，数据可能不准",
						Toast.LENGTH_SHORT).show();
				mLocationClient.requestLocation();
				// bt.setClickable(false);
			}
			bt.setClickable(true);
			GeoPoint currentPoint = new GeoPoint(
					(int) (location.getLatitude() * 1e6),
					(int) (location.getLongitude() * 1e6));
			if (!isFirst && isBegin) {
				old = new GeoPoint((int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6));
				movementdata.setDistance(movementdata.getDistance()
						+ DistanceUtil.getDistance(old, currentPoint));
				graphicsoverlay.setData(drawline(old, currentPoint));
			}
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			locData.direction = location.getDerect();
			mLocationOverlay.setData(locData);
			if (isFirst) {
				mMapView.getOverlays().add(mLocationOverlay);
				mMapView.refresh();
				mMapView.getController().animateTo(currentPoint);
				isFirst = false;
			} else {
				mMapView.refresh();
				mMapView.getController().animateTo(currentPoint);
			}

		}

		@Override
		public void onReceivePoi(BDLocation location) {

		}

	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
			mLocationClient.stop();
		}
		super.onDestroy();
	}

	public Graphic drawline(GeoPoint old, GeoPoint currentPoint) {
		// 根据前后两次定位结果，画出一条直线
		if (old == null)// 如果为首次定位，返回空
			return null;
		geopoints[0] = old;
		geopoints[1] = currentPoint;
		Geometry mGeometry = new Geometry();// 自定义几何图形
		mGeometry.setPolyLine(geopoints);
		Symbol mSymbol = new Symbol();// 图形样式
		Symbol.Color mColor = mSymbol.new Color();// 设置图形颜色
		mColor.red = 255;
		mColor.blue = 0;
		mColor.green = 0;
		mColor.alpha = 255;
		mSymbol.setLineSymbol(mColor, 5);
		return new Graphic(mGeometry, mSymbol);
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
			mLocationClient.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
			mLocationClient.start();
		}
		movementdata = null;
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class Speed implements Runnable {
		// 计算速度的线程，Android内对线程是不安全的，必须在Handler中调用线程

		@Override
		public void run() {
			movementdata.setSpeed();
			tv.setText("Distance:" + movementdata.getDistance() + "m\n"
					+ "Speed:" + movementdata.getSpeed() + "m/s");
			handler.postDelayed(speed, 1000);
		}

	}

	class Move implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			movementdata = new MovementData();

		}
	}

	void getUser() {// 用于获取用户的体重
		sp = MainActivity.this.getSharedPreferences("user", 0);
		weightstr = sp.getString("userweight", "none");
		if (weightstr.equals("none"))
			weightstr = null;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 1 && resultCode == 1) {
			if (sp == null) {
				sp = MainActivity.this.getSharedPreferences("user", 0);
				weightstr = sp.getString("userweight", "none");
				weightstr = null;
			}
			SharedPreferences.Editor mEditor = sp.edit();
			weightstr = data.getExtras().getString("weight");
			if (!weightstr.equals("")) {
				mEditor.putString("userweight",
						data.getExtras().getString("weight"));
				mEditor.commit();
				weightstr = data.getExtras().getString("weight");
				Toast.makeText(MainActivity.this, "你的体重为:" + weightstr + "kg",
						Toast.LENGTH_SHORT).show();
			} else {
				weightstr = null;
				mEditor.remove("userweight");
				mEditor.commit();
			}

		}

	}

}
