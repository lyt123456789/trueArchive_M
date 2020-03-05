package cn.com.trueway.base.util;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Trueway on 2017/2/22.
 */

public class SpinLock {

    AtomicReference<Thread> owner=new AtomicReference<Thread>();

    public void lock(){
        Thread cur=Thread.currentThread();
        while(!owner.compareAndSet(null,cur)){

        }
    }

    public void unlock(){
        Thread cur=Thread.currentThread();
        owner.compareAndSet(cur,null);
    }

    public void clear() {
//        owner=null;
    }
}
