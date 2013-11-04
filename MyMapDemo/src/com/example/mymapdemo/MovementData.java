package com.example.mymapdemo;

public class MovementData {
	/*
	 * 保存运动信息
	 * 目前包括距离与速度
	 * 提供各种getter和setter
	 * 如果可以，最好使用Intent将数据打包传递
	 */
	private double distance=0,speed=0;
	private long time;
	private long begintime=0;
	
	public MovementData(){
		this.begintime=System.currentTimeMillis();
	}

	public long getTime() {
		return time;
	}

	private void setTime(long time) {
		this.time = time;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed() {
		this.setTime((System.currentTimeMillis()-this.begintime)/1000);
		this.speed =this.getDistance()/(this.getTime());
	}

}
