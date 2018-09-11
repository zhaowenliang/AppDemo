package cc.buddies.app.appdemo;

public class HelloB extends HelloA {

    public HelloB() {
        System.out.println("HelloB构造");
    }

    {
        System.out.println("HelloB 方法块");
    }

    static {
        System.out.println("HelloB static");
    }

}
