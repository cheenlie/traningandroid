package com.pmkebiao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBCreater extends SQLiteOpenHelper {


	private static final int VERSION = 1;
	String sql1 = "create table if not exists Class_info"
			+ "(id   INTEGER PRIMARY KEY AUTOINCREMENT,class_name varchar,class_type varchar,class_start_week int,timers_total int,timers_oneweek int,Class_each_week varchar,Class_starttime varchar,Class_finnishtime varchar,"
			+ "class_cut int,class_cut_all varchar,location varchar,reminder int,moneycost varchar,others varchar,insert_time varchar,modify_time varchar,user_id int ,child_id int)";
	String sql2 = "create table if not exists Classes"
			+ "(id  INTEGER PRIMARY KEY AUTOINCREMENT,courseid varchar ,class_id int,class_no int,week_no int,week_day int,class_starttime varchar,class_finishtime varchar,class_name varchar,  class_cut int, location varchar, class_type varchar,reminder int, others varchar,insert_time varchar,modify_time varchar,child_id int)";
	String sql3 = "create table if not exists Notes"
			+ "(id  INTEGER PRIMARY KEY AUTOINCREMENT ,lesson_id int,img_no int, notetext varchar,note_img0 varchar,note_img1 varchar,note_img2 varchar,note_img3 varchar,note_img4 varchar,note_img5 varchar,note_img6 varchar,note_img7 varchar,note_img8 varchar,note_img0_y varchar,note_img1_y varchar,note_img2_y varchar,note_img3_y varchar,note_img4_y varchar,note_img5_y varchar,note_img6_y varchar,note_img7_y varchar,note_img8_y varchar,others varchar,insert_time varchar,  modify_time varchar, lesson_time varchar, location varchar,flag int,child_id int)";
	String sql4 = "create table if not exists Childs"
			+ "(id  INTEGER PRIMARY KEY AUTOINCREMENT,cid varchar,parent_id int,img_tx varchar, name varchar,sex int,level int,childno int,insert_time varchar)";
	String sql5 = "create table if not exists Users"
			+ "(id  INTEGER PRIMARY KEY  ,phone_no varchar,password varchar, name varchar,sex int,img_user_tx varchar,location varchar)";
	String sqlinsert="insert into Users(  phone_no,  password, sex, img_user_tx,name, location  ) "
			+ "values('0','0',0,'0','0','0')";
	public DBCreater(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBCreater(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public DBCreater(Context context, String name) {
		this(context, name, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		db.execSQL(sqlinsert);
		
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
