package org.ppzhu.pojo;

import lombok.Data;

@Data
public class User {

  private String uid;
  private String uname;
  private String upwd;
  private String vcode;
  private Long time;
  private String email;
  private String phone;

}
