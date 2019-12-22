package xyz.intellij.player.util.entity;

import java.io.IOException;

import xyz.intellij.player.R;

public class Test {
    //测试接口
    public static void main(String[] args) throws IOException {
        Repo s = new Repo();
        for (StreamEntity t : s.getall()){
            System.out.println(t.getStreamName());
        }
        RateEntityPK a = new RateEntityPK();
        a.setUserId(1000);
        a.setStreamId(1);
        System.out.println(s.getrate(a).hashCode());
        //尝试修改
        RateEntity tt = new RateEntity();
        tt.setStreamId(1);
        tt.setUserId(1000);
        tt.setqRate(3.2);
        tt.setcRate(6.5);
        s.saverate(tt);
        System.out.println(s.getrate(a).hashCode());
    }
}
