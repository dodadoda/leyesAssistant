package com.leyes.leyesassistant.common;

import com.hehao.hehaolibrary.common.BaseApplication;

/**
 * @author Hehao
 * @time 2015/12/24
 */
public class Application extends BaseApplication {

    private Application app;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        /**初始化通信框架*/
        initHttp();
    }

    private void initHttp() {

    }

}
