package com.pmkebiao.util;

import android.content.Context;


public class Constant {

	 public static int START_ADDCOURSEACTIVITY = Constant.ADD_START;
	    public static int ADD_START = 0;
	    public static int EDIT_START = 1;
//	    public static Single_class noteSingle_class = null;
	    public static Context class_note_context = null;
	    public static boolean finishIt = false;

	    public static final int START_PUBLISHEDACTIVITY_ADD = 1;
	    public static final int START_PUBLISHEDACTIVITY_EDIT = 2;
	    public static int START_PUBLISHEDACTIVITY_FLAG = 0;

	    public static final int START_PHOTOACTIVITY_SHOAWACTICITY = 1;
	    public static int START_PHOTOACTIVITY_FLAG = 0;

	    public static final int CHILD_EDIT = 1;
	    public static final int CHILD_ADD = 2;
	    public static int CHILD_FLAG = 0;

	    public static int MYIDENTIFICATIONFLAG;
	    public static final int MYIDENTIFICATIONREGISTER = 1;
	    public static final int MYIDENTIFICATIONSHOW = 2;

//	    public static Child_info addChild_info ;
//	    
//	    public static User_info addUser_info;
	    
	    public static final int VerifyActivityStartRE=0;
	    public static final int VerifyActivityStartNEW=1;
	    public static int VerifyActivityStart;
	    public static String server_ip="123.56.157.221";
	    
	    /**
	     * 标记头像是否有修改，1-修改，   0-没修改
	     */
	    public static int touxiangChanged=0;
	    
	    /**
	     * 标记笔记是否发生变化 需要重新加载
	     */
	    public static boolean noteChanged = false;
}
