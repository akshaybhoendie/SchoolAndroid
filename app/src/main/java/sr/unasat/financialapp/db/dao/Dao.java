package sr.unasat.financialapp.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.dto.Category;
import sr.unasat.financialapp.dto.Currency;
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
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.ICON;
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
import static sr.unasat.financialapp.util.DateUtil.int_to_monthShort;
import static sr.unasat.financialapp.util.IconUtil.getImageBytes;

public class Dao extends SQLiteOpenHelper {
    Context context;
    public Dao(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
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

        if (newVersion>oldVersion){

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
    }

     private void setDefaultCategories(SQLiteDatabase db){
        Drawable drawable = ContextCompat.getDrawable(context,R.drawable.food);
        Bitmap icon = ((BitmapDrawable)drawable).getBitmap();
        byte[] iconByte = getImageBytes(icon);
        defaultCategories(db,"no category",null,0,null);
        defaultCategories(db,"income","all income",0,null);
        defaultCategories(db,"food","food expenses",300,iconByte);
        defaultCategories(db,"clothing","clothing expenses",300,null);
        defaultCategories(db,"entertainment","entertainment expenses",300,null);



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
        cursor.close();

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

        cursor.close();
        return user;
    }



    private void defaultCategories(SQLiteDatabase db,String name, String descr, double budget,byte[] icon){

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_NAME,name);
        contentValues.put(CAT_DESCR,descr);
        contentValues.put(BUDGET,budget);
        contentValues.put(USER_ID,1);
        contentValues.put(ICON,icon);

         db.insert(CAT_TABLE, null, contentValues);

    }

    public boolean insertCategory(String name, String descr, double budget,byte[] icon){

        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_NAME,name);
        contentValues.put(CAT_DESCR,descr);
        contentValues.put(BUDGET,budget);
        contentValues.put(USER_ID,1);
        contentValues.put(ICON,icon);

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
            byte[] icon= cursor.getBlob(cursor.getColumnIndex(ICON));

