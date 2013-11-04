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

	public class BitBtClickListener implements OnClickListener {// ����

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
	 * ��ͼ���ֵ�mainActivity���ṩ�Ĺ��ܣ� һ����λUserλ�ã����ڵ�ͼ����ʾ
	 * ����Userѡ��Begin�󣬿�ʼ����ʱ�䲢��࣬ͬʱ����ʵʱ�ٶ� ��ʼ��λ��ÿ��1s����һ���ٶȣ�ÿ��2s��λ
	 * 
	 * Ŀǰ�ⲿ�ֽ����ǳ�����һ����ƣ�Ŀǰ����һ����֪��BUG��
	 * ��ʱ���������һ�ζ�λ���ʱ�����ֿ���λ������LocationListener����Ӧ��
	 * Ŀǰ��֪�������ԭ�����Ѿ���ٶȵ�ͼAPI�ٷ��ύ��bug���棬���ȴ��ظ� �ڵ�ͼ�ϣ�����һ����ϸ��û����ӣ����緽���ͷ֮��ġ�
	 * 
	 * Ϊ�˱�֤��ͼ��������������ֹ���Ժ�Ҫ�������Ƴ����������ܳ����Զ��������ᵼ��GPSͣ�ã�������ݵĽ����жϣ��͵�ͼ������stop
	 */
	public class StopButtonListener implements OnClickListener {
		// StopButton�ļ����������stop�󽫼�¼�µ����ݴ��͵�����Activity

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

				resultstr = "����" + addstr + "���� " + movementdata.getDistance()
						+ " m\nƽ���ٶ� " + movementdata.getSpeed() + "m/s"
						+ "������:" + kcal + "ǧ��";
			} else
				resultstr = "����" + addstr + "���� " + movementdata.getDistance()
						+ " m\nƽ���ٶ� " + movementdata.getSpeed() + "m/s";
			bundle.putString("result", resultstr);
			bundle.putString("bitmapurl", urlstr);
			intent.putExtras(bundle);
			MainActivity.this.startActivity(intent);
		}

	}

	public class BtListener implements OnClickListener {
		// BeginButton�ļ�����

		@Override
		public void onClick(View arg0) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			graphicsoverlay.removeAll();
			isBegin = true;
			if (!mLocationClient.isStarted()) {
				// �ٴλ���LocationClientʱ������������λ
				mLocationClient.start();
				mLocationClient.requestLocation();
			}
			// newһ���µ�movementdata
			movementdata = new MovementData();
			// ��Speed�̼߳���Handler
			handler.post(speed);

		}

	}

	protected String key = "52429ae754349e5ec3debcb7d2192f5b";// �ٶȵ�ͼAPI�����KEY�����Բ��ܸģ���������

	/*
	 * ���������࣬�ֱ��Ƕ�λ�ͻ��ˡ���λ����������λ����
	 */
	public LocationClient mLocationClient;
	private BDLocationListener mLocationListener;
	private LocationClientOption option = new LocationClientOption();
	/*
	 * �����ĸ��࣬�ֱ��ǵ�ͼ������ͼView���ҵ�λ��ͼ�㡢��λ����
	 */
	private BMapManager mBMapMan;
	private MapView mMapView;
	private MyLocationOverlay mLocationOverlay;
	private LocationData locData = new LocationData();

	private boolean isFirst = true, isBegin = false;// ����Ƿ��ǵ�һ�ζ�λ���붨λ�����Ƿ���
	private String urlstr = "/mnt/sdcard/test.png", weightstr = null,
			addstr = null;
	private double kcal1, kcal2, kcal;

	public MovementData movementdata;// �洢�˶����ݣ��Ӵ�����Ի���ٶȣ��������ݡ�

	private TextView tv, tvr;
	private Button bt, btstop, bitbt, rebt;
	private Handler handler = new Handler();
	private Speed speed = new Speed();// �����ٶȵ��߳�
	private GeoPoint[] geopoints = new GeoPoint[2];
	private GraphicsOverlay graphicsoverlay;// ·��ͼ��
	private SharedPreferences sp;// ���ڻ�ȡ�û���Ϣ

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
		 * ���ö�λ���Ϊ1000ms���������3��POI��Ϣ�㣨��ʱû���õ���
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

		// ������λ
		mLocationClient.start();
		mLocationClient.requestLocation();
		tv.setText("NULL");
	}

	public class MyLocationListener implements BDLocationListener {
		/*
		 * ��λ��������ÿ��һ��ʱ�䣬�����˼�����
		 */

		@Override
		public void onReceiveLocation(BDLocation location) {
			// �ص����������ص�ǰlocatin������λ����Ϣ
			GeoPoint old = null;
			String city=location.getCity();
			if(city.endsWith("��"))
				city=city.substring(0, city.indexOf('��'));
			Weather weather = new Weather(city, MainActivity.this);
			tv.setText(weather.getWeather());
			tvr.setText("Radius:" + location.getRadius());
			addstr = location.getAddrStr();
			if (location.getRadius() >= 70) {
				Toast.makeText(MainActivity.this,
						"��ǰ��λ����:" + location.getRadius() + "m�����ݿ��ܲ�׼",
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
		// ����ǰ�����ζ�λ���������һ��ֱ��
		if (old == null)// ���Ϊ�״ζ�λ�����ؿ�
			return null;
		geopoints[0] = old;
		geopoints[1] = currentPoint;
		Geometry mGeometry = new Geometry();// �Զ��弸��ͼ��
		mGeometry.setPolyLine(geopoints);
		Symbol mSymbol = new Symbol();// ͼ����ʽ
		Symbol.Color mColor = mSymbol.new Color();// ����ͼ����ɫ
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
		// �����ٶȵ��̣߳�Android�ڶ��߳��ǲ���ȫ�ģ�������Handler�е����߳�

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

	void getUser() {// ���ڻ�ȡ�û�������
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
				Toast.makeText(MainActivity.this, "�������Ϊ:" + weightstr + "kg",
						Toast.LENGTH_SHORT).show();
			} else {
				weightstr = null;
				mEditor.remove("userweight");
				mEditor.commit();
			}

		}

	}

}
