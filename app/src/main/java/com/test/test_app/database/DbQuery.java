package com.test.test_app.database;

import com.test.test_app.base.BaseApplication;

public class DbQuery { // 공통사용하는 쿼리 저장

    //region "Variables"
    public static final int ZEROLINE = 0;
    public static final int ONELINE = 1;
    public static final int MULTILINE = 2;
    //endregion "Variables"

    //region "Methods"
    private static int getDbLineCheck(DbUtil db) {
        if (db.recordCount() == 0) {
            return ZEROLINE;
        } else if (db.recordCount() == 1) {
            return ONELINE;
        } else {
            return MULTILINE;
        }
    }

    public static int getStyleLikeOpen(DbUtil db, String searchInfo) {
        searchInfo = searchInfo.trim();

        String sql = "SELECT DISTINCT STY_CD, STY_NM, RF_STY_NO " +
                "FROM COMMON_PLSTYCD " +
                "WHERE COMP_CD = :COMP_CD AND (STY_CD LIKE :STY_CD OR STY_NM LIKE :STY_NM OR RF_STY_NO LIKE :RF_STY_NO) " +
                "ORDER BY STY_NM";
        db.dbParams.clear();
        db.dbParams.add("COMP_CD", BaseApplication.UserInfo.COMP_CD);
        db.dbParams.add("STY_CD", "%" + searchInfo + "%");
        db.dbParams.add("STY_NM", "%" + searchInfo + "%");
        db.dbParams.add("RF_STY_NO", "%" + searchInfo + "%");
        db.queryOpen(sql);

        return getDbLineCheck(db);
    }

    public static int getColorLikeOpen(DbUtil db, String searchInfo) {
        searchInfo = searchInfo.trim();

        String sql = " SELECT COL_CD, COL_NM, RF_COL_NO " +
                "FROM COMMON_PLCOLCD " +
                "WHERE COMP_CD = :COMP_CD AND (COL_CD LIKE :COL_CD OR COL_NM LIKE :COL_NM OR RF_COL_NO LIKE :RF_COL_NO) " +
                "ORDER BY COL_NM";
        db.dbParams.clear();
        db.dbParams.add("COMP_CD", BaseApplication.UserInfo.COMP_CD);
        db.dbParams.add("COL_CD", "%" + searchInfo + "%");
        db.dbParams.add("COL_NM", "%" + searchInfo + "%");
        db.dbParams.add("RF_COL_NO", "%" + searchInfo + "%");
        db.queryOpen(sql);

        return getDbLineCheck(db);
    }

    public static int getSizeLikeOpen(DbUtil db, String searchInfo) {
        searchInfo = searchInfo.trim();

        String sql = " SELECT SIZE_CD, SIZE_NM, RF_SIZE_NO " +
                "FROM COMMON_PLSIZECD " +
                "WHERE COMP_CD = :COMP_CD AND (SIZE_CD LIKE :SIZE_CD OR SIZE_NM LIKE :SIZE_NM OR RF_SIZE_NO LIKE :RF_SIZE_NO) " +
                "ORDER BY SIZE_NM";
        db.dbParams.clear();
        db.dbParams.add("COMP_CD", BaseApplication.UserInfo.COMP_CD);
        db.dbParams.add("SIZE_CD", "%" + searchInfo + "%");
        db.dbParams.add("SIZE_NM", "%" + searchInfo + "%");
        db.dbParams.add("RF_SIZE_NO", "%" + searchInfo + "%");
        db.queryOpen(sql);

        return getDbLineCheck(db);
    }

    public static int getCustLikeOpen(DbUtil db, String searchInfo) {
        String sql = "SELECT CUST_CD, IFNULL(CUST_SNM, CUST_NM) AS CUST_NM, CUST_TP, USE_YN " +
                "FROM COMMON_PRCUST " +
                "WHERE COMP_CD = :COMP_CD AND USE_YN = 'Y' AND (CUST_CD LIKE :CUST_CD OR CUST_NM LIKE :CUST_NM OR CUST_SNM LIKE :CUST_SNM) " +
                "ORDER BY CUST_CD";
        db.dbParams.clear();
        db.dbParams.add("COMP_CD", BaseApplication.UserInfo.COMP_CD);
        db.dbParams.add("CUST_CD", "%" + searchInfo + "%");
        db.dbParams.add("CUST_NM", "%" + searchInfo + "%");
        db.dbParams.add("CUST_SNM", "%" + searchInfo + "%");
        db.queryOpen(sql);

        return getDbLineCheck(db);
    }

    public static int getBarcodeLikeOpenNew(DbUtil db, String searchInfo) {
        searchInfo = searchInfo.trim();
        String sql = " SELECT A.BAR_CD, A.STY_CD, A.COL_CD, A.SIZE_CD, B.RF_STY_NO, B.STY_NM, C.RF_COL_NO, C.COL_NM, D.RF_SIZE_NO, D.SIZE_NM " +
                " FROM COMMON_PLSTYDTLCD A " +
                " LEFT OUTER JOIN COMMON_PLSTYCD B ON A.COMP_CD = B.COMP_CD AND A.STY_CD = B.STY_CD " +
                " LEFT OUTER JOIN COMMON_PLCOLCD C ON A.COMP_CD = C.COMP_CD AND A.COL_CD = C.COL_CD " +
                " LEFT OUTER JOIN COMMON_PLSIZECD D ON A.COMP_CD = D.COMP_CD AND A.SIZE_CD = D.SIZE_CD " +
                " WHERE A.COMP_CD = :COMP_CD AND (A.BAR_CD LIKE :BAR_CD OR B.STY_NM LIKE :STY_NM) ";

        db.dbParams.clear();
        db.dbParams.add("COMP_CD", BaseApplication.UserInfo.COMP_CD);
        db.dbParams.add("BAR_CD", "%" + searchInfo + "%");
        db.dbParams.add("STY_NM", "%" + searchInfo + "%");
        db.queryOpen(sql);

        return getDbLineCheck(db);
    }
    //region "Methods"
}