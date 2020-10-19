package com.cupshe.ak.base;

import com.cupshe.ak.net.AddressUtils;

import java.util.Random;

/**
 * BaseGenerator
 *
 * @author zxy
 */
public class BaseGenerator {

    private static final String ROUND = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final IdGenerators ID_GENERATORS = new IdGenerators(1, AddressUtils.getLocalIp4AddressMod32());

    public static String generateRandomString(int count) {
        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            int idx = new Random().nextInt(ROUND.length());
            result[i] = ROUND.charAt(idx);
        }

        return new String(result);
    }

    public static long generateGlobalId() {
        return ID_GENERATORS.nextId();
    }

    /*** 全局唯一 ID 生成器 */
    private static class IdGenerators {

        private static final long START_TIMESTAMP = 1584843269315L;

        /*** 序列号占用的位数 */
        private static final long SEQUENCE_BIT = 12L;

        /*** 机器标识占用的位数 */
        private static final long MACHINE_BIT = 5L;

        /*** 数据中心占用的位数 */
        private static final long DATA_CENTER_BIT = 5L;

        /*** 最大支持的数据中心数量: 31 */
        private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

        /*** 最大支持的机器数量: 31 */
        private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

        /*** 最大支持的 12 位存储序列号: 4095 */
        private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

        /*** 机器标识较序列号的偏移量 */
        private static final long MACHINE_LEFT = SEQUENCE_BIT;

        /*** 数据中心较序列号的偏移量 */
        private static final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

        /*** 时间戳较序列号的偏移量 */
        private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

        /*** 数据中心 */
        private long dataCenterId;

        /*** 机器标识 */
        private long machineId;

        /*** 序列号 */
        private long sequence = 0L;

        /*** 上一次时间戳 */
        private static long lastTimestamp = -1L;

        private IdGenerators(long dataCenterId, long machineId) {
            if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
                throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
            } else if (machineId > MAX_MACHINE_NUM || machineId < 0) {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }

            this.dataCenterId = dataCenterId;
            this.machineId = machineId;
        }

        private synchronized long nextId() {
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
                    | machineId << MACHINE_LEFT
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
