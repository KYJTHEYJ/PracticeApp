package com.test.test_app.database;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class DbParams {

    //region "Variables"
    private HashMap<String, Object> dbParamsList; // 파라미터를 해쉬맵에 담아서 처리
    //endregion "Variables"

    //region "Methods"
    public DbParams() {
        dbParamsList = new HashMap<>();
    }

    public HashMap<String, Object> getParamsList() {
        return dbParamsList;
    }

    public void add(String po_FieldName, Object po_Value) {
        dbParamsList.put(po_FieldName, po_Value);
    }

    public void clear() {
        dbParamsList.clear();
    }

    public String getString(String po_FieldName) {
        return String.valueOf(dbParamsList.get(po_FieldName));
    }

    public String getParamsApplySql(String sql) {
        if (dbParamsList.size() <= 0) {
            return sql;
        }

        Comparator<String> comp = (lhs, rhs) -> {
            if (lhs.length() < rhs.length()) {
                return 1;
            }

            if (lhs.length() > rhs.length()) {
                return -1;
            }

            return 0;
        };

        Set<String> set = dbParamsList.keySet();
        String[] keys = set.toArray(new String[set.size()]);
        Arrays.sort(keys, comp);

        for (String key : keys) {
            Object obj = dbParamsList.get(key);
            String str;
            str = String.valueOf(obj);
            str = str.replace("'", "''");

            if (obj instanceof String) {
                str = "'" + str + "'";
            }

            sql = sql.replace(":" + key, str);
        }

        clear();
        return sql;
    }
    //endregion "Methods"
}