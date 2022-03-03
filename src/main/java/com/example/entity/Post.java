package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-03-03
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Post implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String title;

    private String content;

    private String releasetime;

    private String type;

    private Integer click;

    private String userId;

    private String state;

    private String tag;


}
