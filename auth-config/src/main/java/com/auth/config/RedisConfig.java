package com.auth.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * @author MYH
 * @time 2023/04/05 下午 02:27
 */
@Configuration
public class RedisConfig {

    @Resource
    private RedisConnectionFactory factory;

    @Resource ObjectMapper objectMapper;


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // 为了开发方便一般直接使用 <String,Object>
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // value 和 hashValue 使用的序列化器
        RedisSerializer<Object> jackson2JsonRedisSerializer = redisSerializer();
        //key 和 hashKey使用的序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //设置key的序列化器
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        //设置value的序列化器
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 指定了要序列化的域，ANY：包含 private 和 public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 指定序列化的输入类型为非final修饰的
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 处理 SimpleGrantedAuthority 权限信息不能被反序列化
        objectMapper.registerModule(
                new SimpleModule()
                        .addDeserializer(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer())
        );
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    static class SimpleGrantedAuthorityDeserializer extends JsonDeserializer<SimpleGrantedAuthority> {
        @Override
        public SimpleGrantedAuthority deserialize(JsonParser p, DeserializationContext dcx) throws IOException {
            JsonNode jsonNode = p.getCodec().readTree(p);
            Iterator<JsonNode> elements = jsonNode.elements();
            while (elements.hasNext()) {
                JsonNode next = elements.next();
                JsonNode authority = next.get("authority");
                if(ObjectUtils.isEmpty(authority)){
                    continue;
                }
                return new SimpleGrantedAuthority(authority.asText());
            }
            return  null;

        }
    }
}
