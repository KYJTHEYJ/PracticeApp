package com.test.test_app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.test.test_app.base.BaseApplication;

public class DbUtil { // 로컬DB 객체를 생성 및 데이터 처리하는 메서드 제공 (트랜잭션, 커서 핸들링, SQL실행, DB에 맞게 타입 변환)

    //region "Variables"
    public Database dataBase; // DB 생성에만 관여
    public SQLiteDatabase sqLite; // 실질적으로 사용하는 DB 객체
    public Cursor dbCursor = null;
    public DbParams dbParams;

    public DbUtil(Context context) {
        dbParams = new DbParams();
        dataBase = new Database(context);
        sqLite = dataBase.getWritableDatabase();
    }
    //endregion "Variables"

    //region "Methods"
    protected void finalize() {
        if (dbCursor != null) {
            dbCursor.close();
        }

        if (sqLite != null) {
            sqLite.close();
        }

        if (dataBase != null) {
            dataBase.close();
        }
    }

    public boolean next() {
        return dbCursor.moveToNext();
    }

    public boolean first() {
        return dbCursor.moveToFirst();
    }

    public boolean last() {
        return dbCursor.moveToLast();
    }

    public boolean previous() {
        return dbCursor.moveToPrevious();
    }

    public boolean isFirst() {
        return dbCursor.isFirst();
    }

    public boolean isLast() {
        return dbCursor.isLast();
    }

    public int recordCount() {
        return dbCursor.getCount();
    }

    public void closeCursor() {
        if (dbCursor != null) {
            dbCursor.close();
        }
    }

    public void queryOpen(String sql) {
        closeCursor();
        dbCursor = sqLite.rawQuery(dbParams.getParamsApplySql(sql), null);
        dbCursor.moveToFirst();
    }

    public boolean execsqlCommit(String sql) {
        sqLite.beginTransaction();
        try {
            sqLite.execSQL(dbParams.getParamsApplySql(sql));
            sqLite.setTransactionSuccessful();
        } catch (Exception e) {
            return false;
        } finally {
            sqLite.endTransaction();
        }

        return true;
    }

    public boolean execsqlCommitThrowException(String sql) {
        sqLite.beginTransaction();
        try {
            sqLite.execSQL(dbParams.getParamsApplySql(sql));
            sqLite.setTransactionSuccessful();
        } catch (Exception e) {
            throw e;
        } finally {
            sqLite.endTransaction();
        }

        return true;
    }

    public void execSql(String sql) {
        try {
            sqLite.execSQL(dbParams.getParamsApplySql(sql));
        } catch (Exception e) {
            throw e;
        }
    }

    public void execJustSql(String sql) {
        try {
            sqLite.execSQL(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void beginTransaction() {
        if (sqLite.inTransaction() == false) {
            sqLite.beginTransaction();
        }
    }

    public boolean endTransaction() {
        try {
            if (sqLite.inTransaction() == true) {
                sqLite.setTransactionSuccessful();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            sqLite.endTransaction();
        }
        return true;
    }

    public boolean catchTransaction() {
        sqLite.endTransaction();
        return true;
    }

    public TypeChange fieldByName(String FieldName) {
        return new TypeChange(dbCursor.getColumnIndex(FieldName));
    }
    //endregion "Methods"

    //region "Inner Class"
    public class TypeChange {
        private int m_index;

        TypeChange(int colinitIndex) {
            m_index = colinitIndex;
        }

        public String asString() {
            if (dbCursor.getType(m_index) == dbCursor.FIELD_TYPE_NULL) {
                return "";
            } else {
                return BaseApplication.commonFunc.NVL(dbCursor.getString(m_index), "");
            }
        }

        public byte[] asBlob() {
            return dbCursor.getBlob(m_index);
        }

        public double asDouble() {
            return dbCursor.getDouble(m_index);
        }

        public float asFloat() {
            return dbCursor.getFloat(m_index);
        }

        public int asInteger() {
            return Double.valueOf(asDouble()).intValue();
        }

        public long asLong() {
            return Double.valueOf(asDouble()).longValue();
        }

        public short asShort() {
            return dbCursor.getShort(m_index);
        }
    }
    //endregion "Inner Class"
}