package com.caster.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.caster.redis.constants.RedisConstants;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig implements Serializable {

	/**
	 * 設置redis緩存過期時間
	 */
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
				this.getRedisCacheConfigurationWithTtl(80), // default 策略
				this.getRedisCacheConfigurationMap() // 可自定義 cache namespace 策略
		);
	}

	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		for (RedisConstants.CacheName obj : RedisConstants.list) {
			//SsoCache和BasicDataCache進行過期時間配置
			if (obj.isEffective())
				redisCacheConfigurationMap.put(obj.getName(), this.getRedisCacheConfigurationWithTtl(obj.getExpireTime()));
		}
		return redisCacheConfigurationMap;
	}

	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
		Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(new LaissezFaireSubTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
		redisSerializer.setObjectMapper(om);
		// 基本配置
		RedisCacheConfiguration defaultCacheConfiguration =
				RedisCacheConfiguration
						.defaultCacheConfig()
						// 設置key為String
						.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
						// 設置value 為自動轉Json的Object
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
						// 不緩存null
						.disableCachingNullValues()
						// 緩存數據保存時間設定
						.entryTtl(Duration.ofSeconds(seconds));

		return defaultCacheConfiguration;
	}
}
