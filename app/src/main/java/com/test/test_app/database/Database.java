package com.test.test_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    //region "Variables"
    private final Context context;
    private static final String DB_NAME = "testApp.db";
    // 스키마가 변경되면 버전업을 해줘야 DB가 생성됨 (onUpgrade 메서드 호출됨). 드롭 후 재생성 하도록 되어 있기 때문에 데이터는 다 날라감
    private static final int DATABASE_VERSION = 1;
    //endregion "Variables"

    //region "Constructors"
    public Database(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    //endregion "Constructors"

    //region "Methods"
    @Override
    public void onCreate(SQLiteDatabase db) { // 데이터베이스 생성 코드
        createTableCommon(db);
        //createTableInputSolid(db);
        //createTableInputReturnSolid(db);
        //createTableOutputSolid(db);
        //createTableReturnSolid(db);
        //createTableReturnRequest(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTableCommon(SQLiteDatabase db) { // 데이터베이스 생성 코드
        //region "기초정보"
        String sql = "DROP TABLE IF EXISTS COMMON_CFCODE";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS TEST_TEST";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_CFCODEBRD";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_CFCOMP";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_PRCUST";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_SHSHOP";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_DSWARE";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_PLBRCD";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_PLITEMCD";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_PLSTYCD";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_PLSTYDTLCD";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_PLCOLCD";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_PLSIZECD";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS COMMON_PLCOSZQTY";
        db.execSQL(sql);
        //endregion "기초정보"

        // CFCODE 테이블 (공통코드)
        db.execSQL(" CREATE TABLE [TEST_TEST]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   CONSTRAINT [PK_TEST_TEST] PRIMARY KEY ([COMP_CD])); ");

        db.execSQL(" CREATE TABLE [COMMON_CFCODE]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [GBN_CD]                  	    VARCHAR2(6) NOT NULL, " // 공통 코드 ID
                + "   [REF_CD]                  	    VARCHAR2(80) NOT NULL, " // 공통 코드
                + "   [REF_CD1]                  	    VARCHAR2(300) NOT NULL, " // 공통 코드1
                + "   [REF_CD2]                  	    VARCHAR2(300) NOT NULL, " // 공통 코드2
                + "   [REF_SNM]                  	    VARCHAR2(150) NOT NULL, " // 단축명칭 (한국어)
                + "   [REF_NM]                  	    VARCHAR2(200) NOT NULL, " // 명칭 (한국어)
                + "   [NM]                  	        VARCHAR2(200) NOT NULL, " // REF_SNM 없는 것을 REF_NM으로 채워넣은 명칭
                + "   [USE_YN]                       	VARCHAR2(1) NOT NULL, " // 사용여부
                + "   [ORD]                     	    NUMBER(5) NOT NULL, " // 정렬순서
                + "   CONSTRAINT [PK_COMMON_CFCODE] PRIMARY KEY ([COMP_CD], [GBN_CD], [REF_CD])); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_CFCODE_1] ON [COMMON_CFCODE] ([COMP_CD], [NM]); "); // 명칭 인덱스

        // CFCODEBRD 테이블 (공통코드 : 브랜드)
        db.execSQL(" CREATE TABLE [COMMON_CFCODEBRD]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [BR_CD]                  	        VARCHAR2(3) NOT NULL, " // 브랜드 코드
                + "   [GBN_CD]                  	    VARCHAR2(6) NOT NULL, " // 공통코드 ID
                + "   [REF_CD]                  	    VARCHAR2(80) NOT NULL, " // 공통 코드
                + "   [REF_SNM]                  	    VARCHAR2(150) NOT NULL, " // 단축명칭 (한국어)
                + "   [REF_NM]                        	VARCHAR2(200) NOT NULL, " // 명칭 (한국어)
                + "   [NM]                       	    VARCHAR2(200) NOT NULL, " // REF_SNM 없는 것을 REF_NM으로 채워넣은 명칭
                + "   [USE_YN]                       	VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   [ORD]                         	NUMBER(3) NOT NULL, " // 정렬 순서
                + "   CONSTRAINT [PK_COMMON_CFCODEBRD] PRIMARY KEY ([COMP_CD], [BR_CD], [GBN_CD], [REF_CD])); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_CFCODEBRD_1] ON [COMMON_CFCODEBRD] ([COMP_CD], [NM]); "); // 명칭 인덱스

        // CFCOMP 테이블 (마스터 : 회사)
        db.execSQL(" CREATE TABLE [COMMON_CFCOMP]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [COMP_KOR_NM]                  	VARCHAR2(150) NOT NULL, " // 회사 명칭
                + "   [RF_COMP_NO]                  	VARCHAR2(3) NOT NULL, " // RFID 회사 코드 고유번호
                + "   CONSTRAINT [PK_COMMON_CFCOMP] PRIMARY KEY ([COMP_CD])); ");
        db.execSQL(" CREATE UNIQUE INDEX [IDX_COMMON_CFCOMP_RFID] ON [COMMON_CFCOMP] ([COMP_CD], [RF_COMP_NO]); "); // 'RFID 회사 코드' 유니크 인덱스 설정

        // PRCUST 테이블 (마스터 : 생산거래처)
        db.execSQL(" CREATE TABLE [COMMON_PRCUST]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [CUST_CD]                  	    VARCHAR2(5) NOT NULL, " // 거래처 코드
                + "   [CUST_NM]                  	    VARCHAR2(80) NOT NULL, " // 거래처 약명
                + "   [CUST_SNM]                  	    VARCHAR2(100) NOT NULL, " // 거래처 상호
                + "   [CUST_TP]                  	    VARCHAR2(2) NOT NULL, " // 거래처 유형 (PR001)
                + "   [USE_YN]                     	    VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   CONSTRAINT [PK_COMMON_PRCUST] PRIMARY KEY ([COMP_CD], [CUST_CD])); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_PRCUST_1] ON [COMMON_PRCUST] ([COMP_CD], [CUST_NM]); "); // 명칭 인덱스
        db.execSQL(" CREATE INDEX [IDX_COMMON_PRCUST_2] ON [COMMON_PRCUST] ([COMP_CD], [CUST_TP]); "); // 타입 인덱스

        // SHSHOP 테이블 (마스터 : 매장)
        db.execSQL(" CREATE TABLE [COMMON_SHSHOP]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [SHOP_CD]                  	    VARCHAR2(6) NOT NULL, " // 매장 코드
                + "   [SHOP_NM]                  	    VARCHAR2(50) NOT NULL, " // 매장 명칭
                + "   [BIZ_DIV]                  	    VARCHAR2(5) NOT NULL, " // 사업장 구분 (SL001)
                + "   [SHOP_GB]                  	    VARCHAR2(5) NOT NULL, " // 매장 구분 (SL008)
                + "   [SHOP_TP]                     	VARCHAR2(5) NOT NULL, " // 매장 형태 (SL009)
                + "   [SHOP_STAT]                     	VARCHAR2(5) NOT NULL, " // 매장상태    (SL011)  02 영업중 03 폐점 04 정산완료
                + "   [USE_YN]                     	    VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   CONSTRAINT [PK_COMMON_SHSHOP] PRIMARY KEY ([COMP_CD], [SHOP_CD])); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_SHSHOP_1] ON [COMMON_SHSHOP] ([COMP_CD], [SHOP_NM]); "); // 명칭 인덱스
        db.execSQL(" CREATE INDEX [IDX_COMMON_SHSHOP_2] ON [COMMON_SHSHOP] ([COMP_CD], [SHOP_TP]); "); // 타입 인덱스

        // DSWARE 테이블 (마스터 : 창고)
        db.execSQL(" CREATE TABLE [COMMON_DSWARE]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [WH_CD]                  	        VARCHAR2(5) NOT NULL, " // 창고 코드
                + "   [WH_NM]                  	        VARCHAR2(50) NOT NULL, " // 창고명
                + "   [WH_GB]                  	        VARCHAR2(1) NOT NULL, " // 창고구분 (DS001)
                + "   [USE_YN]                  	    VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   [LOC_USE_YN]                  	VARCHAR2(1) NOT NULL, " // 로케이션 사용 여부
                + "   [MOBILE_YN]                  	    VARCHAR2(1) NOT NULL, " // 모바일 사용 여부
                + "   CONSTRAINT [PK_COMMON_DSWARE] PRIMARY KEY ([COMP_CD], [WH_CD])); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_DSWARE_1] ON [COMMON_DSWARE] ([COMP_CD], [WH_NM]); "); // 명칭 인덱스

        // PLBRCD 테이블 (마스터 : 브랜드)
        db.execSQL(" CREATE TABLE [COMMON_PLBRCD]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [BR_CD]                  	        VARCHAR2(2) NOT NULL, " // 브랜드 코드
                + "   [BR_NM]                        	VARCHAR2(30) NOT NULL, " // 명칭 (한국어)
                + "   [USE_YN]                       	VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   [ORD]                         	NUMBER(5) NOT NULL, " // 정렬 순서
                + "   CONSTRAINT [PK_COMMON_PLBRCD] PRIMARY KEY ([COMP_CD], [BR_CD])); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_PLBRCD_1] ON [COMMON_PLBRCD] ([COMP_CD], [BR_NM]); "); // 명칭 인덱스

        // PLITEMCD 테이블 (마스터 : 아이템)
        db.execSQL(" CREATE TABLE [COMMON_PLITEMCD]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [BR_CD]                  	        VARCHAR2(3) NOT NULL, " // 브랜드 코드
                + "   [IT]                        	    VARCHAR2(1) NOT NULL, " // 품목
                + "   [IT_GB]                        	VARCHAR2(3) NOT NULL, " // 복종
                + "   [ITEM_CD]                       	VARCHAR2(4) NOT NULL, " // 아이템 코드
                + "   [ITEM_NM]                       	VARCHAR2(50) NOT NULL, " // 명칭 (한국어)
                + "   [NM]                       	    VARCHAR2(50) NOT NULL, " // 명칭 (한국어)
                + "   [USE_YN]                       	VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   [ORD]                         	NUMBER(9) NOT NULL, " // 정렬 순서
                + "   CONSTRAINT [PK_COMMON_PLITEMCD] PRIMARY KEY ([COMP_CD], [BR_CD], [ITEM_CD])); ");
        db.execSQL(" CREATE UNIQUE INDEX [IDX_COMMON_PLITEMCD_1] ON [COMMON_PLITEMCD] ([COMP_CD], [BR_CD], [ITEM_CD]); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_PLITEMCD_2] ON [COMMON_PLITEMCD] ([COMP_CD], [BR_CD], [ITEM_CD], [NM]); "); // 명칭 인덱스

        // PLSTYCD 테이블 (마스터 : 스타일)
        db.execSQL(" CREATE TABLE [COMMON_PLSTYCD]              "
                + " ( [COMP_CD]          	     	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [STY_CD]                  	    VARCHAR2(50) NOT NULL, " // 스타일 코드
                + "   [BR_CD]                    	    VARCHAR2(3) NOT NULL, " // 브랜드
                + "   [YEAR_CD]                  	    VARCHAR2(3) NOT NULL, " // 연도
                + "   [SESN_CD]                  	    VARCHAR2(3) NOT NULL, " // 시즌
                + "   [SEX]                     	    VARCHAR2(3) NOT NULL, " // 성별 (PL008 공통관리 1)
                + "   [IT]                  	        VARCHAR2(3) NOT NULL, " // 품목 (BRD:BR001 공통관리 2)
                + "   [IT_GB]                    	    VARCHAR2(3) NOT NULL, " // 복종 (BRD:BR002 공통관리 2)
                + "   [ITEM]                       	    VARCHAR2(4) NOT NULL, " // 아이템
                + "   [LINE_CD]                   	    VARCHAR2(3) NOT NULL, " // 라인 (BRD:BR003 공통관리 3)
                + "   [STY_SEQ]                   	    VARCHAR2(5) NOT NULL, " // 스타일 순번
                + "   [STY_NM]                      	VARCHAR2(100) NOT NULL, " // 스타일 명칭 (한글 품명)
                + "   [STY_NM_ENG]                  	VARCHAR2(100) NOT NULL, " // 스타일 명칭 (영문 품명)
                + "   [MAIN_STY_CD]                  	VARCHAR2(15) NOT NULL, " // 메인 스타일 코드
                + "   [RF_STY_NO]                    	VARCHAR2(6) NOT NULL, " // RFID 스타일 코드 고유번호
                + "   [CUST_CD]                    	    VARCHAR2(5) NOT NULL, " // 생산처 코드
                + "   [RFID_YN]                    	    VARCHAR2(1) NOT NULL, " // RFID 사용여부
                + "   [RFID_SKIP]                    	VARCHAR2(1) NOT NULL, " // RFID 관리 제품이지만 RFID 수불 가능 여부
                + "   [USE_YN]                       	VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   CONSTRAINT [PK_COMMON_PLSTYCD] PRIMARY KEY ([COMP_CD], [STY_CD])); ");
        db.execSQL(" CREATE UNIQUE INDEX [IDX_COMMON_PLSTYCD_RFID] ON [COMMON_PLSTYCD] ([COMP_CD], [RF_STY_NO]); "); // 'RFID 스타일 코드' 유니크 인덱스 설정
        db.execSQL(" CREATE INDEX [IDX_COMMON_PLSTYCD_1] ON [COMMON_PLSTYCD] ([COMP_CD], [STY_NM]); "); // 명칭 인덱스
        db.execSQL(" CREATE INDEX [IDX_COMMON_PLSTYCD_2] ON [COMMON_PLSTYCD] ([COMP_CD], [CUST_CD], [STY_NM]); "); // 명칭 + 회사 + 인덱스

        // PLSTYDTLCD 테이블 (디테일 : 스타일 제품 바코드)
        db.execSQL(" CREATE TABLE [COMMON_PLSTYDTLCD]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [BAR_CD]                  	    VARCHAR2(20) NOT NULL, " // 제품바코드
                + "   [STY_CD]                  	    VARCHAR2(15) NOT NULL, " // 스타일코드
                + "   [COL_CD]                  	    VARCHAR2(5) NOT NULL, " // 컬러코드
                + "   [SIZE_CD]                  	    VARCHAR2(3) NOT NULL, " // 사이즈코드
                + "   [STY_TP]                  	    VARCHAR2(1) NOT NULL, " // 스타일 타입 (바코드구분(PL031 P 자사, 8 88코드, T 면세점, E 기타))
                + "   CONSTRAINT [PK_COMMON_PLSTYDTLCD] PRIMARY KEY ([COMP_CD], [BAR_CD])); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_PLSTYDTLCD_1] ON [COMMON_PLSTYDTLCD] ([COMP_CD], [BAR_CD], [STY_CD], [COL_CD], [SIZE_CD], [STY_TP]); "); // 바코드 인덱스

        // PLCOLCD 테이블 (마스터 : 컬러)
        db.execSQL(" CREATE TABLE [COMMON_PLCOLCD]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [COL_CD]                  	    VARCHAR2(5) NOT NULL, " // 컬러 코드
                + "   [COL_NM]                  	    VARCHAR2(100) NOT NULL, " // 컬러 명칭
                + "   [RF_COL_NO]                     	VARCHAR2(4) NOT NULL, " // RFID 컬러 코드 고유번호
                + "   [USE_YN]                       	VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   CONSTRAINT [PK_COMMON_PLCOLCD] PRIMARY KEY ([COMP_CD], [COL_CD])); ");
        db.execSQL(" CREATE UNIQUE INDEX [IDX_COMMON_PLCOLCD_RFID] ON [COMMON_PLCOLCD] ([COMP_CD], [RF_COL_NO]); "); // 'RFID 컬러 코드' 유니크 인덱스 설정
        db.execSQL(" CREATE INDEX [IDX_COMMON_PLCOLCD_1] ON [COMMON_PLCOLCD] ([COMP_CD], [COL_NM]); "); // 명칭 인덱스

        // PLSIZECD 테이블 (마스터 : 사이즈)
        db.execSQL(" CREATE TABLE [COMMON_PLSIZECD]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [SIZE_CD]                  	    VARCHAR2(4) NOT NULL, " // 사이즈 코드
                + "   [SIZE_NM]                  	    VARCHAR2(50) NOT NULL, " // 사이즈 명칭
                + "   [RF_SIZE_NO]                     	VARCHAR2(4) NOT NULL, " // RFID 사이즈 코드 고유번호
                + "   [USE_YN]                       	VARCHAR2(1) NOT NULL, " // 사용 여부
                + "   CONSTRAINT [PK_COMMON_PLSIZECD] PRIMARY KEY ([COMP_CD], [SIZE_CD])); ");
        db.execSQL(" CREATE UNIQUE INDEX [IDX_COMMON_PLSIZECD_RFID] ON [COMMON_PLSIZECD] ([COMP_CD], [RF_SIZE_NO]); "); // 'RFID 사이즈 코드' 유니크 인덱스 설정
        db.execSQL(" CREATE INDEX [IDX_COMMON_PLSIZECD_1] ON [COMMON_PLSIZECD] ([COMP_CD], [SIZE_NM]); "); // 명칭 인덱스

        // PLCOSZQTY 테이블 (마스터 : 작지정보)
        db.execSQL(" CREATE TABLE [COMMON_PLCOSZQTY]              "
                + " ( [COMP_CD]          	    	    VARCHAR2(5) NOT NULL,  " // 회사 코드
                + "   [STY_CD]                  	    VARCHAR2(15) NOT NULL, " // 스타일 코드
                + "   [ADD_NO]                  	    VARCHAR2(3) NOT NULL, " // 생산차수
                + "   [COL_CD]                     	    VARCHAR2(5) NOT NULL, " // 컬러 코드
                + "   [SIZE_CD]                       	VARCHAR2(3) NOT NULL, " // 사이즈 코드
                + "   [CUST_CD]                       	VARCHAR2(10) NOT NULL, " // 거래처 코드
                + "   CONSTRAINT [PK_COMMON_PLCOSZQTY] PRIMARY KEY ([COMP_CD], [STY_CD], [ADD_NO], [COL_CD], [SIZE_CD])); ");
        db.execSQL(" CREATE INDEX [IDX_COMMON_PLCOSZQTY_1] ON [COMMON_PLCOSZQTY] ([COMP_CD], [STY_CD], [ADD_NO], [COL_CD], [SIZE_CD], [CUST_CD]); "); // 거래처 코드 인덱스
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    //endregion "Methods"
}
