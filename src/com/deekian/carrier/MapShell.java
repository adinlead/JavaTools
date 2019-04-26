package com.deekian.carrier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adinlead on 16/09/28.
 */
public class MapShell {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    //    要被包装的Map
    private Map map;

    public MapShell() {
        this.map = new HashMap();
    }

    public MapShell(Map map) {
        if (map == null) {
            this.map = new HashMap();
        } else {
            this.map = map;
        }
    }

    public MapShell(Map map, Map spare) {
        if (map == null) {
            this.map = spare;
        } else {
            this.map = map;
        }
    }

    public Map getMap() {
        return map;
    }

    public boolean notEmpty() {
        return this.map.size() > 0;
    }

    public boolean notEmpty(Object key) {
        return this.map.containsKey(key) && this.map.get(key) != null && !isEmptyString(this.getString(key));
    }

    public boolean notEmpty(Object... keys) {
        for (Object key : keys) {
            if (this.isEmpty(key))
                return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return this.map.size() <= 0;
    }

    public boolean isEmpty(Object key) {
        return !this.map.containsKey(key) || isEmptyString(this.getString(key));
    }

    public boolean hasEmpty(Object... keys) {
        for (Object k : keys) {
            if (isEmpty(k))
                return true;
        }
        return false;
    }

    public Object get(Object key) {
        return this.map.get(key);
    }

    public void put(Object key, Object value) {
        this.map.put(key, value);
    }

    public Integer getInteger(Object key) {
        return getInteger(key, null);
    }

    public Integer getInteger(Object key, Integer spare) {
        if (this.map.containsKey(key) && this.map.get(key) != null) {
            Object o = this.map.get(key);
            if (o instanceof Integer) {
                return (Integer) o;
            } else {
                try {
                    return Integer.valueOf(o.toString());
                } catch (NumberFormatException e) {
                    return spare;
                }
            }
        }
        return spare;
    }

    public Long getLong(Object key) {
        return getLong(key, null);
    }

    public Long getLong(Object key, Long spare) {
        if (this.map.containsKey(key) && this.map.get(key) != null) {
            Object o = this.map.get(key);
            if (o instanceof Long) {
                return (Long) o;
            } else {
                try {
                    return Long.valueOf(o.toString());
                } catch (NumberFormatException e) {
                    return spare;
                }
            }
        }
        return spare;
    }

    public Double getDouble(Object key) {
        return getDouble(key, null);
    }

    public Double getDouble(Object key, Double spare) {
        if (this.map.containsKey(key) && this.map.get(key) != null) {
            Object o = this.map.get(key);
            if (o instanceof Double) {
                return (Double) o;
            } else {
                try {
                    return Double.valueOf(o.toString());
                } catch (NumberFormatException e) {
                    return spare;
                }
            }
        }
        return spare;
    }

    public String getString(Object key) {
        return getString(key, null);
    }

    public String getString(Object key, String spare) {
        if (this.map.containsKey(key)) {
            return String.valueOf(this.map.get(key));
        }
        return spare;
    }

    public int size() {
        return this.map.size();
    }

    public boolean valueEquation(Object key, Object val) {
        Object o = this.map.get(key);
        if (o == val)
            return true;
        else
            return val.equals(val);
    }

    /**
     * @param keys
     * @function 保留键存在于参数中的的值
     */
    public void retain(Object... keys) {
        Map tmp = this.map;
        this.map.clear();
        for (Object key : keys) {
            this.map.put(key, tmp.get(key));
        }
    }

    public boolean valueNot(String key, int... values) {
        int v = this.getInteger(key);
        for (int value : values) {
            if (v == value)
                return false;
        }
        return true;
    }

    public Date getDFDate(String key) {
        if (this.isEmpty(key)) {
            return null;
        }
        try {
            return DATE_FORMAT.parse(this.getString(key));
        } catch (ParseException e) {
            System.out.printf("格式化时间时发生错误! %s", e);
            return null;
        }
    }

    public Date getDFTime(String key) {
        if (this.isEmpty(key)) {
            return null;
        }
        try {
            return TIME_FORMAT.parse(this.getString(key));
        } catch (ParseException e) {
            System.out.printf("格式化时间时发生错误! %s", e);
            return null;
        }
    }


    private static boolean isEmptyString(String s) {
        return s != null && s.length() > 0;
    }
}