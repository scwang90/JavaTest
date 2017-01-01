package com.jsontester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.jsontester.model.Group;
import com.jsontester.util.AfJsoner;
import com.jsontester.util.JacksonUtil;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntData;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * GroupTester
 * Created by SCWANG on 2016/5/23.
 */
public class GroupTester {

    public static class Group {
        public String id;
        public String name;
        public Group(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    public static class Friend {
        public String id;
        public String name;
    }

    @Test
    public void array() {
        String json = "[\n" +
                "\"0dfd11e9-a358-47ca-8fb9-9d16d3eb6772\",\n" +
                "\"20000000-0000-0000-0000-000000000001\",\n" +
                "\"29b37b4f-9a2e-4a0f-9954-d15a986f80f9\"\n" +
                "]";
        Gson gson = new Gson();
        System.out.println(gson.toJson(Arrays.asList(gson.fromJson(json, String[].class))));
    }

    @Test
    public void cloneList() throws IOException {
        List<Group> list = new ArrayList<Group>();
        list.add(new Group("1", "scwang"));
        list.add(new Group("2", "feng"));
        List<Friend> friends = AfJsoner.cloneList(list, Friend.class);
        System.out.println(AfJsoner.toJson(friends));
    }

    @Test
    public void fromJson() throws IOException {
        Gson gson = new Gson();
        String json = "{\"Id\":\"G120\",\"Name\":\"新建讨论组\",\"Desc\":\"超级管理员2016-5-23 创建\",\"CreateTime\":1463991852360,\"CreateUserId\":\"20000000-0000-0000-0000-000000000001\"}";
        System.out.println(gson.fromJson(json, Group.class).toString());
        Map<String, Object> map = gson.fromJson(json, Map.class);
        Map<String, Object> minimap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            minimap.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        json = gson.toJson(minimap);
        System.out.println(gson.fromJson(json, Group.class).toString());
        System.out.println(JacksonUtil.toObject(json, Group.class).toString());

    }

    @Test
    public void map() throws JsonProcessingException {
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("TaskNo", "task.TaskNo");
        param.put("TestNo", "record.TestNo");
        param.put("ItemCode", "task.ItemCode");
        param.put("DevieMakerCode", "task.Device.MakerCode");
        param.put("DevieNo", "task.Device.Id");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Signature", "");
        params.put("Param", param);
        System.out.println(JacksonUtil.toJson(params));
    }

}
