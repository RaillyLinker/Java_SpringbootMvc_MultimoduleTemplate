package com.raillylinker.module_data_redis;

import com.raillylinker.module_data_redis.redis_map_components.redis1_main.Redis1_Lock_Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModuleTest {
    @Autowired
    Redis1_Lock_Test redis1LockTest;

    @Test
    void tryRedisLockAndUnlock() {
        // 첫 번째 tryLock 테스트 (락을 획득)
        String result1 = redis1LockTest.tryLock(1000L);
        assertNotNull(result1, "첫 번째 tryLock은 UUID를 반환해야 합니다.");

        // 두 번째 tryLock 테스트 (락을 얻지 못함)
        String result2 = redis1LockTest.tryLock(1000L);
        assertNull(result2, "두 번째 tryLock은 null을 반환해야 합니다.");

        // 락 해제 호출 (unlock)
        redis1LockTest.unlock(result1);

        // 다시 tryLock 호출 후 성공해야 함
        String result3 = redis1LockTest.tryLock(1000L);
        assertNotNull(result3, "unlock 후에는 다시 tryLock이 성공해야 합니다.");

        // 마지막으로 락 해제
        redis1LockTest.unlock(result3);
    }
}
