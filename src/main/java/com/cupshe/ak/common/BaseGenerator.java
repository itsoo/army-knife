package com.cupshe.ak.common;

import com.cupshe.ak.net.AddressUtils;
import com.cupshe.ak.text.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * BaseGenerator
 *
 * @author zxy
 */
public abstract class BaseGenerator {

    public abstract Object next();

    public static class RandomLetterGenerator extends BaseGenerator {

        private static final String LETTER_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        private final int count;

        private RandomLetterGenerator(int count) {
            this.count = count;
        }

        public static RandomLetterGenerator of(int count) {
            return new RandomLetterGenerator(count);
        }

        @Override
        public String next() {
            char[] result = new char[count];
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < count; i++) {
                int index = random.nextInt(LETTER_POOL.length());
                result[i] = LETTER_POOL.charAt(index);
            }

            return new String(result);
        }
    }

    public static class PrimaryKeyGenerator extends BaseGenerator {

        private static final long START_TIMESTAMP = 1584843269315L;

        /*** 序列号占用的位数 */
        private static final long SEQUENCE_BIT = 12L;

        /*** 最大支持的 12 位存储序列号: 4095 */
        private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

        /*** 数据中心占用的位数 */
        private static final long DATA_CENTER_BIT = 5L;

        /*** 最大支持的数据中心数量: 31 */
        private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

        /*** 机器标识占用的位数 */
        private static final long MACHINE_BIT = 5L;

        /*** 最大支持的机器数量: 31 */
        private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

        private static final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

        private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

        /*** 数据中心 */
        private final long dataCenterId;

        /*** 机器标识 */
        private final long machineId;

        private long sequence = 0L;

        private static long lastTimestamp = -1L;

        private PrimaryKeyGenerator(long dataCenterId, long machineId) {
            String messageTemplate = "{} can't be greater than {} or less than 0";
            if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
                throw new IllegalArgumentException(
                        StringUtils.getFormatString(messageTemplate, "dataCenterId", MAX_DATA_CENTER_NUM));
            }
            if (machineId > MAX_MACHINE_NUM || machineId < 0) {
                throw new IllegalArgumentException(
                        StringUtils.getFormatString(messageTemplate, "machineId", MAX_MACHINE_NUM));
            }

            this.dataCenterId = dataCenterId;
            this.machineId = machineId;
        }

        public static PrimaryKeyGenerator of(long dataCenterId) {
            return of(dataCenterId, AddressUtils.getLocalIp4AddressMod32());
        }

        public static PrimaryKeyGenerator of(long dataCenterId, long machineId) {
            return new PrimaryKeyGenerator(dataCenterId, machineId);
        }

        @Override
        public synchronized Long next() {
            long currTimestamp = getNewTimestamp();
            if (currTimestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards. Refusing to generate id");
            } else if (currTimestamp == lastTimestamp) {
                // 相同毫秒内，序列号自增
                sequence = (sequence + 1) & MAX_SEQUENCE;
                if (sequence == 0L) {
                    currTimestamp = getNextMill();
                }
            } else {
                sequence = 0L;
            }

            lastTimestamp = currTimestamp;
            return (currTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                    | dataCenterId << DATA_CENTER_LEFT
                    | machineId << SEQUENCE_BIT
                    | sequence;
        }

        private static long getNewTimestamp() {
            return System.currentTimeMillis();
        }

        private static long getNextMill() {
            long mill = getNewTimestamp();
            while (mill <= lastTimestamp) {
                mill = getNewTimestamp();
            }

            return mill;
        }
    }
}
