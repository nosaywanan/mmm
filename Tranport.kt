package com.linkee.disni.databinding.watcher

import android.os.Handler
import android.os.Message
import java.util.concurrent.ConcurrentHashMap

class Transport:Handler(){
     val TAG="Transport"
    private val taskMap= ConcurrentHashMap<Any, WatchDog.WatchDogTask<Any>>()
    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        msg?.let { _ ->
            taskMap[msg.obj]?.subscribe(msg.obj)
            taskMap.remove(msg.obj)
        }
    }
    public fun transport(task: WatchDog.WatchDogTask<Any>) {
        val message=obtainMessage()
        message.obj =task.observe()
        taskMap[message.obj]=task
        //Log.e(TAG,"transport task observe result is :${message.obj}")
        sendMessage(message)
    }
}