            category = new Category(cat_id,name,description,budget,icon,getUserById(userID));

        }
        cursor.close();
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
            byte[] icon= cursor.getBlob(cursor.getColumnIndex(ICON));

            category = new Category(cat_id,name,description,budget,icon,getUserById(userID));
        }
        cursor.close();
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

            }while (!cursor.isAfterLast());

        }
        cursor.close();

        return list;

    }

    public boolean editCategory(String name, String descr, double budget,int id){

        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_NAME,name);
        contentValues.put(CAT_DESCR,descr);
        contentValues.put(BUDGET,budget);
        contentValues.put(USER_ID,1);

        return db.update(CAT_TABLE, contentValues,CAT_ID+" = ?", new String[] { "" + id })>0;

    }

    public boolean editCategory(ContentValues contentValues,int id){

        SQLiteDatabase db=getWritableDatabase();

        return db.update(CAT_TABLE, contentValues,CAT_ID+" = ?", new String[] { "" + id })>0;

    }

    public boolean deleteCategory(int id) {
        List<Transaction> transactionsToUpdate = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_ID, 1);
        Cursor cursor;
        cursor = db.query(TRAN_TABLE, null,
                CAT_ID + " = ?", new String[]{"" + id}, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                int tranID = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                String name = cursor.getString(cursor.getColumnIndex(TRAN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(TRAN_DESCR));
                double amount = cursor.getDouble(cursor.getColumnIndex(TRAN_AMOUNT));
                String date = cursor.getString(cursor.getColumnIndex(DATE));
                int catID = cursor.getInt(cursor.getColumnIndex(CAT_ID));

                Transaction transaction = new Transaction(tranID, name, amount, date, getCategoryById(catID).getUser(), getCategoryById(catID));
                transactionsToUpdate.add(transaction);
                cursor.moveToNext();
            }
            while (cursor.isAfterLast() == false);
        }cursor.close();

        for (Transaction transaction:transactionsToUpdate){
            editTransaction(contentValues,transaction.getTran_id());
        }


        return db.delete(CAT_TABLE,CAT_ID+" = ?", new String[] { "" + id })>0;

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
        cursor.close();
        return transaction;
    }

    public boolean editTransaction(ContentValues contentValues,int id){
        SQLiteDatabase db = getWritableDatabase();
        Transaction oldTransaction = getTransactionByID(id);

        //String date = String.valueOf(contentValues.get(DATE));

        if (db.update(TRAN_TABLE,contentValues,
                TRAN_ID+" = ?", new String[] { "" + id })>0){

            Transaction transaction=getTransactionByID(id);
            editreportTrigger(transaction);
            editbalanceTrigger(oldTransaction,transaction);
            Log.i(TAG, "insertTransaction: succes");

            return true;
        }else {
            return false;
        }

    }

    public boolean deleteTransaction(int transactionID){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TRAN_AMOUNT,0);

        editTransaction(contentValues,transactionID);
        deleteRep(transactionID);

        return db.delete(TRAN_TABLE,TRAN_ID+" = ?", new String[] { "" + transactionID })>0;

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
        cursor.close();
        return transaction;
    }

    public List<Transaction> getTransactionsByDay(int day,int month, int year){
        List<Transaction> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Transaction transaction;

        Cursor cursor = db.query(
                REP_TABLE,null,
                DAY+" = ? and "+MONTH+" = ? and "+YEAR+" = ?", new String[] { "" + String.valueOf(day),String.valueOf(month),String.valueOf(year)},null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                if (transaction!=null) {
                    list.add(transaction);
                }

            }while (!cursor.isAfterLast());

        }
        cursor.close();
        return list;
    }

    public List<Transaction> getTransactionsByDayAndCategory(int day,int month, int year,Category category){
        List<Transaction> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Transaction transaction;

        Cursor cursor = db.query(
                REP_TABLE,null,
                DAY+" = ? and "+MONTH+" = ? and "+YEAR+" = ? and "+CAT_ID+" = ? ",
                new String[] { "" + String.valueOf(day),String.valueOf(month),String.valueOf(year),String.valueOf(category.getId())}
                ,null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                if (transaction!=null) {
                    list.add(transaction);
                }

            }while (!cursor.isAfterLast());

        }
        cursor.close();
        return list;
    }



    private boolean insertReport(ContentValues contentValues){

        return getWritableDatabase().insert(REP_TABLE, null, contentValues)>0;
    }

    private boolean editReport(ContentValues contentValues,int id){
        return getWritableDatabase().update(REP_TABLE,contentValues,
                TRAN_ID+" = ?", new String[] { "" + id })>0;
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
        cursor.close();
        return days;
    }

    public List<String> getDaysByCategory(int month,int year,Category category){
        SQLiteDatabase db = getReadableDatabase();
        List<String> days=new ArrayList<>();
        Cursor cursor = db.query(
                REP_TABLE,new String[]{DAY},
                MONTH+" = ? and "+YEAR+" = ? and "+CAT_ID+" = ? ", new String[] { "" + String.valueOf(month),String.valueOf(year),String.valueOf(category.getId())},null,null,null);

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
        cursor.close();
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

    private boolean editreportTrigger(Transaction transaction){

        ContentValues contentValues=new ContentValues();

        contentValues.put(USER_ID,transaction.getUser().getId());
        contentValues.put(CAT_ID,transaction.getCategory().getId());
        contentValues.put(TRAN_ID,transaction.getTran_id());

        return editReport(contentValues,transaction.getTran_id());

    }

    private boolean balanceTrigger(Transaction transaction){

        double amount = transaction.getTran_amount();
        if (transaction.getCategory().getId()!=2){
            amount=-1*amount;
        }
        User user = transaction.getUser();

        double transactionsValue = user.getTransactions();
        double opening = user.getOpening();

        double newValue = transactionsValue+amount;
        double closing = opening+newValue;

        ContentValues contentValues =new ContentValues();


        contentValues.put(TRANSACTIONS,newValue);
        contentValues.put(CLOSING,closing);

        SQLiteDatabase db = getWritableDatabase();

        return db.update(USER_TABLE, contentValues, USER_ID+ " = " + user.getId(), null)>0;
    }

    private boolean editbalanceTrigger(Transaction transactionold,Transaction transaction){

        double oldAmount = transactionold.getTran_amount();
        double amount = transaction.getTran_amount();
        User user = transaction.getUser();
        if (transaction.getCategory().getId()!=2){
            amount=-1*amount;
        }

        double transactionsValue = user.getTransactions();
        double opening = user.getOpening();

        double newValue = transactionsValue-oldAmount+amount;
        double closing = opening+newValue;

        ContentValues contentValues =new ContentValues();


        contentValues.put(TRANSACTIONS,newValue);
        contentValues.put(CLOSING,closing);

        SQLiteDatabase db = getWritableDatabase();

        return db.update(USER_TABLE, contentValues, USER_ID+ " = " + user.getId(), null)>0;
    }



    public HashMap<String,List<Transaction>> getTransactionsLast7Days(){

        SQLiteDatabase db=getReadableDatabase();
        Date date = Calendar.getInstance().getTime();
        int[] dateArr = convertDate(date);
        HashMap<String,List<Transaction>> days =new HashMap<>(7);


        Transaction transaction = null;
        int theDay= dateArr[4];
        int theMonth=dateArr[1];
        int year=dateArr[0];
        Cursor cursor;
        for (int i = 0; i<7;i++) {
            List<Transaction> transactions = new ArrayList<>();
            List<Transaction> nullTransactions =new ArrayList<>();

            if (i!=0){
                theDay--;
            }
            if ((theDay) == 0) {

                theMonth = theMonth - 1;
                if (theMonth==0){
                    theMonth=12;
                    year--;
                }
                theDay = new GregorianCalendar(year,theMonth-1,1).getActualMaximum(Calendar.DAY_OF_MONTH);

            }
                cursor = db.query(REP_TABLE, null,
                        YEAR + " = ? and " + MONTH + " = ? and " + DAY + " = ? ", new String[]
                                {"" + String.valueOf(year), String.valueOf(theMonth), String.valueOf(theDay)}, null, null, null);

                if (cursor.moveToFirst()) {
                    do {

                        int tranID=cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                        transaction = getTransactionByID(tranID);
                        cursor.moveToNext();


                        transaction.setTran_date(String.valueOf(theDay));
                        transactions.add(transaction);
                        days.put(String.valueOf(i), transactions);

                    } while (!cursor.isAfterLast());

                }else{
                    transaction=new Transaction();
                    transaction.setTran_amount(0);
                    transaction.setTran_id(0);
                    transaction.setTran_date(String.valueOf(theDay));
                    nullTransactions.add(transaction);
                    days.put(String.valueOf(i),nullTransactions);
                }
                cursor.close();

            }
        return days;
    }

    public HashMap<String,List<Transaction>> getTransactions12Months(int year) {

        SQLiteDatabase db = getReadableDatabase();
        Date date = Calendar.getInstance().getTime();
        int[] dateArr = convertDate(date);
        HashMap<String, List<Transaction>> months = new HashMap<>(12);

        //int theDay= dateArr[4];
        int theMonth = dateArr[1];
        Cursor cursor;
        for (int i = 0; i < 12; i++) {
            List<Transaction> transactions;

            if (i != 0) {
                theMonth--;
            }

            if (theMonth == 0) {
                theMonth = 12;
                year--;
            }

            transactions = getTransactionsByMonth(year,theMonth);

            if (transactions.isEmpty()){
                Transaction nulltransaction= new Transaction();

                nulltransaction.setTran_date(int_to_monthShort(theMonth));
                nulltransaction.setTran_amount(0);
                transactions.add(nulltransaction);
                months.put(String.valueOf(i),transactions);

            }else {

                transactions.get(0).setTran_date(int_to_monthShort(theMonth));
                months.put(String.valueOf(i), transactions);
            }
        }
        return months;
    }

    public List<Transaction> getTransactionsByMonth(int year, int month){
        List<Transaction> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Transaction transaction;

        Cursor cursor = db.query(
                REP_TABLE,null,
                MONTH+" = ? and "+YEAR+" = ?", new String[] { "" + String.valueOf(month),String.valueOf(year)},null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                list.add(transaction);

            }while (!cursor.isAfterLast());

            cursor.close();
        }

        return list;
    }



    private void deleteRep(int transactionId){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(REP_TABLE,TRAN_ID+" = ?", new String[] { "" + transactionId });
    }

    public double getAmountUsedByCategoryCurrentMonth(Category category){
        SQLiteDatabase db=getReadableDatabase();
        Date date=Calendar.getInstance().getTime();
        Transaction transaction;
        int[] dayArr = convertDate(date);
        double used=0;

        Cursor cursor = db.query(
                REP_TABLE,null,
                YEAR+" = ? and "+MONTH+" = ? and "+CAT_ID+" = ?",
                new String[] { "" + String.valueOf(dayArr[0]),String.valueOf(dayArr[1]),String.valueOf(category.getId())},null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                used = used+transaction.getTran_amount();

            }while (!cursor.isAfterLast());

            cursor.close();
        }

        return used;
    }

    public double getCategoryValuesByMonth(Category category,int year,int month){
        SQLiteDatabase db=getReadableDatabase();
        Transaction transaction;
        double used=0;

        Cursor cursor = db.query(
                REP_TABLE,null,
                YEAR+" = ? and "+MONTH+" = ? and "+CAT_ID+" = ?",
                new String[] { "" + String.valueOf(year),String.valueOf(month),String.valueOf(category.getId())},null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                used = used+transaction.getTran_amount();

            }while (!cursor.isAfterLast());

            cursor.close();
        }

        return used;
    }

    public double getCategoryValuesToDay(Category category){

        SQLiteDatabase db=getReadableDatabase();
        Date date=Calendar.getInstance().getTime();
        Transaction transaction;
        int[] dayArr = convertDate(date);
        double used=0;

        Cursor cursor = db.query(
                REP_TABLE,null,
                YEAR+" = ? and "+MONTH+" = ? and "+DAY+" = ? and "+CAT_ID+" = ?",
                new String[] { "" + String.valueOf(dayArr[0]),String.valueOf(dayArr[1]),String.valueOf(dayArr[4]),String.valueOf(category.getId())},null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                used = used+transaction.getTran_amount();

            }while (!cursor.isAfterLast());

            cursor.close();
        }

        return used;

    }


    public double getCategoryValues(Category category){

        SQLiteDatabase db=getReadableDatabase();

        Transaction transaction;

        double used=0;

        Cursor cursor = db.query(
                REP_TABLE,null,
                CAT_ID+" = ?",
                new String[] { "" + String.valueOf(category.getId())},null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                used = used+transaction.getTran_amount();

            }while (!cursor.isAfterLast());

            cursor.close();
        }

        return used;

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

            }while (!cursor.isAfterLast());


        }
        cursor.close();
        return list;
    }







    public double getAllCategoryValues(){

        SQLiteDatabase db=getReadableDatabase();

        Transaction transaction;

        double used=0;

        Cursor cursor = db.query(REP_TABLE,null,null,null,null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();

                used = used+transaction.getTran_amount();

            }while (!cursor.isAfterLast());

            cursor.close();
        }

        return used;

    }


    public List<Transaction>getTransactionsByCategory(Category category){
        SQLiteDatabase db=getReadableDatabase();

        Transaction transaction;
        List<Transaction>transactions=new ArrayList<>();


        Cursor cursor = db.query(
                REP_TABLE,null,
                CAT_ID+" = ?",
                new String[] { "" + String.valueOf(category.getId())},null,null,null);
        if (cursor .moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TRAN_ID));
                transaction = getTransactionByID(id);

                cursor.moveToNext();
                transactions.add(transaction);

            }while (!cursor.isAfterLast());

            cursor.close();
        }

        return transactions;
    }

    public List<Integer> getTransactionMonthsByYear(int year){
        SQLiteDatabase db = getReadableDatabase();
        List<Integer> months=new ArrayList<>();
        Cursor cursor = db.query(
                REP_TABLE,new String[]{MONTH},
                YEAR+" = ?", new String[] { "" +year},null,null,null);

        if (cursor.moveToFirst()) {
            do {
                int month=cursor.getInt(cursor.getColumnIndex(MONTH));

                if (!months.contains(month)){
                    months.add(month);
                }

                cursor.moveToNext();

            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return months;
    }

    public List<Integer> getTransactionDaysByMonth(int year,int month){
        SQLiteDatabase db = getReadableDatabase();
        List<Integer> days=new ArrayList<>();
        Cursor cursor = db.query(
                REP_TABLE,new String[]{DAY},
                YEAR+" = ? and "+MONTH+" = ? ", new String[] { "" +year,String.valueOf(month),},null,null,null);

        if (cursor.moveToFirst()) {
            do {
                int day=cursor.getInt(cursor.getColumnIndex(DAY));

                if (!days.contains(day)){
                    days.add(day);
                }

                cursor.moveToNext();

            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return days;
    }



    public List<String> getTransactionYears(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = null;
        List<String>list=new ArrayList<>();
        cursor = db.query(REP_TABLE,null,null,null,null,null,null);

        if (cursor .moveToFirst()) {
            do {
                String year = String.valueOf(cursor.getInt(cursor.getColumnIndex(YEAR)));
                if (!list.contains(year)) {
                    list.add(year);
                }

                cursor.moveToNext();


            }while (cursor.isAfterLast() == false);

        }cursor.close();

        return list;
    }


    public List<String> getTransactionMonthsByYearInt(int year){
        SQLiteDatabase db = getReadableDatabase();
        List<String> months=new ArrayList<>();
        Cursor cursor = db.query(
                REP_TABLE,new String[]{MONTH},
                YEAR+" = ?", new String[] { "" +year},null,null,null);

        if (cursor.moveToFirst()) {
            do {
                String month=String.valueOf(cursor.getInt(cursor.getColumnIndex(MONTH)));

                if (!months.contains(month)){
                    months.add(month);
                }

                cursor.moveToNext();

            }while(!cursor.isAfterLast());
        }cursor.close();

        return months;
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

    public List<Integer>getYearsWithTransactions(){
        SQLiteDatabase db = getReadableDatabase();
        List<Integer> years=new ArrayList<>();
        Cursor cursor = db.query(REP_TABLE,new String[]{YEAR},null,null,null,null,null);

        if (cursor.moveToFirst()) {
            do {
                int year=cursor.getInt(cursor.getColumnIndex(YEAR));

                years.add(year);
                cursor.moveToNext();

            }while(!cursor.isAfterLast());
        }cursor.close();

        return years;
    }
}