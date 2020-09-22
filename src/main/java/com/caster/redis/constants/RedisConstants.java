package com.caster.redis.constants;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RedisConstants {

	public static final String USER_INFO = "userInfo";
	public static final String ALL_USERS = "allUsers";

	public static final String CACHE_NAME_USER = "userCache";

	public static final List<CacheName> list = Collections.unmodifiableList(
			new ArrayList<CacheName>() {{
				add(new CacheName(CACHE_NAME_USER, true, 86400));
			}});

	@Data
	public static class CacheName {
		private String name;
		private boolean effective;
		private int expireTime;

		public CacheName(String name, boolean effective) {
			this.name = name;
			this.effective = effective;
			this.expireTime = 3600;
		}

		public CacheName(String name, boolean effective, int expireTime) {
			this.name = name;
			this.effective = effective;
			this.expireTime = expireTime;
		}
	}
}
