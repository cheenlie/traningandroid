/**
 * @author zhangqing3
 *
 */
package com.pmkebiao.dao;
import java.io.Serializable;

public class ClassInfo implements Serializable
{	
	private int id;
	private String class_name;//	课程名	必选	
	private String class_type;//	课程类别	必选	
	private int class_start_week;//	起始周	必选	
    private int timers_total;//	总次数	必选	
    private int timers_oneweek;//	每周次数	必选	
	private String[] Class_each_week;//	每次周几上
	private String[] Class_starttime;//	开始时间
	private String[] Class_finnishtime;//	下课时间
	private int class_cut;     //	假期扣除次数	可选	
	private String location;//	上课地点	可选	通过百度地图选择
	private int reminder;//	课前提醒	可选	缺省是不打勾的，即不提醒
	private String moneycost;//	课程总费用	可选	
	private String others;//	注释	可选	
	private int user_id;//用户id 
	private int child_id;// 孩子id

public int getUser_id() {
		return user_id;
	}



	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}



	public int getChild_id() {
		return child_id;
	}



	public void setChild_id(int child_id) {
		this.child_id = child_id;
	}



public ClassInfo(String class_name,String class_type,int class_start_week,int timers_total,int timers_oneweek,String[] Class_each_week,String[] Class_starttime,String[] Class_finnishtime,String location
,int reminder, String moneycost,String others,int child_id)
	{
		super();
	
		this.Class_each_week=Class_each_week;
		this.class_name=class_name;
		this.class_type=class_type;
		this.class_start_week=class_start_week;
		this.location=location;
		this.timers_oneweek=timers_oneweek;
		this.timers_total=timers_total;
		this.moneycost=moneycost;
		this.reminder=reminder;		
		this.Class_starttime=Class_starttime;
		this.others=others;
		this.Class_finnishtime=Class_finnishtime;
		this.child_id=child_id;
	}	



	public String[] getClass_starttime() {
		return Class_starttime;
	}



	public void setClass_starttime(String[] class_starttime) {
		Class_starttime = class_starttime;
	}



	public String[] getClass_finnishtime() {
		return Class_finnishtime;
	}



	public void setClass_finnishtime(String[] class_finnishtime) {
		Class_finnishtime = class_finnishtime;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getClass_name() {
		return class_name;
	}



	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}



	public String getClass_type() {
		return class_type;
	}



	public void setClass_type(String class_type) {
		this.class_type = class_type;
	}



	public int getClass_start_week() {
		return class_start_week;
	}



	public void setClass_start_week(int class_start_week) {
		this.class_start_week = class_start_week;
	}



	public int getTimers_total() {
		return timers_total;
	}



	public void setTimers_total(int timers_total) {
		this.timers_total = timers_total;
	}



	public int getTimers_oneweek() {
		return timers_oneweek;
	}



	public void setTimers_oneweek(int timers_oneweek) {
		this.timers_oneweek = timers_oneweek;
	}



	public String[] getClass_each_week() {
		return Class_each_week;
	}



	public void setClass_each_week(String[] class_each_week) {
		Class_each_week = class_each_week;
	}



	public int getClass_cut() {
		return class_cut;
	}



	public void setClass_cut(int class_cut) {
		this.class_cut = class_cut;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public int getReminder() {
		return reminder;
	}



	public void setReminder(int reminder) {
		this.reminder = reminder;
	}



	public String getMoneycost() {
		return moneycost;
	}



	public void setMoneycost(String moneycost) {
		this.moneycost = moneycost;
	}



	public String getOthers() {
		return others;
	}



	public void setOthers(String others) {
		this.others = others;
	}
	
	
}


