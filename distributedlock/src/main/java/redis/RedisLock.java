package redis;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Collections;

public class RedisLock {


    Jedis jedis;

    String lockKeySuffix = "_redis_lock";

    public RedisLock() {
        jedis = new Jedis("localhost", 6379);
    }

    public boolean lock(String biz, String requiredId, int expiredTime) {
        String lockKey = biz + lockKeySuffix;
        String result = jedis.set(lockKey, requiredId, "NX", "EX", expiredTime);
        if("OK".equals(result)) {
            return true;
        }
        return false;
    }

    public boolean unlock(String biz, String requiredId) {
        String lockKey = biz + lockKeySuffix;
        //lua 脚本
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Arrays.asList(lockKey), Arrays.asList(requiredId));
        if(result.equals(1L)) {
            return true;
        }
        return false;
    }

}
