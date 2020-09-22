package com.caster.redis.runner;

import com.caster.redis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author: caster
 * @date: 2020/8/27
 */
@Component
@RequiredArgsConstructor
public class InitRedisConnRunner implements ApplicationRunner {

	final UserService userService;

	/**
	 * Redis connection 再 server 啟動時,
	 * 並不會像db 連線一樣一開始就建立好連線池,
	 * 所以考慮到建立連線池時會花一些時間,
	 * 根據不同網路環境時間差會有所落差,
	 * 所以在啟動完server 時可先觸發Redis 讓它去建立連線池,
	 * 以利後續操作時更加順利.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		userService.deleteUserById(0);
		System.out.println("init Redis connection ...");
	}

}
