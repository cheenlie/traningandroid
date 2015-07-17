package com.pmkebiao.dao;
import java.io.Serializable;

import com.pmkebiao.db.DBOperation;


import android.content.Context;


public class SingleClass implements Serializable
{

    private ClassInfo ci;
    private int id;
    private int class_id;// 课程id
    private String class_name;
    private String courseid;
    private int class_no;// 第几次课
    private int week_no;// 第几周
    private int week_day;// 周几上课
    private String class_starttime;// 上课时间
    private String class_finishtime;// 下课时间
    private int class_cut;// 是否被cut
    private String location;// 上课地点
    private int reminder;//
    private String others;// 其他
    private String class_type;// 课类型   

	public int getChildid() {
		return childid;
	}

	public void setChildid(int childid) {
		this.childid = childid;
	}
    private int childid;
    public SingleClass(int id, int class_id, String class_name, int class_no,
	    int week_no, int week_day, String class_starttime,
	    String class_finishtime, int class_cut, String location,
	    int reminder, String others, String class_type,String courseid)
    {
	super();
	this.courseid=courseid;
	this.id = id;
	this.class_id = class_id;// 课程id
	this.class_name = class_name;
	this.class_no = class_no;// 第几次课
	this.week_no = week_no;// 第几周
	this.week_day = week_day;// 周几上课
	this.class_starttime = class_starttime;// 上课时间
	this.class_finishtime = class_finishtime;// 下课时间
	this.class_cut = class_cut;// 是否被cut
	this.location = location;
	this.reminder = reminder;
	this.others = others;
	this.class_type = class_type;
    }

    public ClassInfo getCi() {
		return ci;
	}

	public void setCi(ClassInfo ci) {
		this.ci = ci;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getOthers()
    {
	return others;
    }

    public void setOthers(String others)
    {
	this.others = others;
    }

    public String getClass_type()
    {
	return class_type;
    }

    public void setClass_type(String class_type)
    {
	this.class_type = class_type;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public int getClass_id()
    {
	return class_id;
    }

    public void setClass_id(int class_id)
    {
	this.class_id = class_id;
    }

    public String getClass_name()
    {
	return class_name;
    }

    public void setClass_name(String class_name)
    {
	this.class_name = class_name;
    }

    public int getClass_no()
    {
	return class_no;
    }

    public void setClass_no(int class_no)
    {
	this.class_no = class_no;
    }

    public int getWeek_no()
    {
	return week_no;
    }

    public void setWeek_no(int week_no)
    {
	this.week_no = week_no;
    }

    public int getWeek_day()
    {
	return week_day;
    }

    public void setWeek_day(int week_day)
    {
	this.week_day = week_day;
    }

    public String getClass_starttime()
    {
	return class_starttime;
    }

    public void setClass_starttime(String class_starttime)
    {
	this.class_starttime = class_starttime;
    }

    public String getClass_finishtime()
    {
	return class_finishtime;
    }

    public void setClass_finishtime(String class_finishtime)
    {
	this.class_finishtime = class_finishtime;
    }

    public int getClass_cut()
    {
	return class_cut;
    }

    public void setClass_cut(int class_cut)
    {
	this.class_cut = class_cut;
    }

    public String getLocation()
    {
	return location;
    }

    public void setLocation(String location)
    {
	this.location = location;
    }

    public int getReminder()
    {
	return reminder;
    }

    public void setReminder(int reminder)
    {
	this.reminder = reminder;
    }

    public ClassInfo getclassinfo(Context context, int class_id)
    {
	DBOperation Do = new DBOperation();
	ci = Do.returnci(context, class_id);

	return ci;
    }

}
