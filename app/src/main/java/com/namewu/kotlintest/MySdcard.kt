package com.namewu.kotlintest

/**
 * Created by Administrator on 2017/2/11.
 */

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.text.TextUtils
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream


class MySdcard {
    private val TAG = "MySdcard"
    fun initWuSdcard(context: Context): Boolean {
        //  if (!SharePreferenceUtil.getSettingDataBoolean(context,SharePreferenceUtil.INITSDCARD_KEY)) {
        var dir: File
        dir = File(pathheadimg)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        dir = File(pathsearchtxt)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        dir = File(pathCacheBanner)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        dir = File(pathwriteimg)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        dir = File(pathCacheImage)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        SharePreferenceUtil.putSettingDataBoolean(context, SharePreferenceUtil.INITSDCARD_KEY, true)
        //  }
        return true
    }

    fun getPathwriteimg(): String {
        return pathwriteimg
    }

    fun getPathheadimg(): String {
        return pathheadimg
    }

    fun getPathsearchtxt(): String {
        return pathsearchtxt
    }

    fun savePicture(path: String, name: String, bitmap: Bitmap) {
        Log.i(TAG, "执行了" + name)
        var fos: FileOutputStream? = null
        val oos: ObjectOutputStream? = null
        var file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        file = File(path + File.separator + name)
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            Log.i(TAG, "保存一个了")
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            Log.i(TAG, "出错了" + e.toString())
            e.printStackTrace()
        } finally {

        }
    }

    fun getPicture(path: String, name: String): Bitmap? {
        val file = File(path + File.separator, name)
        Log.i(TAG, file.absolutePath)
        if (!file.exists()) {
            Log.i("WuSdcard", "不存在")
            return null
        }
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    fun saveMusic(path: String, name: String, mpsbyte: ByteArray) {
        var fos: FileOutputStream? = null
        var file = File(path)
        if (!file.exists()) {
            file.mkdirs()
            Log.i(TAG, "创建文件夹成功")
        }
        file = File(path + File.separator + name)
        try {
            if (!file.exists()) {
                file.createNewFile()
                Log.i("WuSdcard", "创建文件成功")
                fos = FileOutputStream(file)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.write(mpsbyte)
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

    }

    fun getFolderSize(file: File): Long {
        var size: Long = 0
        val files = file.listFiles() ?: return 0
        for (i in files.indices) {
            if (files[i].isDirectory) {
                size = size + getFolderSize(files[i])
            } else {
                size = size + files[i].length()
            }
            L.i(TAG, size.toString() + "size")
        }
        return size
    }

    /**
     * 删除指定目录下文件及目录
     * @param deleteThisPath
     * *
     * @param
     * *
     * @return
     */
    fun deleteFolderFile(filePath: String, deleteThisPath: Boolean): Boolean {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                val file = File(filePath)
                if (file.isDirectory) {// 处理目录
                    val files = file.listFiles()
                    for (i in files.indices) {
                        deleteFolderFile(files[i].absolutePath, true)
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory) {// 如果是文件，删除
                        file.delete()
                    } else {// 目录
                        if (file.listFiles().size == 0) {// 目录下没有文件或者目录，删除
                            file.delete()
                        }
                    }
                }
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
        return true
    }

    companion object {
        private val path = Environment.getExternalStorageDirectory().toString() + File.separator + "com.namewu.booksalesystem"
        private var mysdcard: MySdcard? = null
        val pathwriteimg = path + File.separator + "writeimg"
        val pathheadimg = path + File.separator + "headimg"
        val pathsearchtxt = path + File.separator + "data"
        val pathCache = path + File.separator + "cache"
        val pathCacheBanner = path + File.separator + "cache" + File.separator + "banner"
        val pathCacheImage = path + File.separator + "cache" + File.separator + "image"
        val pathabout = path + File.separator + "data" + File.separator + "about"
        fun getMysdcard(): MySdcard {
            if (mysdcard == null) {
                mysdcard = MySdcard()
            }
            return mysdcard
        }
    }
}
