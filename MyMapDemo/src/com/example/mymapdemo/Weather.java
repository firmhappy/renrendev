package com.example.mymapdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class Weather{
	private String city="海口",strurl=null,id,jsonData;
	private Map<String,String> mapAllNameID;
	private Context context;
	public Weather(String city,Context context){
		context=context;
		this.city=city;
		NameIdMap nameIDmap=new NameIdMap();
		mapAllNameID=nameIDmap.getMapAllNameID();
		id=mapAllNameID.get(city);
		strurl="http://m.weather.com.cn/data/" + id + ".html";
		HttpDownloader http=new HttpDownloader();
		jsonData=http.download(strurl);
		
	}
	public String getWeather(){
		 String weather="暂无天气信息";
		 try {
			JSONObject allweatherdata=new JSONObject(jsonData);
			JSONObject weatherData=allweatherdata.getJSONObject("weatherinfo");
			weather="今日天气:\n"+weatherData.getString("weather1")+"\n"+weatherData.getString("temp1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return weather;
	}
	

}
