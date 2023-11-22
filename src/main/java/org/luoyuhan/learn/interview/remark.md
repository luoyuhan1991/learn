1、缓存
    1.1 Redis缓存淘汰机制
        noeviction： 不删除，直接返回报错信息。
        allkeys-lru：移除最久未使用（使用频率最少）使用的key。推荐使用这种。
        volatile-lru：在设置了过期时间key中，移除最久未使用的key。
        allkeys-random：随机移除某个key。
        volatile-random：在设置了过期时间的key中，随机移除某个key。
        volatile-ttl： 在设置了过期时间的key中，移除准备过期的key。
        allkeys-lfu：移除最近最少使用的key。
        volatile-lfu：在设置了过期时间的key中，移除最近最少使用的key。
