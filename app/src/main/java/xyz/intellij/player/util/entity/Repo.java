package xyz.intellij.player.util.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Repo {
    //后台uri
    private String bsse="https://www.intellij.xyz/z/";
    //登录
    private String login="user/login";
    //注册
    private String register = "user/register";
    //视频流全部
    private String streamall = "stream/all";
    //视频流添加
    private String streamadd = "stream/add";
    //视频评分
    private String rateget = "rate/getone";
    //添加，修改评分
    private String rateadd = "rate/set";
    private OkHttp server;
    public Repo(){
        this.server = new OkHttp();
    }
    //登录方法
    public UserEntity login(UserEntity userEntity) throws IOException {
        ObjectMapper objm = new ObjectMapper();
        String res = server.post(this.bsse+this.login,objm.writeValueAsString(userEntity));
        JSONObject sss = (JSONObject) JSON.parse(res);
        JSONObject data = (JSONObject) sss.get("user");
        if (sss.getString("msg").equals("登陆成功")){
            UserEntity user = objm.readValue(data.toString(),new TypeReference<UserEntity>(){});
            return user;
        }
        return null;
    }
    //注册
    public UserEntity register(UserEntity userEntity) throws IOException {
        ObjectMapper objm = new ObjectMapper();
        String res = server.post(this.bsse+this.register,objm.writeValueAsString(userEntity));
        JSONObject sss = (JSONObject) JSON.parse(res);
        JSONObject data = (JSONObject) sss.get("user");
        if (sss.getString("msg").equals("注册成功")){
            UserEntity user = objm.readValue(data.toString(),new TypeReference<UserEntity>(){});
            return user;
        }
        return null;
    }
    //获取视频列表
    public List<StreamEntity> getall() throws IOException {
        ObjectMapper objm = new ObjectMapper();
        String res = server.post(this.bsse+this.streamall,new JSONObject().toString());
        List<StreamEntity> sss = objm.readValue(res,new TypeReference<List<StreamEntity>>(){});
        Collections.sort(sss, new Comparator<StreamEntity>() {
            @Override
            public int compare(StreamEntity o1, StreamEntity o2) {
                double a;
                double b;
                if (o1.getStreamQrate()!=null){
                    a=o1.getStreamQrate();
                }else{
                    a=0;
                }
                if (o2.getStreamQrate()!=null){
                    b=o2.getStreamQrate();
                }else{
                    b=0;
                }
                double diff = a-b;
                if (diff>0){return -1;}
                else if(diff<0){return 1;}
                return 0;
            }
        });
        return sss;
    }
    //添加视频
    public StreamEntity streamadd(StreamEntity streamEntity) throws IOException{
        ObjectMapper objm = new ObjectMapper();
        String res = server.post(this.bsse+this.streamadd,objm.writeValueAsString(streamEntity));
        StreamEntity get = objm.readValue(res,new TypeReference<StreamEntity>(){});
        if (get.equals(streamEntity)){
            return streamEntity;
        }
        return null;
    }
    //获取评分
    public RateEntity getrate(RateEntityPK rateEntityPK) throws  IOException{
        ObjectMapper objm = new ObjectMapper();
        String res = server.post(this.bsse+this.rateget,objm.writeValueAsString(rateEntityPK));
//        System.out.println(res);
        RateEntity get = objm.readValue(res,new TypeReference<RateEntity>(){});
        if (get!=null){
            return get;
        }
        return null;
    }
    //保存评分
    public RateEntity saverate(RateEntity rateEntity) throws  IOException{
        ObjectMapper objm = new ObjectMapper();
        String res = server.post(this.bsse+this.rateadd,objm.writeValueAsString(rateEntity));
//        System.out.println(objm.writeValueAsString(rateEntity));
        RateEntity get = objm.readValue(res,new TypeReference<RateEntity>(){});
        if (get!=null){
            return get;
        }
        return null;
    }
}
