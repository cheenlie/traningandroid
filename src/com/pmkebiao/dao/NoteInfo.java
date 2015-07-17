package com.pmkebiao.dao;
import java.util.ArrayList;

import com.pmkebiao.db.DBOperation;


import android.content.Context;

public class NoteInfo {
	private SingleClass sc;
	private int id;
	private int lessonid;
	private int img_no;		
	private int child_id;
	

	public int getChild_id() {
		return child_id;
	}


	public void setChild_id(int child_id) {
		this.child_id = child_id;
	}

	private String notetext;
	private String note_img0;
	private String note_img1;
	private String note_img2;
	private String note_img3;
	private String note_img4;
	private String note_img5;
	private String note_img6;
	private String note_img7;
	private String note_img8;
	private String note_img0_y;
	private String note_img1_y;
	private String note_img2_y;
	private String note_img3_y;
	private String note_img4_y;
	private String note_img5_y;
	private String note_img6_y;
	private String note_img7_y;
	private String note_img8_y;
	
	
	private String others;
	private String insert_time;
	private String modify_time;
	private String lesson_time;
	private String location;
	public   ArrayList<String> img_list=new ArrayList<String>();
	public   ArrayList<String> img_list_y=new ArrayList<String>();
	private int flag;
	public NoteInfo(int id, int lessonid, int img_no, String notetext,
			String note_img0, String note_img1, String note_img2,
			String note_img3, String note_img4, String note_img5,
			String note_img6, String note_img7, String note_img8,
			String note_img0_y, String note_img1_y, String note_img2_y,
			String note_img3_y, String note_img4_y, String note_img5_y,
			String note_img6_y, String note_img7_y, String note_img8_y,
			String others, String insert_time, String modify_time,String lesson_time,String location, int flag,int child_id) {
		super();
		this.id = id;
		this.lessonid = lessonid;
		this.img_no = img_no;
		this.notetext = notetext;
		this.note_img0 = note_img0;
		this.note_img1 = note_img1;
		this.note_img2 = note_img2;
		this.note_img3 = note_img3;
		this.note_img4 = note_img4;
		this.note_img5 = note_img5;
		this.note_img6 = note_img6;
		this.note_img7 = note_img7;
		this.note_img8 = note_img8;
		this.note_img0_y = note_img0_y;
		this.note_img1_y = note_img1_y;
		this.note_img2_y = note_img2_y;
		this.note_img3_y = note_img3_y;
		this.note_img4_y = note_img4_y;
		this.note_img5_y= note_img5_y;
		this.note_img6_y = note_img6_y;
		this.note_img7_y = note_img7_y;
		this.note_img8_y = note_img8_y;
		this.others = others;
		this.insert_time = insert_time;
		this.modify_time = modify_time;
		this.lesson_time=lesson_time;
		this.location=location;
		this.flag = flag;
        this.img_list.add(note_img0);
        this.img_list.add(note_img1);
        this.img_list.add(note_img2);
        this.img_list.add(note_img3);
        this.img_list.add(note_img4);
        this.img_list.add(note_img5);
        this.img_list.add(note_img6);
        this.img_list.add(note_img7);
        this.img_list.add(note_img8);     
        this.img_list_y.add(note_img0_y);
        this.img_list_y.add(note_img1_y);
        this.img_list_y.add(note_img2_y);
        this.img_list_y.add(note_img3_y);
        this.img_list_y.add(note_img4_y);
        this.img_list_y.add(note_img5_y);
        this.img_list_y.add(note_img6_y);
        this.img_list_y.add(note_img7_y);
        this.img_list_y.add(note_img8_y);   
        this.child_id=child_id;
        
	}

	
	public String getNote_img0_y() {
		return note_img0_y;
	}


	public void setNote_img0_y(String note_img0_y) {
		this.note_img0_y = note_img0_y;
	}


	public String getNote_img1_y() {
		return note_img1_y;
	}


	public void setNote_img1_y(String note_img1_y) {
		this.note_img1_y = note_img1_y;
	}


	public String getNote_img2_y() {
		return note_img2_y;
	}


	public void setNote_img2_y(String note_img2_y) {
		this.note_img2_y = note_img2_y;
	}


	public String getNote_img3_y() {
		return note_img3_y;
	}


	public void setNote_img3_y(String note_img3_y) {
		this.note_img3_y = note_img3_y;
	}


