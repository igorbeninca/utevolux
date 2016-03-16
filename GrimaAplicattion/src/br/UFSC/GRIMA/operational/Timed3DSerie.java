package br.UFSC.GRIMA.operational;

import org.jfree.data.time.RegularTimePeriod;

public class Timed3DSerie {
	private double x;
	private double y;
	private double z;
	private RegularTimePeriod time;
	private boolean nullRegister;
////////////////////////////////////////Constructor///////////////////////////////////////////////////
	public Timed3DSerie(double x, double y, double z, RegularTimePeriod time) {
		// TODO Auto-generated constructor stub
		setX(x);
		setY(y);
		setZ(z);
		setTime(time);
	}
//////////////////////////////////////Methods/////////////////////////////////////////////////////////////
	public boolean equals(Timed3DSerie register) {
		return (x == register.getX())&&(y == register.getY())&&(z == register.getZ());
		
	}
///////////////////////////////////Getters and Setters//////////////////////////////////////////////////
	public boolean isNullRegister() {
		return nullRegister;
	}
	public void setNullRegister(boolean nullRegister) {
		this.nullRegister = nullRegister;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public RegularTimePeriod getTime() {
		return time;
	}
	public void setTime(RegularTimePeriod time) {
		this.time = time;
	}
	
}
