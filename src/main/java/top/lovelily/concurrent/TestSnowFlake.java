package top.lovelily.concurrent;

import org.joda.time.DateTime;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Desc: TestSnowFlake
 * Author: xuhe
 * Date: 2018/11/13 5:57 PM
 * Version: 1.0
 */
public class TestSnowFlake {

    public static final long INVALID_ID = 0L;
    static volatile AtomicInteger initialSn = new AtomicInteger(0); // volatile 多余， 内部 value 已经申明为volatile
    static final AtomicInteger STEPPER = new AtomicInteger(1);
    static final int STEP_BITS = 22;
    static final int STEP_ID_MASK = (1 << STEP_BITS) - 1;
    static final long SKIP_TIME = DateTime.parse("2018-11-13T04:44:44.444+08:00").getMillis();

    public static long generateId() {
        long id = (System.currentTimeMillis() - SKIP_TIME) << STEP_BITS;
        id |= STEPPER.incrementAndGet() & STEP_ID_MASK;
        return id;
    }


    public static void main(String[] args) {
        System.out.println(TestSnowFlake.generateId());
        System.out.println(createRecordId(20,0));
        // 右移
        System.out.println(1 << 19);
        // 按位与
        System.out.println(1 & 5);

        Random random = new Random();
        System.out.println(random.nextInt(999999));


    }


    private static AtomicInteger recordIdSeq = new AtomicInteger(0);

    public static long createRecordId(int shardId) {
        return createRecordId(5, shardId);
    }

    public static long createRecordId() {
        return createRecordId(0);
    }

    public static long createRecordId(int shardIdBits, int shardId) {
        int maxSubIdBits = 22;
        int maxSeqIdBits = maxSubIdBits - shardIdBits;
        int maxSeqId     = (int) Math.pow(2, maxSeqIdBits);
        int seqId        = recordIdSeq.incrementAndGet() % maxSeqId;
        long nowMillis   = System.currentTimeMillis();
        long kyxEpoch    = 1259268284444L; // 2009-11-27T04:44:44.444+08:00

        long result = (nowMillis - kyxEpoch) << maxSubIdBits;
        result |= (shardId << maxSeqIdBits);
        result |= seqId;
        return result;
    }

    public static String generateSn(String prefix) {

//        int sn = initialSn.get();
//        if (sn == 0) {
//            // load from db
//            int dbSn = 23;
//            initialSn = new AtomicInteger(dbSn);
//        }

        int sn = initialSn.getAndIncrement();

        StringBuffer stringBuffer = new StringBuffer(prefix).append("000000");
        // B000000
        int numLen = String.valueOf(sn).length();
        stringBuffer.replace( 7 - numLen, 7 , String.valueOf(sn));

        return stringBuffer.toString();
    }

}
