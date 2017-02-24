package sr.unasat.financialapp.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import sr.unasat.financialapp.dto.Category;
import sr.unasat.financialapp.dto.Currency;
import sr.unasat.financialapp.dto.Report;
import sr.unasat.financialapp.dto.Transaction;
import sr.unasat.financialapp.dto.User;
import static android.content.ContentValues.TAG;
import static sr.unasat.financialapp.db.schema.Schema.DATABASE_NAME;
import static sr.unasat.financialapp.db.schema.Schema.DATABASE_VERSION;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.BUDGET;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_DESCR;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_NAME;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_TABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CREATE_CATTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.DROP_CATTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.COUNTRY;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.CREATE_CURTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.CURRENCY;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.CURTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.CUR_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.CUR_LOGO;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCurrency.DROP_CURTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaReport.CREATE_REPTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaReport.DAY;
import static sr.unasat.financialapp.db.schema.Schema.SchemaReport.WEEKDAY;
import static sr.unasat.financialapp.db.schema.Schema.SchemaReport.DROP_REPTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaReport.MONTH;
import static sr.unasat.financialapp.db.schema.Schema.SchemaReport.REP_TABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaReport.WEEK;
import static sr.unasat.financialapp.db.schema.Schema.SchemaReport.YEAR;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.CREATE_TRANTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.DATE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.DROP_TRANTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_AMOUNT;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_DESCR;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_NAME;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_TABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.CLOSING;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.CREATED;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.CREATE_USERTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.DROP_USERTABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.EMAIL;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.NAME_USER;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.OPENING;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.PASSWORD;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.SURNAME;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.TRANSACTIONS;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.USER_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.USER_TABLE;
import static sr.unasat.financialapp.util.DateUtil.convertDate;
import static sr.unasat.financialapp.util.DateUtil.int_to_month;

public class Dao extends SQLiteOpenHelper {

    public Dao(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_USERTABLE);
        db.execSQL(CREATE_TRANTABLE);
        db.execSQL(CREATE_CATTABLE);
        db.execSQL(CREATE_REPTABLE);
        db.execSQL(CREATE_CURTABLE);


