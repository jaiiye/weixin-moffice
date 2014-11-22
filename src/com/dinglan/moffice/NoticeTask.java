package com.dinglan.moffice;

import java.util.Date;

public class NoticeTask implements Runnable {
    public void run() {
        System.out.println("数据备份任务启动");
        System.out.println(new Date());
        System.out.println("数据备份任务完成");
    }
}
