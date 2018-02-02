package com.milk.jump

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object AdbCaller {

    private var adbPath = ADB_PATH
    private var screenshotLocation = SCREENSHOT_LOCATION

    fun setAdbPath(adbPath: String) {
        AdbCaller.adbPath = adbPath
    }

    fun setScreenshotLocation(screenshotLocation: String) {
        AdbCaller.screenshotLocation = screenshotLocation
    }

    /**
     * 调用adb长按屏幕
     *
     * @param timeMilli
     */
    fun longPress(timeMilli: Double) {
         val cmd = adbPath + " shell input touchscreen swipe 170 187 170 187 " + timeMilli.toInt();
        execCmd(cmd);
    }

    /**
     * 改进的截图方法<br></br>
     */
    fun printScreen() {
        // 截屏命令
        val screencapCmd = adbPath + " shell /system/bin/screencap -p /sdcard/screenshot.png"
        execCmd(screencapCmd);

        // 图片拷贝命令
        val copyCmd = adbPath + " pull /sdcard/screenshot.png " + screenshotLocation;
        execCmd(copyCmd);
    }

    fun execCmd(cmd: String){
        try {
            val p1 = Runtime.getRuntime().exec(cmd)
            val bufferedReader = BufferedReader(InputStreamReader(p1.errorStream))
            var s = bufferedReader.readLine()
            while (s != null) {
                s = bufferedReader.readLine()
                println(s)
            }
            p1.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
