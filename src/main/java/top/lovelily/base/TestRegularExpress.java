package top.lovelily.base;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegularExpress {
    public static String getContent(Map<String, String> params, String content) {
        Pattern p = Pattern.compile("(\\$\\{)([\\w]+)(\\})");
        Matcher m = p.matcher(content);
        StringBuffer sr = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            m.appendReplacement(sr, params.get(group.substring(2, group.length() - 1)));
        }
        m.appendTail(sr);
        return sr.toString();
    }

    public static void main(String[] args) {
        String content = "尊敬的用户，你的车辆 ${} 保险即将到期，请及时续保！！";
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("plateNum", "沪ADS8888");
        String resolvedStr = getContent(paramMap, content);
        System.out.println(resolvedStr);

        Map<String, String> params = new HashMap<>();
        params.put("first", "平安信鸽取消");
        params.put("keyword1", "13555555555");
        params.put("keyword2", "20230614");
        params.put("remark", resolvedStr);

        JSONObject wxparams = new JSONObject();
        for (String key : params.keySet()) {
            String value = params.get(key);
            JSONObject basics = new JSONObject();
            basics.put("value", value);
            wxparams.put(key, basics);
        }
        System.out.println(wxparams.toJSONString());

    }
}
