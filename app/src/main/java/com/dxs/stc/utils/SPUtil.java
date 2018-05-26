package com.dxs.stc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * created by hl at 2018/5/26
 * com.dxs.stc.utils.SPUtil
 *
 * @author https://blog.csdn.net/a512337862/article/details/73633420
 * <p>
 * <p>
 * <ul>
 * <strong>init</strong>
 * <li>you should init this in your appliciton </li>
 * <li>SPUtil.getInstance(this, "yourName");</li>
 * </ul>
 * <ul>
 * <strong>设置基本数据</strong>
 * <li>boolean resultBol = SPUtil.putData(booleanKey, bool);</li>
 * <li>boolean resultInt = SPUtil.putData(intKey, in);</li>
 * <li>boolean resultStr = SPUtil.putData(stringKey, str);</li>
 * <li>boolean resultFlo = SPUtil.putData(floatKey, f);</li>
 * <li>boolean resultLon = SPUtil.putData(longKey, lo);</li>
 * <li>boolean resultBea = SPUtil.putData(beanKey, bean);</li>
 * </ul>
 * <ul>
 * <strong>读取常规数据</strong>
 * <li>boolean Bol = (boolean) SPUtil.getData(booleanKey, false);</li>
 * <li>int Int = (int) SPUtil.getData(intKey, -1);</li>
 * <li>String Str = (String) SPUtil.getData(stringKey, "");</li>
 * <li>float Flo = (float) SPUtil.getData(floatKey, 0f);</li>
 * <li>long Lon = (long) SPUtil.getData(longKey, 1L);</li>
 * <li>Bean Bea = (Bean) SPUtil.getData(beanKey, bean);</li>
 * </ul>
 * <p>
 * <strong>list & map 数据</strong>
 * //List
 * <li>private List<Integer> integers = new ArrayList<>();</li>
 * <li>private List<Bean> beens = new ArrayList<>();</li>
 * <li>private List<String> strings = new ArrayList<>();</li>
 * //Map
 * <li>private Map<String, Bean> map = new HashMap<>();</li>
 * <p>
 * <strong>设置list or map 数据</strong>
 * <li>boolean resultInts = SPUtil.putListData(intsKey, integers); </li>
 * <li>boolean resultBeans = SPUtil.putListData(hunmansKey, beens); </li>
 * <li>boolean resultStrs = SPUtil.putListData(strsKey, strings); </li>
 * <p>
 * <p>
 * <strong>获取list or map 数据</strong>
 * //List
 * <li>integers.addAll(SPUtil.getListData(intsKey, Integer.class));</li>
 * <li>humanBeens.addAll(SPUtil.getListData(hunmansKey, HumanBean.class));</li>
 * <li>strings.addAll(SPUtil.getListData(strsKey, String.class));</li>
 * <p>
 * //Map
 * <li>map.putAll(SPUtil.getHashMapData(mapKey, HumanBean.class));</li>
 * <p>
 * Map 的读取暂时只实现 Map<String,Object> 的类型。
 * @version V1.0 <SPUtil>
 */
public class SPUtil {

    private static SPUtil util;
    private static SharedPreferences sp;

    public SPUtil(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 初始化SPUtil,只需要初始化一次，建议在Application中初始化
     *
     * @param context 上下文对象
     * @param name    SharedPreferences Name
     */
    public static void getInstance(Context context, String name) {
        if (util == null) {
            util = new SPUtil(context, name);
        }
    }

    /**
     * 保存基础数据类型
     *
     * @param key   键
     * @param value 值
     * @return 保存结果
     */
    public static boolean putData(String key, Object value) {
        boolean result;
        if (value == null) {
            result = false;
            return result;
        }
        SharedPreferences.Editor editor = sp.edit();
        String type = value.getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    editor.putBoolean(key, (Boolean) value);
                    break;
                case "Long":
                    editor.putLong(key, (Long) value);
                    break;
                case "Float":
                    editor.putFloat(key, (Float) value);
                    break;
                case "String":
                    editor.putString(key, (String) value);
                    break;
                case "Integer":
                    editor.putInt(key, (Integer) value);
                    break;
                default:
                    Gson gson = new Gson();
                    String json = gson.toJson(value);
                    editor.putString(key, json);
                    break;
            }
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取SharedPreferences中保存的基本类型的数据
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return
     */
    public static Object getData(String key, Object defaultValue) {
        Object result;
        String type = defaultValue.getClass().getSimpleName();
        try {
            switch (type) {
                case "Boolean":
                    sp.getBoolean(key, (Boolean) defaultValue);
                    break;
                case "Long":
                    sp.getLong(key, (Long) defaultValue);
                    break;
                case "Float":
                    sp.getFloat(key, (Float) defaultValue);
                    break;
                case "String":
                    sp.getString(key, (String) defaultValue);
                    break;
                case "Integer":
                    sp.getInt(key, (Integer) defaultValue);
                    break;
                default:
                    Gson gson = new Gson();
                    String json = sp.getString(key, "");
                    if (!TextUtils.isEmpty(json)) {
                        result = gson.fromJson(json, defaultValue.getClass());
                    } else {
                        result = defaultValue;
                    }
                    break;
            }
            result = true;
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 保存List<T>数据类型
     *
     * @param key  键
     * @param list 值
     * @return 保存结果
     */
    public static <T> boolean putListData(String key, List<T> list) {
        boolean result;
        if (list == null || list.size() == 0) {
            result = false;
            return result;
        }
        int len = list.size();
        SharedPreferences.Editor editor = sp.edit();
        String type = list.get(0).getClass().getSimpleName();
        JsonArray array = new JsonArray();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < len; i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < len; i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < len; i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < len; i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < len; i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < len; i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取SharedPreferences中保存的List<T>的数据
     *
     * @param key 键
     * @param cls
     * @return
     */
    public static <T> List<T> getListData(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();

        String json = sp.getString(key, "");
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }

    /**
     * 用于保存map集合
     *
     * @param key
     * @param map
     * @return 保存结果
     */
    public static <K, V> Boolean putHashMapData(String key, Map<K, V> map) {
        boolean result;
        SharedPreferences.Editor editor = sp.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 获取保存的map
     *
     * @param key
     * @param clsV
     * @return
     */
    public static <V> HashMap<String, V> getHashMapData(String key, Class<V> clsV) {
        String json = sp.getString(key, "");
        HashMap<String, V> map = new HashMap<>();
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String entryKey = entry.getKey();
            JsonObject value = (JsonObject) entry.getValue();
            map.put(entryKey, gson.fromJson(value, clsV));
        }
        return map;
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public static void remove(@NonNull String key) {
        sp.edit().remove(key).apply();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean contains(@NonNull String key) {
        return sp.contains(key);
    }

    /**
     * 清除所有的缓存
     */
    public static void removeAll() {
        sp.edit().clear().apply();
    }
}
