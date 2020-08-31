package com.teacherblitz.marshalling.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 * 
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/31
 */
@Data
@Builder
public class SubscribeRespDTO implements Serializable {

    private Integer subReqId;

    private Integer status;

    private String desc;
}
