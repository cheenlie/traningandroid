package com.pmkebiao.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.pmkebiao.dao.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBOperation {
	public void deleteallnotes(Context context) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		String abc = "DELETE FROM  Notes";

		sd.execSQL(abc);
	}

	public void insertorupdatenote(Context context, NoteInfo myNoteinfo) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		String abc = " select * from Notes where lesson_id='"
				+ myNoteinfo.getLessonid() + "'";
		Cursor cursor = sd.rawQuery(abc, null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			if (myNoteinfo.getImg_no() == 0) {
				String update = "update Notes set" + " img_no='"
						+ myNoteinfo.getImg_no() + "' ,notetext='"
						+ myNoteinfo.getNotetext() + "',others='"
						+ myNoteinfo.getOthers() + "',insert_time='"
						+ myNoteinfo.getInsert_time() + "' where id='" + id
						+ "'";

				sd.execSQL(update);
			} else {
				String update = "update Notes set" + " img_no='"
						+ myNoteinfo.getImg_no() + "' ,notetext='"
						+ myNoteinfo.getNotetext() + "',note_img0_y='"
						+ myNoteinfo.getImg_list_y().get(0) + "' "
						+ " ,note_img1_y='" + myNoteinfo.getImg_list_y().get(1)
						+ "' ,note_img2_y='"
						+ myNoteinfo.getImg_list_y().get(2) + "',note_img3_y='"
						+ myNoteinfo.getImg_list_y().get(3) + "',note_img4_y='"
						+ myNoteinfo.getImg_list_y().get(4) + "',note_img5_y='"
						+ myNoteinfo.getImg_list_y().get(5) + "',note_img6_y='"
						+ myNoteinfo.getImg_list_y().get(6) + "',note_img7_y='"
						+ myNoteinfo.getImg_list_y().get(7) + "',note_img8_y='"
						+ myNoteinfo.getImg_list_y().get(8) + "',others='"
						+ myNoteinfo.getOthers() + "',insert_time='"
						+ myNoteinfo.getInsert_time() + "' where id='" + id
						+ "'";

				sd.execSQL(update);
			}
			db.close();
		} else {
			insert_note(context, myNoteinfo);
		}

	}

	public int check_lesson_id(Context context, String course_id, int class_no) {
		int id = -1;
		try {

			DBCreater db = new DBCreater(context, "myclass.db");
			SQLiteDatabase sd = db.getReadableDatabase();
			String abc = " select * from Classes where courseid='" + course_id
					+ "'and  class_no='" + class_no + "'";

			Cursor cursor = sd.rawQuery(abc, null);
			while (cursor.moveToNext()) {
				id = cursor.getInt(cursor.getColumnIndex("id"));
			}

			db.close();
			return id;

		} catch (Exception e) {
			return id;
		}

	}

	public int check_class_id(Context context, String course_id, int childid,
			String name) {
		int id = -1;
		try {

			DBCreater db = new DBCreater(context, "myclass.db");
			SQLiteDatabase sd = db.getReadableDatabase();
			String abc = " select * from Class_info where others='" + course_id
					+ "'and  child_id='" + childid + "'and class_name='" + name
					+ "'";

			Cursor cursor = sd.rawQuery(abc, null);
			while (cursor.moveToNext()) {
				id = cursor.getInt(cursor.getColumnIndex("id"));
			}

			db.close();
			return id;

		} catch (Exception e) {
			return id;
		}

	}

	public void update_user(Context context, UserInfo user) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sqliteDatabase = db.getReadableDatabase();
		String abc = "update Users set" + " phone_no='" + user.getPhone_no()
				+ "' ,img_user_tx='" + user.getImg_user_tx() + "',password='"
				+ user.getPassword() + "' ,name='" + user.getName()
				+ "' ,sex='" + user.getSex() + "' ,location='"
				+ user.getlocation() + "'where id='" + user.getId() + "'";
		sqliteDatabase.execSQL(abc);
		db.close();
	}

	public void insert_user(Context context, UserInfo user) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		sd.execSQL("insert into Users(  phone_no,  password, sex, img_user_tx,name, location  ) "
				+ "values('"
				+ user.getPhone_no()
				+ "','"
				+ user.getPassword()
				+ "','"
				+ user.getSex()
				+ "','"
				+ user.getImg_user_tx()
				+ "','"
				+ user.getName() + "','" + user.getlocation() + "')");
		db.close();
	}

	public int lastuser(Context context) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		int id = 0;
		String abc = " select id from Users desc";
		Cursor cursor = sd.rawQuery(abc, null);
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex("id"));
		}
		db.close();
		return id;

	}

	public void delete_child(Context context, int id) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		sd.execSQL("delete from Childs where id='" + id + "'");
		db.close();
	}

	public void update_child(Context context, ChildInfo ci) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sqliteDatabase = db.getReadableDatabase();
		String abc = "update Childs  set" + " img_tx='" + ci.getImg_tx()
				+ "' ,cid='" + ci.getCid() + "' ,name='" + ci.getName()
				+ "' ,sex='" + ci.getSex() + "' ,level='" + ci.getLevel()
				+ "'where cid='" + ci.getCid() + "'and  parent_id='"
				+ ci.getParent_id() + "'";
		sqliteDatabase.execSQL(abc);
		db.close();
	}

	public void insert_child(Context context, ChildInfo child_info) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// �������ڸ�ʽ
		String insert_time = df.format(new Date());
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		sd.execSQL("insert into Childs(cid,parent_id ,img_tx , name ,sex ,level  ,insert_time   ) "
				+ "values('"
				+ child_info.getCid()
				+ "','"
				+ child_info.getParent_id()
				+ "','"
				+ child_info.getImg_tx()
				+ "','"
				+ child_info.getName()
				+ "','"
				+ child_info.getSex()
				+ "','" + child_info.getLevel() + "','" + insert_time + "')");
		db.close();
	}

	public ChildInfo getsingle_child_info(Context context, int id) {

		ChildInfo ci = null;
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		String abc = " select * from Childs where id='" + id + "'";
		Cursor cursor = sd.rawQuery(abc, null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			ci = new ChildInfo(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("cid")),
					cursor.getInt(cursor.getColumnIndex("parent_id")),
					cursor.getString(cursor.getColumnIndex("name")),
					cursor.getString(cursor.getColumnIndex("img_tx")),
					cursor.getInt(cursor.getColumnIndex("sex")),
					cursor.getInt(cursor.getColumnIndex("level")));
		}
		return ci;
	}

	public ArrayList<ChildInfo> getchild_info(Context context, int parentid) {
		ArrayList<ChildInfo> al = new ArrayList<ChildInfo>();
		ChildInfo ci;
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		String abc = " select * from Childs where parent_id='" + parentid + "'";
		Cursor cursor = sd.rawQuery(abc, null);
		while (cursor.moveToNext()) {
			ci = new ChildInfo(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("cid")), parentid,
					cursor.getString(cursor.getColumnIndex("name")),
					cursor.getString(cursor.getColumnIndex("img_tx")),
					cursor.getInt(cursor.getColumnIndex("sex")),
					cursor.getInt(cursor.getColumnIndex("level")));
			al.add(ci);
		}
		return al;
	}

	public UserInfo get_user_info(Context context, String phone_no) {
		UserInfo ni = null;
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		Cursor cursor = sd.rawQuery("select  * from Users where phone_no='"
				+ phone_no + "'", null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			ni = new UserInfo(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("phone_no")),
					cursor.getString(cursor.getColumnIndex("password")),
					cursor.getInt(cursor.getColumnIndex("sex")),
					cursor.getString(cursor.getColumnIndex("img_user_tx")),
					cursor.getString(cursor.getColumnIndex("name")),
					cursor.getString(cursor.getColumnIndex("location")));
			return ni;
		} else {
			return ni;
		}
	}

	public UserInfo get_user_info_1(Context context, int id) {
		UserInfo ni = null;
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		Cursor cursor = sd.rawQuery("select  * from Users where id='" + id
				+ "'", null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			ni = new UserInfo(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("phone_no")),
					cursor.getString(cursor.getColumnIndex("password")),
					cursor.getInt(cursor.getColumnIndex("sex")),
					cursor.getString(cursor.getColumnIndex("img_user_tx")),
					cursor.getString(cursor.getColumnIndex("name")),
					cursor.getString(cursor.getColumnIndex("location")));
			return ni;
		} else {
			return ni;
		}
	}

	public NoteInfo get_lesson_note(Context context, int lessonid) {
		NoteInfo ni = null;
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		Cursor cursor = sd.rawQuery("select  * from Notes where lesson_id='"
				+ lessonid + "'", null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			ni = new NoteInfo(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getInt(cursor.getColumnIndex("lesson_id")),
					cursor.getInt(cursor.getColumnIndex("img_no")),
					cursor.getString(cursor.getColumnIndex("notetext")),
					cursor.getString(cursor.getColumnIndex("note_img0")),
					cursor.getString(cursor.getColumnIndex("note_img1")),
					cursor.getString(cursor.getColumnIndex("note_img2")),
					cursor.getString(cursor.getColumnIndex("note_img3")),
					cursor.getString(cursor.getColumnIndex("note_img4")),
					cursor.getString(cursor.getColumnIndex("note_img5")),
					cursor.getString(cursor.getColumnIndex("note_img6")),
					cursor.getString(cursor.getColumnIndex("note_img7")),
					cursor.getString(cursor.getColumnIndex("note_img8")),
					cursor.getString(cursor.getColumnIndex("note_img0_y")),
					cursor.getString(cursor.getColumnIndex("note_img1_y")),
					cursor.getString(cursor.getColumnIndex("note_img2_y")),
					cursor.getString(cursor.getColumnIndex("note_img3_y")),
					cursor.getString(cursor.getColumnIndex("note_img4_y")),
					cursor.getString(cursor.getColumnIndex("note_img5_y")),
					cursor.getString(cursor.getColumnIndex("note_img6_y")),
					cursor.getString(cursor.getColumnIndex("note_img7_y")),
					cursor.getString(cursor.getColumnIndex("note_img8_y")),
					cursor.getString(cursor.getColumnIndex("others")),
					cursor.getString(cursor.getColumnIndex("insert_time")),
					cursor.getString(cursor.getColumnIndex("modify_time")),
					cursor.getString(cursor.getColumnIndex("lesson_time")),
					cursor.getString(cursor.getColumnIndex("location")),
					cursor.getInt(cursor.getColumnIndex("flag")),
					cursor.getInt(cursor.getColumnIndex("child_id")));
			return ni;
		} else {
			return ni;
		}
	}

	public ArrayList<NoteInfo> select_top_notes(Context context, int no,
			int no2, int childid) {
		ArrayList<NoteInfo> al = new ArrayList<NoteInfo>();
		NoteInfo ni;
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		String abc = " select * from Notes where child_id='" + childid
				+ "'order by id desc limit '" + no + "','" + no2 + "'";
		Cursor cursor = sd.rawQuery(abc, null);
		while (cursor.moveToNext()) {
			ni = new NoteInfo(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getInt(cursor.getColumnIndex("lesson_id")),
					cursor.getInt(cursor.getColumnIndex("img_no")),
					cursor.getString(cursor.getColumnIndex("notetext")),
					cursor.getString(cursor.getColumnIndex("note_img0")),
					cursor.getString(cursor.getColumnIndex("note_img1")),
					cursor.getString(cursor.getColumnIndex("note_img2")),
					cursor.getString(cursor.getColumnIndex("note_img3")),
					cursor.getString(cursor.getColumnIndex("note_img4")),
					cursor.getString(cursor.getColumnIndex("note_img5")),
					cursor.getString(cursor.getColumnIndex("note_img6")),
					cursor.getString(cursor.getColumnIndex("note_img7")),
					cursor.getString(cursor.getColumnIndex("note_img8")),
					cursor.getString(cursor.getColumnIndex("note_img0_y")),
					cursor.getString(cursor.getColumnIndex("note_img1_y")),
					cursor.getString(cursor.getColumnIndex("note_img2_y")),
					cursor.getString(cursor.getColumnIndex("note_img3_y")),
					cursor.getString(cursor.getColumnIndex("note_img4_y")),
					cursor.getString(cursor.getColumnIndex("note_img5_y")),
					cursor.getString(cursor.getColumnIndex("note_img6_y")),
					cursor.getString(cursor.getColumnIndex("note_img7_y")),
					cursor.getString(cursor.getColumnIndex("note_img8_y")),
					cursor.getString(cursor.getColumnIndex("others")),
					cursor.getString(cursor.getColumnIndex("insert_time")),
					cursor.getString(cursor.getColumnIndex("modify_time")),
					cursor.getString(cursor.getColumnIndex("lesson_time")),
					cursor.getString(cursor.getColumnIndex("location")),
					cursor.getInt(cursor.getColumnIndex("flag")),
					cursor.getInt(cursor.getColumnIndex("child_id")));
			al.add(ni);
		}
		return al;
	}

	public void delete_note(Context context, int note_id) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		sd.execSQL("delete from Notes where id='" + note_id + "'");
		db.close();
	}

	public boolean insert_note(Context context, NoteInfo ni) {
		try {
			if (ni.getInsert_time() == "") {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// �������ڸ�ʽ
				String insert_time = df.format(new Date());
				ni.setInsert_time(insert_time);
			}
			DBCreater db = new DBCreater(context, "myclass.db");
			SQLiteDatabase sd = db.getReadableDatabase();
			sd.execSQL("insert into Notes(lesson_id ,img_no , notetext ,note_img0 ,note_img1 ,note_img2 ,note_img3 ,note_img4 ,note_img5 ,note_img6 ,note_img7,note_img8 ,note_img0_y ,note_img1_y ,note_img2_y ,note_img3_y ,note_img4_y ,note_img5_y ,note_img6_y ,note_img7_y,note_img8_y ,others ,insert_time  , lesson_time , location ,flag,child_id  ) "
					+ "values('"
					+ ni.getLessonid()
					+ "','"
					+ ni.getImg_no()
					+ "','"
					+ ni.getNotetext()
					+ "','"
					+ ni.getNote_img0()
					+ "','"
					+ ni.getNote_img1()
					+ "','"
					+ ni.getNote_img2()
					+ "','"
					+ ni.getNote_img3()
					+ "','"
					+ ni.getNote_img4()
					+ "','"
					+ ni.getNote_img5()
					+ "','"
					+ ni.getNote_img6()
					+ "','"
					+ ni.getNote_img7()
					+ "','"
					+ ni.getNote_img8()
					+ "','"
					+ ni.getNote_img0_y()
					+ "','"
					+ ni.getNote_img1_y()
					+ "','"
					+ ni.getNote_img2_y()
					+ "','"
					+ ni.getNote_img3_y()
					+ "','"
					+ ni.getNote_img4_y()
					+ "','"
					+ ni.getNote_img5_y()
					+ "','"
					+ ni.getNote_img6_y()
					+ "','"
					+ ni.getNote_img7_y()
					+ "','"
					+ ni.getNote_img8_y()
					+ "','"
					+ ni.getOthers()
					+ "','"
					+ ni.getInsert_time()
					+ "','"
					+ ni.getLesson_time()
					+ "','"
					+ ni.getLocation()
					+ "','"
					+ ni.getFlag()
					+ "','"
					+ ni.getChild_id() + "')");
			db.close();
		} catch (Exception e) {
			Log.d("Exception", e.toString());

			return false;
		}

		return true;
	}

	public void update_single_class(Context context, SingleClass sc) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// �������ڸ�ʽ
		String modify_time = df.format(new Date());
		SQLiteDatabase sqliteDatabase = db.getReadableDatabase();

		String abc = "update Classes set" + " class_id='" + sc.getClass_id()
				+ "' ,class_no='" + sc.getClass_no() + "' ,week_no='"
				+ sc.getWeek_no() + "' ,week_day='" + sc.getWeek_day()
				+ "' ,class_starttime='" + sc.getClass_starttime()
				+ "' ,class_finishtime='" + sc.getClass_finishtime()
				+ "' ,class_name='" + sc.getClass_name() + "' , class_cut='"
				+ sc.getClass_cut() + "' , location='" + sc.getLocation()
				+ "' , class_type='" + sc.getClass_type() + "' ,reminder='"
				+ sc.getReminder() + "' , others='" + sc.getOthers()
				+ "' ,modify_time='" + modify_time + "' where id='"
				+ sc.getId() + "'";

		sqliteDatabase.execSQL(abc);
		db.close();

	}

	public Boolean insert_entire_class(Context context, ClassInfo ci) {
		try {
			DBCreater db = new DBCreater(context, "myclass.db");
			SQLiteDatabase sqliteDatabase = db.getReadableDatabase();
			int timers_oneweek = ci.getTimers_oneweek();// ÿ�ܴ��� ��ѡ
			String Class_each_week = "";
			String Class_starttime = "";
			String Class_finnishtime = "";
			for (int i = 0; i < timers_oneweek; i++) {
				Class_each_week = Class_each_week + "*"
						+ ci.getClass_each_week()[i];
				Class_starttime = Class_starttime + "*"
						+ ci.getClass_starttime()[i];
				Class_finnishtime = Class_finnishtime + "*"
						+ ci.getClass_finnishtime()[i];
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// �������ڸ�ʽ
			String insert_time = df.format(new Date());
			sqliteDatabase
					.execSQL("insert into Class_info(class_name ,class_type ,class_start_week ,timers_total ,timers_oneweek ,Class_each_week ,Class_starttime ,Class_finnishtime ,"
							+ "location ,reminder ,moneycost ,others,child_id,insert_time ) values('"
							+ ci.getClass_name()
							+ "','"
							+ ci.getClass_type()
							+ "','"
							+ ci.getClass_start_week()
							+ "','"
							+ ci.getTimers_total()
							+ "','"
							+ ci.getTimers_oneweek()
							+ "','"
							+ Class_each_week
							+ "','"
							+ Class_starttime
							+ "','"
							+ Class_finnishtime
							+ "','"
							+ ci.getLocation()
							+ "','"
							+ ci.getReminder()
							+ "','"
							+ ci.getMoneycost()
							+ "','"
							+ ci.getOthers()
							+ "','"
							+ ci.getChild_id()
							+ "','"
							+ insert_time
							+ "')");
			Cursor cursor = sqliteDatabase.rawQuery(
					"select  * from Class_info order by id desc", null);
			if (cursor.getCount() != 0) {
				cursor.moveToFirst();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String class_name = cursor.getString(cursor
						.getColumnIndex("class_name"));
				if (class_name.equals(ci.getClass_name())) {
					int remain_timers = ci.getTimers_total();
					/**
					 * ���޸�week=1Ϊweek=0 �������û���������
					 */
					int week = 0;

					while (remain_timers > timers_oneweek) {
						for (int i = 0; i < timers_oneweek; i++) {
							insert_single_class(
									context,
									class_name,
									id,
									ci.getTimers_total() - remain_timers,
									week + ci.getClass_start_week(),
									Integer.parseInt(ci.getClass_each_week()[i]),
									ci.getClass_starttime()[i], ci
											.getClass_finnishtime()[i], 0, ci
											.getLocation(), ci.getReminder(),
									ci.getOthers(), ci.getChild_id(), ci
											.getClass_type());
							remain_timers--;

							Log.d("rrrrrr", week + "nn" + remain_timers);
						}
						week++;
					}
					while (remain_timers <= timers_oneweek) {
						boolean flag = false;
						for (int i = 0; i < timers_oneweek; i++) {
							insert_single_class(
									context,
									class_name,
									id,
									ci.getTimers_total() - remain_timers,
									week + ci.getClass_start_week(),
									Integer.parseInt(ci.getClass_each_week()[i]),
									ci.getClass_starttime()[i], ci
											.getClass_finnishtime()[i], 0, ci
											.getLocation(), ci.getReminder(),
									ci.getOthers(), ci.getChild_id(), ci
											.getClass_type());
							Log.d("xxxxx", week + "nn" + remain_timers);

							/**
							 * ���޸� remain_timers > =0Ϊremain_timers > 0 if
							 * (remain_timers <0)Ϊif (remain_timers == 0)
							 * ��������һ�ڿ�����
							 */
							if (remain_timers > 0) {
								remain_timers--;
							}

							if (remain_timers == 0) {
								flag = true;
								break;
							}

						}
						if (flag == true) {
							break;
						} else {
							week++;
						}
					}
				} else {

				}
				db.close();
				cursor.close();
				return true;
			} else {
				db.close();
				cursor.close();
				return false;
			}

		} catch (Exception e) {
			Log.d("Exception", e.toString());
			return false;
		}
	}

	private boolean insert_single_class(Context context, String class_name,
			int class_id, int class_no, int week_no, int week_day,
			String class_starttime, String class_finishtime, int class_cut,
			String location, int reminder, String others, int childid,
			String class_type) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// �������ڸ�ʽ
			String insert_time = df.format(new Date());
			DBCreater db = new DBCreater(context, "myclass.db");
			SQLiteDatabase sd = db.getReadableDatabase();
			sd.execSQL("insert into Classes(class_id ,courseid,class_no ,week_no ,week_day ,class_starttime ,class_finishtime ,class_name , class_cut , location , class_type ,reminder , others,child_id ,insert_time  ) "
					+ "values('"
					+ class_id
					+ "','"
					+ others
					+ "','"
					+ (class_no + 1)
					+ "','"
					+ week_no
					+ "','"
					+ week_day
					+ "','"
					+ class_starttime
					+ "','"
					+ class_finishtime
					+ "','"
					+ class_name
					+ "','"
					+ class_cut
					+ "','"
					+ location
					+ "','"
					+ class_type
					+ "','"
					+ reminder
					+ "','"
					+ others
					+ "','"
					+ childid + "','" + insert_time + "')");
			db.close();
		} catch (Exception e) {
			Log.d("Exception", e.toString());

			return false;
		}

		return true;
	}

	public ClassInfo returnci(Context context, int classid) {
		ClassInfo ci = null;
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		Cursor cursor = sd.rawQuery("select  * from Class_info where id='"
				+ classid + "'", null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			String Class_on_week = cursor.getString(cursor
					.getColumnIndex("Class_each_week"));
			String Class_starttime = cursor.getString(cursor
					.getColumnIndex("Class_starttime"));
			String Class_finnishtime = cursor.getString(cursor
					.getColumnIndex("Class_finnishtime"));

			String a[] = Class_on_week.split("\\*");
			String b[] = Class_starttime.split("\\*");
			String c[] = Class_finnishtime.split("\\*");
			ci = new ClassInfo(cursor.getString(cursor
					.getColumnIndex("class_name")), cursor.getString(cursor
					.getColumnIndex("class_type")), cursor.getInt(cursor
					.getColumnIndex("class_start_week")), cursor.getInt(cursor
					.getColumnIndex("timers_total")), cursor.getInt(cursor
					.getColumnIndex("timers_oneweek")), a, b, c,
					cursor.getString(cursor.getColumnIndex("location")),
					cursor.getInt(cursor.getColumnIndex("reminder")),
					cursor.getString(cursor.getColumnIndex("moneycost")),
					cursor.getString(cursor.getColumnIndex("others")),
					cursor.getInt(cursor.getColumnIndex("child_id")));
		}
		return ci;
	}

	public ClassInfo returnci_corseid(Context context, int corseid) {
		ClassInfo ci = null;
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();
		Cursor cursor = sd.rawQuery("select  * from Class_info where others='"
				+ corseid + "'", null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			String Class_on_week = cursor.getString(cursor
					.getColumnIndex("Class_each_week"));
			String Class_starttime = cursor.getString(cursor
					.getColumnIndex("Class_starttime"));
			String Class_finnishtime = cursor.getString(cursor
					.getColumnIndex("Class_finnishtime"));

			String a[] = Class_on_week.split("\\*");
			String b[] = Class_starttime.split("\\*");
			String c[] = Class_finnishtime.split("\\*");
			ci = new ClassInfo(cursor.getString(cursor
					.getColumnIndex("class_name")), cursor.getString(cursor
					.getColumnIndex("class_type")), cursor.getInt(cursor
					.getColumnIndex("class_start_week")), cursor.getInt(cursor
					.getColumnIndex("timers_total")), cursor.getInt(cursor
					.getColumnIndex("timers_oneweek")), a, b, c,
					cursor.getString(cursor.getColumnIndex("location")),
					cursor.getInt(cursor.getColumnIndex("reminder")),
					cursor.getString(cursor.getColumnIndex("moneycost")),
					cursor.getString(cursor.getColumnIndex("others")),
					cursor.getInt(cursor.getColumnIndex("child_id")));
		}
		return ci;
	}

	public void delete_entire_class(Context context, int class_id,
			boolean delnote) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sd = db.getReadableDatabase();

		String abc = " select * from Classes where class_id='" + class_id + "'";
		Cursor cursor = sd.rawQuery(abc, null);
		while (cursor.moveToNext()) {
			if (delnote == true) {
				sd.execSQL("delete from Notes where lesson_id='"
						+ cursor.getInt(cursor.getColumnIndex("id")) + "'");
			}

			else {
				sd.execSQL("update  Notes SET lesson_id=0 where lesson_id='"
						+ cursor.getInt(cursor.getColumnIndex("id")) + "'");

			}

		}

		sd.execSQL("delete from Class_info where id='" + class_id + "'");
		sd.execSQL("delete from Classes where class_id='" + class_id + "'");

	}

	public void delete_single_class(Context context, String courseid,
			int classno) {
		try {
			DBCreater db = new DBCreater(context, "myclass.db");
			SQLiteDatabase sd = db.getReadableDatabase();
			sd.execSQL("delete from Classes where courseid='" + courseid
					+ "'and class_no='" + classno + "'");
		} catch (Exception e) {
			Log.d("ee", e.toString());
		}
	}

	public void update_single_class(Context context, String courseid,
			int classno, int weekRank, int courseDay, String classPlace,
			String startTime, String endTime) {
		try {
			DBCreater db = new DBCreater(context, "myclass.db");

			SQLiteDatabase sqliteDatabase = db.getReadableDatabase();

			String abc = "update Classes set week_no='" + weekRank
					+ "' ,week_day='" + courseDay + "' ,class_starttime='"
					+ startTime + "' ,class_finishtime='" + endTime
					+ "'  , location='" + classPlace + "'  where courseid='"
					+ courseid + "' and class_no='" + classno + "'";

			sqliteDatabase.execSQL(abc);
			db.close();

		} catch (Exception e) {
			Log.d("ee", e.toString());
		}
	}

	public void delete_single_class(Context context, SingleClass sc,
			boolean insert_onemore) {
		try {
			DBCreater db = new DBCreater(context, "myclass.db");
			SQLiteDatabase sd = db.getReadableDatabase();

			sd.execSQL("UPDATE Class_info  SET class_cut = class_cut + 1 WHERE id='"
					+ sc.getClass_id() + "'");
			if (insert_onemore == false) {

				sd.execSQL("delete from Classes where id='" + sc.getId() + "'");
				sd.close();
				db.close();

			} else {

				SingleClass last_sc = return_single_base_lesson(
						context,
						"select  * from Classes where class_id='"
								+ sc.getClass_id() + "' order by id desc");
				SingleClass first_sc = return_single_base_lesson(
						context,
						"select  * from Classes where class_id='"
								+ sc.getClass_id() + "' order by id asc");
				Cursor cursor = sd.rawQuery(
						"select  * from Class_info where id='"
								+ sc.getClass_id() + "'", null);
				if (cursor.getCount() != 0) {
					cursor.moveToFirst();
					String Class_on_week = cursor.getString(cursor
							.getColumnIndex("Class_each_week"));
					String Class_starttime = cursor.getString(cursor
							.getColumnIndex("Class_starttime"));
					String Class_finnishtime = cursor.getString(cursor
							.getColumnIndex("Class_finnishtime"));
					int single_class_week_no = 0;
					String a[] = Class_on_week.split("\\*");
					String b[] = Class_starttime.split("\\*");
					String c[] = Class_finnishtime.split("\\*");
					for (int i = 1; i < a.length; i++) {
						if (Integer.parseInt(a[i]) == last_sc.getWeek_day()) {

							if (i < (a.length - 1)) {
								Class_on_week = a[i + 1];
								single_class_week_no = last_sc.getWeek_no();
								Class_starttime = b[i + 1];
								Class_finnishtime = c[i + 1];
							}
							if (i == (a.length - 1)) {
								Class_on_week = a[1];
								single_class_week_no = last_sc.getWeek_no() + 1;
								Class_starttime = b[1];
								Class_finnishtime = c[1];
							}

							insert_single_class(context, sc.getClass_name(),
									sc.getClass_id(), last_sc.getClass_no(),
									single_class_week_no,
									Integer.parseInt(Class_on_week),
									Class_starttime, Class_finnishtime, 0,
									last_sc.getLocation(),
									last_sc.getReminder(), last_sc.getOthers(),
									last_sc.getChildid(),
									last_sc.getClass_type());

							break;
						}
					}
					sd.execSQL("delete from Classes where id='" + sc.getId()
							+ "'");
					for (int classid = sc.getClass_no(); classid <= last_sc
							.getClass_no(); classid++) {
						sd.execSQL("UPDATE Classes  SET class_no = class_no - 1  WHERE id='"
								+ (classid + first_sc.getId()) + "'");
					}
					cursor.close();
				}
			}
		} catch (Exception e) {
			Log.d("ee", e.toString());
		}
	}

	public SingleClass return_single_lesson(Context context,
			int single_class_id) {
		String sql = "select  * from Classes where id='" + single_class_id
				+ "'";
		return return_single_base_lesson(context, sql);
	}

	public SingleClass return_single_base_lesson(Context context, String sql) {
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sqliteDatabase = db.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(sql, null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			int id = cursor.getInt(cursor.getColumnIndex("id"));

			int class_id = cursor.getInt(cursor.getColumnIndex("class_id"));
			String class_name = cursor.getString(cursor
					.getColumnIndex("class_name"));
			int class_no = cursor.getInt(cursor.getColumnIndex("class_no"));
			int week_no = cursor.getInt(cursor.getColumnIndex("week_no"));
			int class_cut = cursor.getInt(cursor.getColumnIndex("class_cut"));
			int reminder = cursor.getInt(cursor.getColumnIndex("reminder"));
			int week_day = cursor.getInt(cursor.getColumnIndex("week_day"));
			String class_starttime = cursor.getString(cursor
					.getColumnIndex("class_starttime"));
			String class_finishtime = cursor.getString(cursor
					.getColumnIndex("class_finishtime"));
			String location = cursor.getString(cursor
					.getColumnIndex("location"));
			String class_type = cursor.getString(cursor
					.getColumnIndex("class_type"));
			String others = cursor.getString(cursor.getColumnIndex("others"));
			String insert_time = cursor.getString(cursor
					.getColumnIndex("insert_time"));
			String modify_time = cursor.getString(cursor
					.getColumnIndex("modify_time"));
			SingleClass sc = new SingleClass(id, class_id, class_name,
					class_no, week_no, week_day, class_starttime,
					class_finishtime, class_cut, location, reminder,
					insert_time, class_type, others);
			sc.setChildid(cursor.getInt(cursor.getColumnIndex("child_id")));
			cursor.close();
			return sc;
		} else {
			return null;
		}
	}

	public ArrayList<SingleClass> return_week_total_class(Context context,
			int week_no, int child_id) {

		ArrayList<SingleClass> al = new ArrayList<SingleClass>();
		DBCreater db = new DBCreater(context, "myclass.db");
		SQLiteDatabase sqliteDatabase = db.getReadableDatabase();
		Cursor cursor = sqliteDatabase.rawQuery(
				"select  * from Classes where week_no='" + week_no
						+ "'and child_id='" + child_id + "'", null);

		while (cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int class_id = cursor.getInt(cursor.getColumnIndex("class_id"));
			String class_name = cursor.getString(cursor
					.getColumnIndex("class_name"));
			int class_no = cursor.getInt(cursor.getColumnIndex("class_no"));
			int class_cut = cursor.getInt(cursor.getColumnIndex("class_cut"));
			int reminder = cursor.getInt(cursor.getColumnIndex("reminder"));
			int week_day = cursor.getInt(cursor.getColumnIndex("week_day"));
			String class_starttime = cursor.getString(cursor
					.getColumnIndex("class_starttime"));
			String class_finishtime = cursor.getString(cursor
					.getColumnIndex("class_finishtime"));
			String location = cursor.getString(cursor
					.getColumnIndex("location"));
			String class_type = cursor.getString(cursor
					.getColumnIndex("class_type"));
			String others = cursor.getString(cursor.getColumnIndex("others"));
			String courseid = cursor.getString(cursor
					.getColumnIndex("courseid"));

			SingleClass sc = new SingleClass(id, class_id, class_name,
					class_no, week_no, week_day, class_starttime,
					class_finishtime, class_cut, location, reminder, others,
					class_type, courseid);
			sc.setChildid(cursor.getInt(cursor.getColumnIndex("child_id")));
			al.add(sc);
		}
		return al;

	}

}