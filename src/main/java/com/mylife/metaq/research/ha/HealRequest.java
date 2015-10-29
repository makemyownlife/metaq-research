package com.mylife.metaq.research.ha;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 执行缓存后 ,间隔 再次执行删除缓存的命令 确保 提交费用后、取消后，缓存一定删除 ，这个没有办法避免断电的问题
 * User: zhangyong
 * Date: 2015/10/28
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class HealRequest implements Delayed {

    //订单号
    private Long orderId;

    // 延后的时间戳
    private long delayTimeStamp;

    private long delay;

    public HealRequest(Long orderId, long delay) {
        this.orderId = orderId;
        this.delay = delay;
        if (delay >= 0) {
            this.delayTimeStamp = System.currentTimeMillis() + delay;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.orderId == null ? 0 : this.orderId.hashCode());
        result = prime * result + (int) (this.delay ^ this.delay >>> 32);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final HealRequest other = (HealRequest) obj;
        if (this.orderId == null) {
            if (other.orderId != null) {
                return false;
            }
        }
        if (this.delay != other.delay) {
            return false;
        }
        return true;
    }


    public long getDelay() {
        return this.delay;
    }

    @Override
    public long getDelay(final TimeUnit unit) {
        return unit.convert(this.delayTimeStamp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }


    public int compareTo(final Delayed o) {
        if (o == this) {
            return 0;
        }
        final HealRequest other = (HealRequest) o;
        final long sub = this.delayTimeStamp - other.delayTimeStamp;
        if (sub == 0) {
            return 0;
        }
        else {
            return sub < 0 ? -1 : 1;
        }
    }

    public Long getOrderId() {
        return orderId;
    }

}
