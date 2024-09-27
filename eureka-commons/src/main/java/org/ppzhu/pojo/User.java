package org.ppzhu.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

  private String uid;
  private String uname;
  private String upwd;
  private String vcode;
  private Long time;
  private String email;
  private String phone;

}
