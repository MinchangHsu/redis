package com.caster.redis.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author: caster
 * @date: 2020/9/22
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsertUserReq {
	private int userId;
	private String userName;
	private String userPassword;
}
