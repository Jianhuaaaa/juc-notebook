package com.jsun.threads.startthreads;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class TestThread2 extends Thread {
    private String url;
    private String name;

    public TestThread2(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("文件下载成功： " + name);
    }

    public static void main(String[] args) {
        TestThread2 t1 = new TestThread2("https://ts1.tc.mm.bing.net/th/id/R-C.4f2863129fa695e2d633efa69f66d24a?rik=HDCY2Zk1%2fYhp5Q&riu=http%3a%2f%2fimg95.699pic.com%2fphoto%2f40018%2f6447.jpg_wh860.jpg&ehk=jA0qDYe3MiCLDLo1h9MAZFrBxEuKpj%2f32eNNC2NwDTQ%3d&risl=&pid=ImgRaw&r=0", "src/main/resources/" + UUID.randomUUID() + "-01.jpg");
        TestThread2 t2 = new TestThread2("https://c-ssl.duitang.com/uploads/item/202006/15/20200615201826_mtJsX.jpeg", "src/main/resources/" + UUID.randomUUID() + "-02.jpg");
        TestThread2 t3 = new TestThread2("https://bpic.588ku.com/element_origin_min_pic/19/03/06/28f689f2bbb2e0e2fb10857d46385986.jpg", "src/main/resources/" + UUID.randomUUID() + "-03.jpg");

        t1.start();
        t2.start();
        t3.start();
    }
}

class WebDownloader {
    public void downloader(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            System.err.println("IO异常， downloader方法出现异常。");
        }
    }
}
