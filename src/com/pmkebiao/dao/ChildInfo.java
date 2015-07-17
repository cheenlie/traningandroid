package com.pmkebiao.dao;

import java.io.Serializable;

public class ChildInfo implements Serializable
{
    private int id;
    private String cid;
    private int parent_id;
    private String name;
    private String img_tx;
    private int sex;
    private int level;

   /**
    * 
    * @param id
    * @param cid
    * @param parent_id
    * @param name 孩子昵称
    * @param img_tx
    * @param sex
    * @param level
    */
    public ChildInfo(int id, String cid, int parent_id, String name, String img_tx, int sex, int level)
    {
	this.cid = cid;
	this.id = id;
	this.parent_id = parent_id;
	this.name = name;
	this.img_tx = img_tx;
	this.sex = sex;
	this.level = level;
    }

    public String getCid()
    {
	return cid;
    }

    public void setCid(String cid)
    {
	this.cid = cid;
    }

    public int getSex()
    {
	return sex;
    }

    public void setSex(int sex)
    {
	this.sex = sex;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public int getParent_id()
    {
	return parent_id;
    }

    public void setParent_id(int parent_id)
    {
	this.parent_id = parent_id;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getImg_tx()
    {
	return img_tx;
    }

    public void setImg_tx(String img_tx)
    {
	this.img_tx = img_tx;
    }

    /*
     * public int getChild_no() { return child_no; }
     * 
     * public void setChild_no(int child_no) { this.child_no = child_no; }
     */
    public int getLevel()
    {
	return level;
    }

    public void setLevel(int level)
    {
	this.level = level;
    }

}
