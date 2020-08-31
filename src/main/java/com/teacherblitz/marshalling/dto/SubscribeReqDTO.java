package com.teacherblitz.marshalling.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Netty请求实体
 * 
 * @author: <a href="mailto:teacherblitz@gmail.com">teacherblitz</a>
 * @since: 2020/8/31
 */
@Data
@Builder
public class SubscribeReqDTO implements Serializable {

    private Integer subReqId;

    private String userName;

    private String productName;
}
