package sr.unasat.financialapp.db.schema;

import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_TABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.CURTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.CUR_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.USER_ID;

public class Schema {
    public static final String DATABASE_NAME = "untitledBP_database.db";
    public static final int DATABASE_VERSION = 1;


    public class SchemaUser {

        public static final String USER_TABLE = "user_table";
        public static final String USER_ID = "user_id";
        public static final String PASSWORD = "password";
        public static final String NAME_USER = "name_user";
        public static final String SURNAME = "surname";
        public static final String EMAIL = "email";
        public static final String CREATED = "created";
        public static final String DELETED = "deleted";
        public static final String OPENING = "opening";
        public static final String TRANSACTIONS = "transactions";
        public static final String CLOSING = "closing";

        public static final String CREATE_USERTABLE =
                "create table "+USER_TABLE+" ( "+ USER_ID +" integer primary key, "
                        +PASSWORD+" string not null, "
                        +NAME_USER+" string , "
                        +SURNAME+" string , "
                        +EMAIL+" string , "
                        +OPENING+" double , "
                        +TRANSACTIONS+" double , "
                        +CLOSING+" double , "
                        +CREATED+" string, "
                        + CUR_ID +" integer , "
                        +DELETED+" string, " +
                        "FOREIGN KEY ("+CUR_ID+") REFERENCES "+CURTABLE+"("+CUR_ID+")); ";

        public static final String DROP_USERTABLE = "drop table if exists "+USER_TABLE;

    }

    public class SchemaCategory {
        public static final String CAT_TABLE = "category_table";
        public static final String CAT_ID = "cat_id";
        public static final String CAT_NAME = "cat_name";
        public static final String CAT_DESCR = "cat_descr";
        public static final String BUDGET = "budget";

        public static final String CREATE_CATTABLE =
                "create table "+CAT_TABLE+" ( "+CAT_ID+" integer primary key, "
                        + CAT_NAME +" string, "
                        + CAT_DESCR +" string , "
                        + BUDGET +" double, "
                        +USER_ID +" integer, "
                        +"FOREIGN KEY ("+USER_ID+") REFERENCES "+CAT_TABLE+"("+USER_ID+")); ";

        public static final String DROP_CATTABLE = "drop table if exists "+CAT_TABLE;
    }

    public class SchemaTransaction {
        public static final String TRAN_TABLE = "tran_table";
        public static final String TRAN_ID = "tran_id";
        public static final String TRAN_NAME = "tran_name";
        public static final String TRAN_DESCR = "tran_descr";
        public static final String TRAN_AMOUNT = "tran_amount";
        public static final String DATE = "date";

        public static final String CREATE_TRANTABLE =
                "create table "+TRAN_TABLE+" ( "+TRAN_ID+" integer primary key, "
                        + TRAN_NAME +" string, "
                        + TRAN_DESCR +" string, "
                        + TRAN_AMOUNT +" double, "
                        +DATE +" string, "
                        +CAT_ID +" integer, "
                        +"FOREIGN KEY ("+CAT_ID+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";

        public static final String DROP_TRANTABLE = "drop table if exists "+TRAN_TABLE;

    }

    public class SchemaReport {
        public static final String REP_TABLE = "rep_table";
        public static final String REPORT_ID = "report_id";
        public static final String WEEKDAY = "weekday";
        public static final String DAY = "day";
        public static final String WEEK = "week";
        public static final String MONTH = "month";
        public static final String YEAR = "year";

        public static final String CREATE_REPTABLE =
                "create table "+ REP_TABLE +" ( "+ REPORT_ID +" integer primary key, "
                        + DAY +" integer, "
                        + WEEKDAY +" integer, "
                        + WEEK +" integer, "
                        + MONTH +" integer, "
                        + YEAR +" integer, "
                        +USER_ID+" integer , "
                        +TRAN_ID+" integer, "
                        +CAT_ID+" integer); ";

        public static final String DROP_REPTABLE = "drop table if exists "+ REP_TABLE;

    }

    public class SchemaCurrency{

        public static final String CURTABLE = "currency_table";
        public static final String CUR_ID = "cur_id";
        public static final String COUNTRY = "country";
        public static final String CURRENCY = "currency";
        public static final String CUR_LOGO = "cur_logo";

        public static final String CREATE_CURTABLE =
                "create table "+ CURTABLE +" ( "+ CUR_ID +" integer primary key, "
                        + COUNTRY +" string, "
                        + CURRENCY +" string , "
                        + CUR_LOGO +" string, "
                        +USER_ID +" integer); ";

        public static final String DROP_CURTABLE = "drop table if exists "+ CURTABLE;


    }

}
