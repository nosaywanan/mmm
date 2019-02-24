package com.linkee.disni.databinding.watcher

import android.util.Log
import java.util.concurrent.*

object WatchDog :Runnable {
    @JvmStatic val TAG="WatcherDog"
    @JvmStatic val DEF_TERMINATE_TIME=1000L
    @JvmStatic val MAX_CORE_SIZE=4
    @JvmStatic val MAX_TASK_SIZE=Short.MAX_VALUE.toInt()
//    @JvmStatic val taskQueue=ArrayBlockingQueue<WatchDogTask<Any>>(MAX_TASK_SIZE)
    @JvmStatic val taskFutureMap=ConcurrentHashMap<WatchDogTask<Any>,Future<*>>(MAX_TASK_SIZE)
//    @JvmStatic val taskAwakeTime=ConcurrentHashMap<WatchDogTask<Any>,Long>(MAX_TASK_SIZE)
    @JvmStatic val transportHandler= Transport()

    override fun run() {
//        executorService?.isShutdown?.let {
            while (true){
                if (taskFutureMap.isEmpty()){
                    Thread.sleep(DEF_TERMINATE_TIME)
                    Log.e(TAG,"run watcher no task sleep")
                }else{
                    //executorService.execute {transportHandler.transport(it as WatchDogTask<Any>)}
                    taskFutureMap.forEach { it ->
                        val future =it.value
                        if (future.isDone){
                            val task = it.key
                            transportHandler.transport(task)
                        }
                    }
                }
            }
//        }
    }

    @JvmStatic  var executorService:ExecutorService?=null
    @JvmStatic fun startWatcher(){
        (executorService ==null).let {
            executorService = Executors.newScheduledThreadPool(MAX_CORE_SIZE)
            executorService!!.submit(this)
            Log.e(TAG,"start watcher")
        }
    }
    @JvmStatic fun <T>subscribe(task: WatchDogTask<T>, awaitTime:Long= DEF_TERMINATE_TIME){
        executorService?.let {
            //taskQueue.add(task as WatchDogTask<Any>)
//            taskAwakeTime[task as WatchDogTask<Any>]=awaitTime
            val future = it.submit(task)
            taskFutureMap[task as WatchDogTask<Any>] = it.submit(task)
            Log.e(TAG,"subscribe : ${task.javaClass.canonicalName}")
        }
    }
    @JvmStatic fun <T>unsubscribe(task: WatchDogTask<T>):Boolean{
        Log.e(TAG,"unsubscribe : ${task.javaClass.canonicalName}")
        //taskAwakeTime.remove(task as WatchDogTask<Any>)
       return taskFutureMap.remove(task as WatchDogTask<Any>)!=null
    }
    @JvmStatic fun stopWatcher(){
        executorService?.let {
            Log.e(TAG,"stop watcher")
            it.shutdown()
            val timeOut = it.awaitTermination(1,TimeUnit.SECONDS)
            if (timeOut){
                val taskListAwait= executorService?.shutdownNow()
                Log.e(TAG,"stop watcher time out force shut down")
            }
        }
        taskFutureMap.clear()
        //taskAwakeTime.clear()
       // taskQueue.clear()
    }
    interface WatchDogTask<T> :Runnable{
        fun observe():T?
        fun subscribe(data: T)
        override fun run() {
            observe()
        }
    }
}


