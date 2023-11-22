package org.luoyuhan.learn.confirm;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

public class ClassRef {
    public static void main(String[] args) {
        // 错误使用
        HosHospitalDO hospitalDO = new HosHospitalDO();
        JSONObject object = new JSONObject();
        for (int i = 0;i<3;i++){
            hospitalDO.setHosName("name"+i);
            object.put(""+i,hospitalDO);
        }
        System.out.println(object);
    }

    @Data
    static class HosHospitalDO {
        String hosName;
    }
}
