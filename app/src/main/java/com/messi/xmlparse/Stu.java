package com.messi.xmlparse;

/**
 * Created by Messi.mo on 2017/6/6.
 */
public class Stu {
  Name name;
  String nickName;

  @Override public String toString() {
    return "Stu{" + "name=" + name + ", nickName='" + nickName + '\'' + '}';
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
   class Name{
     public Name(String name, String sex) {
       this.name = name;
       Sex = sex;
     }

     private String name;
    private String Sex;
    public String getSex() {
      return Sex;
    }

    public void setSex(String sex) {
      Sex = sex;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override public String toString() {
      return "Name{" + "name='" + name + '\'' + ", Sex='" + Sex + '\'' + '}';
    }
  }
}
