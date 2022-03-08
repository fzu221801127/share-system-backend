package com.example.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-03-08
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class User implements Serializable {

    private static final long serialVersionUID=1L;

      private String id;

    private String nickname;

    private String birthday;

    private String signature;

    private String phoneNumber;

    private String password;

    private String collectione;

    private String state;

    private String headPortraitUrl;

    private String permission;


}
