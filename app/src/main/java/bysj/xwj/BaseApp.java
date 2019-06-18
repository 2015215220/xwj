package bysj.xwj;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by Administrator on 2019/4/18.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //程序入口初始化
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5a504a51");

    }
}
