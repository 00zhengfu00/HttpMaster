package kale.http.framework;

import com.jsontojava.JsonToJava;
import com.jsontojava.OutputOption;

import android.os.Handler;
import android.os.Message;
import android.util.SparseBooleanArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kale.http.framework.util.UrlUtil;
import kale.http.framework.util.WordUtil;

public class Presenter {

    private MainActivity mActivity;

    private List<String> paramList = new ArrayList<>();

    private Handler mHandler;

    public Presenter(MainActivity mMainActivity) {
        this.mActivity = mMainActivity;
        Handler.Callback callback = new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.obj instanceof List) {
                    mActivity.onCreateModelsResponse((List<String>) msg.obj);
                    return true;
                } else {
                    return false;
                }
            }
        };
        mHandler = new Handler(callback);
    }

    public void onActivityDestroy() {
        mActivity = null;
        mHandler.removeCallbacksAndMessages(null);
    }

    public List<String> getParamList() {
        return paramList;
    }

    /**
     * 通过url解析出param的list
     */
    public void parseUrlToListItem(String url) {
        paramList.clear();
        for (Map.Entry<String, String> entry : UrlUtil.getParams(url).entrySet()) {
            paramList.add(entry.getKey() + " = " + entry.getValue());
        }
    }

    /**
     * 更新网络请求方法体
     *
     * @param sba 被选中的默认参数列表
     */
    public String getMethodBlock(String url, SparseBooleanArray sba, boolean isPost, boolean hasModel) {
        final Map<String, String> defaultParamMap = new HashMap<>();
        for (int i = 0; i < sba.size(); i++) {
            if (sba.valueAt(i)) {
                String temp = paramList.get(sba.keyAt(i));
                defaultParamMap.put(temp.substring(0, temp.indexOf(" =")), temp.substring(temp.indexOf("= ") + 2, temp.length()));
            }
        }
        Map<String, String> originParamMap = UrlUtil.getParams(url);

        StringBuilder sb = new StringBuilder();
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (!isPost) {
            return getGetMethodBlock(url, defaultParamMap, originParamMap, sb, hasModel);
        } else {
            return getPostMethodBlock(url, defaultParamMap, originParamMap, sb, hasModel);
        }
    }

    /**
     * Post
     *
     * // 无参数
     * private void postFunction(HttpResponse<RootClass> response) {
     * mHttpRequest.doPost("url", RootClass.class, response);
     * }
     *
     * // 有参数
     * private void postFunction(String param, HttpResponse<Object> response) {
     * HashMap<String, String> map = new HashMap<>();
     * map.put("defaultKey", "defaultValue");
     * map.put("param", param);
     * mHttpRequest.doPost("url", map, null, response);
     * }
     */
    protected String getPostMethodBlock(String url, Map<String, String> defaultParamMap, Map<String, String> originParamMap,
            StringBuilder sb, boolean hasModel) {
        String modelClass = hasModel ? "RootClass.class" : "null";
        String modelType = hasModel ? "RootClass" : "Object";
        // 无参数
        if (originParamMap.size() == 0) {
            sb.append("    private void function(HttpResponse<").append(modelType).append("> response) {\n")
                    .append("        mHttpRequest.doPost(\"").append(url).append("\", ").append(modelClass).append(", response);\n")
                    .append("    }");
            return sb.toString();
        }
        // 有参数
        sb.append("    private void function(");
        for (Map.Entry<String, String> entry : originParamMap.entrySet()) {
            if (defaultParamMap.get(entry.getKey()) == null) {
                sb.append("String ").append(WordUtil.underlineToCamel(entry.getKey())).append(", ");
            }
        }
        sb.append("HttpResponse<").append(modelType).append("> response) {\n")
                .append("        HashMap<String, String> map = new HashMap<>();\n");
        for (Map.Entry<String, String> entry : defaultParamMap.entrySet()) {
            sb.append("        map.put(\"").append(entry.getKey()).append("\", \"").append(entry.getValue()).append("\");\n");
        }
        for (Map.Entry<String, String> entry : originParamMap.entrySet()) {
            if (defaultParamMap.get(entry.getKey()) == null) {
                sb.append("        map.put(\"").append(entry.getKey()).append("\", ").append(WordUtil.underlineToCamel(entry.getKey()))
                        .append(");\n");
            }
        }
        sb.append("        mHttpRequest.doPost(\"").append(url).append("\", map, \n                ").append(modelClass).append(", "
                + "response);\n")
                .append("    }");
        return sb.toString();
    }

    /**
     * Get
     *
     * // 无参数
     * private void getFunction(HttpResponse<RootClass> response) {
     * mHttpRequest.doGet("url", RootClass.class, response);
     * }
     *
     * // 有参数
     * private void getFunction(String param, HttpResponse<Object> response) {
     * mHttpRequest.doGet("url?
     * param="+param+"&defaultKey=defaultValue", null, response);
     * }
     */
    protected String getGetMethodBlock(String url, Map<String, String> defaultParamMap, Map<String, String> originParamMap,
            StringBuilder sb, boolean hasModel) {
        String modelClass = hasModel ? "RootClass.class" : "null";
        String modelType = hasModel ? "RootClass" : "Object";
        // 无参数
        if (originParamMap.size() == 0) {
            sb.append("    private void function(HttpResponse<").append(modelType).append("> response) {\n")
                    .append("        mHttpRequest.doGet(\"").append(url).append("\", ").append(modelClass).append(", response);\n")
                    .append("    }");
            return sb.toString();
        }
        // 有参数
        sb.append("    private void function(");
        for (Map.Entry<String, String> entry : originParamMap.entrySet()) {
            if (defaultParamMap.get(entry.getKey()) == null) {
                sb.append("String ").append(WordUtil.underlineToCamel(entry.getKey())).append(", ");
            }
        }
        sb.append("HttpResponse<").append(modelType).append("> response) {\n");
        sb.append("        mHttpRequest.doGet(\"").append(url).append("?\"\n                + \"");
        for (Map.Entry<String, String> entry : defaultParamMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        for (Map.Entry<String, String> entry : originParamMap.entrySet()) {
            if (defaultParamMap.get(entry.getKey()) == null) {
                sb.append(entry.getKey()).append("=\" + ").append(WordUtil.underlineToCamel(entry.getKey())).append("\n" + "                + \"&");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(", ").append(modelClass).append(", response);\n").append("    }");
        return sb.toString();
    }


    /**
     * 建立json解析好的model类
     */
    public void createModels(final String url) {
        final Message msg = new Message();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JsonToJava jsonToJava = new JsonToJava();
                    jsonToJava.setUrl(url);
                    jsonToJava.setPackage("--------------------------------------");
                    jsonToJava.setBaseType("RootClass");
                    //Log.d("out", "onCreate json -->" + jsonToJava.getJsonPoJos());
                    Object jsonObject = jsonToJava.getJsonObjectFromString(getJsonStr(jsonToJava)); // 通过jsonString得到jsonObject
                    jsonToJava.parseToModel(jsonObject); // 通过jsonObject解析得到model类
                    msg.obj = jsonToJava.getJsonPoJosList();
                } catch (Exception ex) {
                    List<String> list = new ArrayList<>();
                    list.add(ex.getMessage());
                    msg.obj = list;
                } finally {
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    /**
     * 得到jsonString
     */
    protected String getJsonStr(JsonToJava jsonToJava) throws IOException {
        jsonToJava.addOutputOption(OutputOption.GSON); // Include Gson Annotations
        //jsonToJava.addOutputOption(OutputOption.PARCELABLE); // Implement Parcelable
        //jsonToJava.addOutputOption(OutputOption.TO_STRING); // Override toString()

        /**
         * 通过之前设置好的url得到jsonString。
         * 【复写提示】
         * 通过jsonToJava.fetchUrlResponse()得到url返回的内容，进行第一次解析，
         * 最终返回的是一个jsonString就行。
         */
        return jsonToJava.fetchUrlResponse();
    }
}