package com.gtriangle.admin.db.saas;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 分布式ID生产器
 * 对系统时间的依赖性非常强，需关闭ntp的时间同步功能。当检测到ntp时间调整后，将会拒绝分配id
 * @author Brian
 *
 */
public class IdWorker implements IIdWorker {
	
	private final long workerId;
	private final static long twepoch = 1361753741828L;
	private long sequence = 0L;
	// 机器标识位数
	private final static long workerIdBits = 5L;
	// 机器ID最大值 
	public final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 毫秒内自增位 
	private final static long sequenceBits = 10L;
	// 机器ID偏左移位数
	private final static long workerIdShift = sequenceBits;
	//时间毫秒左移17位
	private final static long timestampLeftShift = sequenceBits + workerIdBits;
	public final static long sequenceMask = -1L ^ -1L << sequenceBits;
	private long lastTimestamp = -1L;

	public IdWorker(final long workerId) {
		super();
		if (workerId > this.maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"worker Id can't be greater than %d or less than 0",
					this.maxWorkerId));
		}
		this.workerId = workerId;
	}

	public synchronized long nextId() {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			this.sequence = (this.sequence + 1) & this.sequenceMask;
			if (this.sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒 
				System.out.println("###########" + sequenceMask);
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			try {
				throw new Exception(
						String.format(
								"Clock moved backwards.  Refusing to generate id for %d milliseconds",
								this.lastTimestamp - timestamp));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID 
		long nextId = ((timestamp - twepoch << timestampLeftShift))
				| (this.workerId << this.workerIdShift) | (this.sequence);
		//  System.out.println("timestamp:" + timestamp + ",timestampLeftShift:"
		//		    + timestampLeftShift + ",nextId:" + nextId + ",workerId:"
		//		    + workerId + ",sequence:" + sequence);
		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}


	public static void main(String[] args){
		
		final IdWorker worker2 = new IdWorker(2);
		final CyclicBarrier cdl = new CyclicBarrier(100); 
		final Map mm = new HashMap();
		for(int i = 0; i < 100; i++){ 
            new Thread(new Runnable() { 
                @Override 
                public void run() { 
                try { 
                    cdl.await(); 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } catch (BrokenBarrierException e) { 
                    e.printStackTrace(); 
                }
                long id = worker2.nextId();
                mm.put(id, "123");
                //if(mm.containsKey(id)) System.out.println("#############en##################");
                System.out.println(worker2.nextId());} 
             }).start(); 
        }
	
		try { 
            TimeUnit.SECONDS.sleep(5); 
        } catch (InterruptedException e) { 
           e.printStackTrace(); 
        }
		//0000000000000000000000000000000000000000000000000000100000000000
		//long ll = 0000000000000000000000000000000000000000000000000000100000000000;
		System.out.println(worker2.workerId << worker2.workerIdShift);
		System.out.println(worker2.maxWorkerId);
	}
}
