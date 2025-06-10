package com.jsun.threads.startthreads;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.*;

public class TestCallable implements Callable<Boolean> {

    private String url;
    private String name;

    public TestCallable(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call() {
        WebDownload webDownload = new WebDownload();
        webDownload.download(url, name);
        System.out.println("文件下载成功： " + name);
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestCallable t1 = new TestCallable("https://ts1.tc.mm.bing.net/th/id/R-C.4f2863129fa695e2d633efa69f66d24a?rik=HDCY2Zk1%2fYhp5Q&riu=http%3a%2f%2fimg95.699pic.com%2fphoto%2f40018%2f6447.jpg_wh860.jpg&ehk=jA0qDYe3MiCLDLo1h9MAZFrBxEuKpj%2f32eNNC2NwDTQ%3d&risl=&pid=ImgRaw&r=0", "src/main/resources/" + UUID.randomUUID() + "-01.jpg");
        TestCallable t2 = new TestCallable("https://c-ssl.duitang.com/uploads/item/202006/15/20200615201826_mtJsX.jpeg", "src/main/resources/" + UUID.randomUUID() + "-02.jpg");
        TestCallable t3 = new TestCallable("https://bpic.588ku.com/element_origin_min_pic/19/03/06/28f689f2bbb2e0e2fb10857d46385986.jpg", "src/main/resources/" + UUID.randomUUID() + "-03.jpg");

        // 创建执行服务：
        ExecutorService service = Executors.newFixedThreadPool(3);
        // 提交执行：
        Future<Boolean> submit1 = service.submit(t1);
        Future<Boolean> submit2 = service.submit(t2);
        Future<Boolean> submit3 = service.submit(t3);
        // 获取结果：
        boolean result1 = submit1.get();
        boolean result2 = submit2.get();
        boolean result3 = submit3.get();
        // 关闭服务：
        service.shutdownNow();
    }
}

class WebDownload {
    public void download(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            System.err.println("IO异常， downloader方法出现异常。");
        }
    }
}