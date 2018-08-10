package memcache;

import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by mazhuang on 2017/5/30.
 */
public class MemCache {

    private MemcachedClient mMcc;
    private static final int sExpire = 60*15; // 缓存过期时间，in seconds
    private static final int sQueryTimeout = 5; // 单次从 memcache 查询的超时时间，in seconds

    private MemCache() {
        try {
            // TODO: 2017/5/31 这里的 ip 和端口要改为安装 memcache 服务的 ip 和端口，如果在本机安装可以不改，在别的机器安装性能更好
            mMcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class InstanceHolder {
        private static MemCache sInstance = new MemCache();
    }

    /**
     * 获取 MemCache 实例
     * 实际可以考虑使用连接池，不每个 session 都新建与 memcache 服务的连接
     * @return MemCache 实例
     */
    public static MemCache getInstance() {
        return InstanceHolder.sInstance;
    }

    public static void releaseInstance() {
        getInstance().mMcc.shutdown();
    }

    /**
     * 设置缓存
     * @param key 缓存 key
     * @param value 缓存 value
     */
    public void set(String key, Object value) {
        mMcc.add(key, sExpire, value);
    }

    /**
     * 当增、删、改数据库，导致影响展示给一部分用户的结果时调用，删除这些用户对应的缓存
     * @param userIds 受影响的用户 ID（一个或多个）
     */
    public void delete(List<String> userIds) {
        for (int i = 0; i < userIds.size(); i++) {
            delete(userIds.get(i));
        }
    }

    /**
     * delete(List<String> userIds) 的单数版
     * @param userId
     */
    public void delete(String userId) {
        mMcc.delete(userId);
    }

    /**
     * 查询某用户对应的展示结果
     * @param userId 用户 ID
     * @return 结果
     */
    public Object query(String userId) {
        Object result = null;
        Future<Object> future = mMcc.asyncGet(userId);
        try {
            result = future.get(sQueryTimeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(true);
        }

        if (result == null) {
            // TODO: 2017/5/31 像以前一样查询数据库，并将结果设置进 memcache，下面这行查询数据库的要改为实际代码
            // result = database.get(userId);
            // set(userId, result);
        }
        return result;
    }
}
