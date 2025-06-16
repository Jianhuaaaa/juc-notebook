package juc.concurrent.programming.single;

public class HungryInstance {
    // 可能会浪费空间
    private byte[] data = new byte[1024 * 1024];
    private byte[] data2 = new byte[1024 * 1024];
    private byte[] data3 = new byte[1024 * 1024];

    private HungryInstance() {
    }

    private final static HungryInstance HUNERY = new HungryInstance();

    public static HungryInstance getInstance() {
        return HUNERY;
    }
}