	public String getNote_img4_y() {
		return note_img4_y;
	}


	public void setNote_img4_y(String note_img4_y) {
		this.note_img4_y = note_img4_y;
	}


	public String getNote_img5_y() {
		return note_img5_y;
	}


	public void setNote_img5_y(String note_img5_y) {
		this.note_img5_y = note_img5_y;
	}


	public String getNote_img6_y() {
		return note_img6_y;
	}


	public void setNote_img6_y(String note_img6_y) {
		this.note_img6_y = note_img6_y;
	}


	public String getNote_img7_y() {
		return note_img7_y;
	}


	public void setNote_img7_y(String note_img7_y) {
		this.note_img7_y = note_img7_y;
	}


	public String getNote_img8_y() {
		return note_img8_y;
	}


	public void setNote_img8_y(String note_img8_y) {
		this.note_img8_y = note_img8_y;
	}


	public ArrayList<String> getImg_list() {
		return img_list;
	}


	public void setImg_list(ArrayList<String> img_list) {
		this.img_list = img_list;
	}


	public ArrayList<String> getImg_list_y() {
		return img_list_y;
	}


	public void setImg_list_y(ArrayList<String> img_list_y) {
		this.img_list_y = img_list_y;
		this.note_img0_y = img_list_y.get(0);
		this.note_img1_y = img_list_y.get(1);
		this.note_img2_y = img_list_y.get(2);
		this.note_img3_y = img_list_y.get(3);
		this.note_img4_y = img_list_y.get(4);
		this.note_img5_y= img_list_y.get(5);
		this.note_img6_y = img_list_y.get(6);
		this.note_img7_y = img_list_y.get(7);
		this.note_img8_y = img_list_y.get(8);
		
	}


	public SingleClass getSc() {
		return sc;
	}


	public String getLesson_time() {
		return lesson_time;
	}

	public void setLesson_time(String lesson_time) {
		this.lesson_time = lesson_time;
	}

	public String getLocation() {
		return location;
	}

	public String getimg(int img_no)
	{
		
		return img_list.get(img_no);
		
	}
	public String getimg_y(int img_no_y)
	{
		
		return img_list_y.get(img_no_y);
		
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public void setLessonid(int lessonid) {
		this.lessonid = lessonid;
	}

	public SingleClass getsingleclass(Context context, int lesson_id) {
		DBOperation Do = new DBOperation();
		sc = Do.return_single_lesson(context, lesson_id);

		return sc;
	}

	

	public void setSc(SingleClass sc) {
		this.sc = sc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLessonid() {
		return lessonid;
	}

	public void setClassid(int lessonid) {
		this.lessonid = lessonid;
	}

	public int getImg_no() {
		return img_no;
	}

	public void setImg_no(int img_no) {
		this.img_no = img_no;
	}

	public String getNotetext() {
		return notetext;
	}

	public void setNotetext(String notetext) {
		this.notetext = notetext;
	}

	public String getNote_img0() {
		return note_img0;
	}

	public void setNote_img0(String note_img0) {
		this.note_img0 = note_img0;
	}

	public String getNote_img1() {
		return note_img1;
	}

	public void setNote_img1(String note_img1) {
		this.note_img1 = note_img1;
	}

	public String getNote_img2() {
		return note_img2;
	}

	public void setNote_img2(String note_img2) {
		this.note_img2 = note_img2;
	}

	public String getNote_img3() {
		return note_img3;
	}

	public void setNote_img3(String note_img3) {
		this.note_img3 = note_img3;
	}

	public String getNote_img4() {
		return note_img4;
	}

	public void setNote_img4(String note_img4) {
		this.note_img4 = note_img4;
	}

	public String getNote_img5() {
		return note_img5;
	}

	public void setNote_img5(String note_img5) {
		this.note_img5 = note_img5;
	}

	public String getNote_img6() {
		return note_img6;
	}

	public void setNote_img6(String note_img6) {
		this.note_img6 = note_img6;
	}

	public String getNote_img7() {
		return note_img7;
	}

	public void setNote_img7(String note_img7) {
		this.note_img7 = note_img7;
	}

	public String getNote_img8() {
		return note_img8;
	}

	public void setNote_img8(String note_img8) {
		this.note_img8 = note_img8;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}

	public String getModify_time() {
		return modify_time;
	}

	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