        setDefaultUser(db);
        setDefaultCategories(db);
        // setDefaultCurrencies();
        Log.i(TAG, "onCreate: succesfull");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_USERTABLE);
        db.execSQL(DROP_TRANTABLE);
        db.execSQL(DROP_CATTABLE);
        db.execSQL(DROP_REPTABLE);
        db.execSQL(DROP_CURTABLE);

        db.execSQL(CREATE_USERTABLE);
        db.execSQL(CREATE_TRANTABLE);
        db.execSQL(CREATE_CATTABLE);
        db.execSQL(CREATE_REPTABLE);
        db.execSQL(CREATE_CURTABLE);
    }

    private void setDefaultCategories(SQLiteDatabase db){

        insertCategory(db,"no category",null,0);
        insertCategory(db,"income","all income",0);
        insertCategory(db,"food","food expenses",300);
        insertCategory(db,"clothing","clothing expenses",300);
        insertCategory(db,"entertainment","entertainment expenses",300);



    }

    private void setDefaultUser(SQLiteDatabase db){

        ContentValues contentValues=new ContentValues();
        contentValues.put(EMAIL,"email");
        contentValues.put(PASSWORD,"0000");

        db.insert(USER_TABLE, null, contentValues);

    }

    //methods to use db

    public Currency getCurencyByID(int id){
        Currency currency = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=null;

        cursor = db.query(CURTABLE, null,
                CUR_ID+" = ?", new String[] { "" + id },null,null,null);
        if (cursor.moveToFirst()){
            int curID = cursor.getInt(cursor.getColumnIndex(CUR_ID));
            String country = cursor.getString(cursor.getColumnIndex(COUNTRY));
            String nameCurrency = cursor.getString(cursor.getColumnIndex(CURRENCY));
            String logo = cursor.getString(cursor.getColumnIndex(CUR_LOGO));

            currency = new Currency(curID,country,nameCurrency,logo);

        }

        return currency;
    }

    public User getUserById(int id ){
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        cursor = db.query(USER_TABLE, null,
                USER_ID+" = ?", new String[] { "" + id },null,null,null);
        if (cursor.moveToFirst()){
            int userID = cursor.getInt(cursor.getColumnIndex(USER_ID));
            String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(EMAIL));
            String surname = cursor.getString(cursor.getColumnIndex(SURNAME));
            String name = cursor.getString(cursor.getColumnIndex(NAME_USER));
            String created = cursor.getString(cursor.getColumnIndex(CREATED));
            double opening = cursor.getDouble(cursor.getColumnIndex(OPENING));
            double transactions = cursor.getDouble(cursor.getColumnIndex(TRANSACTIONS));
            double closing = cursor.getDouble(cursor.getColumnIndex(CLOSING));
            int currencyID = cursor.getInt(cursor.getColumnIndex(CUR_ID));

            user = new User(userID, password, email, created, opening, transactions, closing, null,null,null,null,null);


        }

        return user;
    }


    private boolean insertCategory(SQLiteDatabase db,String name, String descr, double budget){

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_NAME,name);
        contentValues.put(CAT_DESCR,descr);
        contentValues.put(BUDGET,budget);
        contentValues.put(USER_ID,1);

        return db.insert(CAT_TABLE, null, contentValues)>0;

    }

    public Category getCategoryById( int id ){
        Category category = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        cursor = db.query(CAT_TABLE, null,
                CAT_ID+" = ?", new String[] { "" + id },null,null,null);
        if (cursor.moveToFirst()) {

            int cat_id = cursor.getInt(cursor.getColumnIndex(CAT_ID));
            String name = cursor.getString(cursor.getColumnIndex(CAT_NAME));
            String description= cursor.getString(cursor.getColumnIndex(CAT_DESCR));
            double budget = cursor.getDouble(cursor.getColumnIndex(BUDGET));
            int userID =       cursor.getInt(cursor.getColumnIndex(USER_ID));

            category = new Category(cat_id,name,description,budget,getUserById(userID));

        }
        return category;
    }

    public Category getCategoryByName( String name ){
        Category category = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        cursor = db.query(CAT_TABLE, null,
                CAT_NAME+" = ?", new String[] { "" + name },null,null,null);
        if (cursor.moveToFirst()) {

            int cat_id = cursor.getInt(cursor.getColumnIndex(CAT_ID));
            String cat_name = cursor.getString(cursor.getColumnIndex(CAT_NAME));
            String description= cursor.getString(cursor.getColumnIndex(CAT_DESCR));
            double budget = cursor.getDouble(cursor.getColumnIndex(BUDGET));
            int userID =       cursor.getInt(cursor.getColumnIndex(USER_ID));

            category = new Category(cat_id,cat_name,description,budget,getUserById(userID));

        }
        return category;
    }

    public List<Category> getCategories(){
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Category category;

        Cursor  cursor = db.query(CAT_TABLE,null,null,null,null,null,null);

        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(CAT_ID));
                category = getCategoryById(id);

                cursor.moveToNext();

                list.add(category);

            }while (cursor.isAfterLast() == false);

        }

        return list;

    }


    public boolean insertTransaction(ContentValues contentValues){

        SQLiteDatabase db = getWritableDatabase();

        //String date = String.valueOf(contentValues.get(DATE));

        if (db.insert(TRAN_TABLE, null, contentValues)>0){

            Transaction transaction=getLastTransaction();
            reportTrigger(transaction);
            balanceTrigger(transaction);
            Log.i(TAG, "insertTransaction: succes");

            return true;
        }else {
            return false;
        }


    }

    public Transaction getTransactionByID( int id ){
        Transaction transaction=null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        cursor = db.query(TRAN_TABLE, null,
                TRAN_ID+" = ?", new String[] { "" + id },null,null,null);
        if (cursor.moveToFirst()) {

            int tranID = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
            String name = cursor.getString(cursor.getColumnIndex(TRAN_NAME));
            String description= cursor.getString(cursor.getColumnIndex(TRAN_DESCR));
            double amount = cursor.getDouble(cursor.getColumnIndex(TRAN_AMOUNT));
            String date= cursor.getString(cursor.getColumnIndex(DATE));
            int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));

            transaction= new Transaction(tranID, name, amount, date, getCategoryById(catID).getUser(),getCategoryById(catID));


        }

        return transaction;
    }

    public Transaction getTransactionByName( String name ){
        Transaction transaction=null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        cursor = db.query(TRAN_TABLE, null,
                TRAN_NAME+" = ?", new String[] { "" + name },null,null,null);
        if (cursor.moveToFirst()) {

            int tranID = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
            String tran_name = cursor.getString(cursor.getColumnIndex(TRAN_NAME));
            String description= cursor.getString(cursor.getColumnIndex(TRAN_DESCR));
            double amount = cursor.getDouble(cursor.getColumnIndex(TRAN_AMOUNT));
            String date= cursor.getString(cursor.getColumnIndex(DATE));
            int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));

            transaction= new Transaction(tranID, tran_name, amount, date, getCategoryById(catID).getUser(),getCategoryById(catID));


        }

        return transaction;
    }

    public List<Transaction> getTransactions(){
        List<Transaction> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Transaction transaction;

        Cursor cursor = null;
        cursor = db.query(TRAN_TABLE,null,null,null,null,null,null);

        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                list.add(transaction);

            }while (cursor.isAfterLast() == false);

        }

        return list;
    }

    private Transaction getLastTransaction(){
        Transaction transaction = null;
        Cursor cursor = null;
        SQLiteDatabase db= getReadableDatabase();

        cursor = db.query(TRAN_TABLE,null,null,null,null,null,null);

        if (cursor.moveToLast()) {

            int tranID = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
            String tran_name = cursor.getString(cursor.getColumnIndex(TRAN_NAME));
            String description= cursor.getString(cursor.getColumnIndex(TRAN_DESCR));
            double amount = cursor.getDouble(cursor.getColumnIndex(TRAN_AMOUNT));
            String date= cursor.getString(cursor.getColumnIndex(DATE));
            int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));

            transaction= new Transaction(tranID, tran_name, amount, date, getCategoryById(catID).getUser(),getCategoryById(catID));
        }

        return transaction;
    }

    public List<Transaction> getTransactionsByDay(int day,int year){
        List<Transaction> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Transaction transaction;

        Cursor cursor = db.query(
                REP_TABLE,new String[]{DAY},
                MONTH+" = ? and "+YEAR+" = ?", new String[] { "" + String.valueOf(day),String.valueOf(year)},null,null,null);



        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                list.add(transaction);

            }while (cursor.isAfterLast() == false);

        }

        return list;
    }




    private boolean insertReport(ContentValues contentValues){

        return getWritableDatabase().insert(REP_TABLE, null, contentValues)>0;
    }

    public List<String> getDays(int month,int year){
        SQLiteDatabase db = getReadableDatabase();
        List<String> days=new ArrayList<>();
        Cursor cursor = db.query(
                REP_TABLE,new String[]{DAY},
                MONTH+" = ? and "+YEAR+" = ?", new String[] { "" + String.valueOf(month),String.valueOf(year)},null,null,null);

        if (cursor.moveToFirst()) {
            do {
                String day=String.valueOf(cursor.getInt(cursor.getColumnIndex(DAY)));

                if (!days.contains(day+" "+int_to_month(month)+" "+year)){
                    String date = day+" "+int_to_month(month)+" "+year;
                    days.add(date);
                }

                cursor.moveToNext();

            }while(!cursor.isAfterLast());
        }

        return days;
    }

    private boolean reportTrigger(Transaction transaction){

        ContentValues contentValues=new ContentValues();

        contentValues.put(USER_ID,transaction.getUser().getId());
        contentValues.put(CAT_ID,transaction.getCategory().getId());
        contentValues.put(TRAN_ID,transaction.getTran_id());

        int[] date= convertDate(Calendar.getInstance().getTime());

        contentValues.put(YEAR,date[0]);
        contentValues.put(MONTH,date[1]);
        contentValues.put(WEEK,date[2]);
        contentValues.put(WEEKDAY,date[3]);
        contentValues.put(DAY,date[4]);

        return insertReport(contentValues);

    }

    private boolean balanceTrigger(Transaction transaction){

        double amount = transaction.getTran_amount();
        User user = transaction.getUser();

        double transactionsValue = user.getTransactions();
        double opening = user.getOpening();

        double newValue = transactionsValue+amount;
        double closing = opening-newValue;

        ContentValues contentValues =new ContentValues();


        contentValues.put(TRANSACTIONS,newValue);
        contentValues.put(CLOSING,closing);

        SQLiteDatabase db = getWritableDatabase();

        return db.update(USER_TABLE, contentValues, USER_ID+ " = " + user.getId(), null)>0;
    }


}