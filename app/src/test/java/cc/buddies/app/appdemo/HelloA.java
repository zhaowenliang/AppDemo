package cc.buddies.app.appdemo;

public class HelloA {

    public HelloA() {
        System.out.println("HelloA构造");
    }

    {
        System.out.println("HelloA 方法块");
    }

    static {
        System.out.println("HelloA static");
    }

}
