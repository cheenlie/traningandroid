package com.pmkebiao.dao;
import java.io.Serializable;

public class UserInfo implements Serializable
{
    private int id;
    private String phone_no;
    private String password;
    private int sex;
    private String name;
    private String img_user_tx;
    private String location;

    /**
     * 
     * @param id
     *            ID
     * @param phone_no
     *            电话号
     * @param password
     *            密码
     * @param sex
     *            性别（0-男，1-女）
     * @param img_user_tx
     *            头像地址
     * @param name
     *            昵称
     * @param last_login_time
     *            最后登录时间
     */
    public UserInfo(int id, String phone_no, String password, int sex, String img_user_tx, String name, String location)
    {
	super();
	this.id = id;
	this.phone_no = phone_no;
	this.password = password;
	this.sex = sex;
	this.name = name;
	this.img_user_tx = img_user_tx;
	this.location = location;

    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public String getPhone_no()
    {
	return phone_no;
    }

    public void setPhone_no(String phone_no)
    {
	this.phone_no = phone_no;
    }

    public String getPassword()
    {
	return password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

    public int getSex()
    {
	return sex;
    }

    public void setSex(int sex)
    {
	this.sex = sex;
    }

    public String getImg_user_tx()
    {
	return img_user_tx;
    }

    public void setImg_user_tx(String img_user_tx)
    {
	this.img_user_tx = img_user_tx;
    }

    public String getlocation()
    {
	return location;
    }

    public void setlocation(String last_login_time)
    {
	this.location = last_login_time;
    }

}
