package com.namewu.kotlintest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    var string:String="Hello" //声明字符串
    val list:Array<String> = arrayOf("wuzhenyu","wangjiaqiao","zhengyu","sundongxue")

//    const val foreverstring:String="Hello_two"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    //声明函数
    fun testString(name:String):Boolean{
        int code = if(isSuccessfully) 200 else 400
        return true
    }

}
