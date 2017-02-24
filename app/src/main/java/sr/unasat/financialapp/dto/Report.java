package sr.unasat.financialapp.dto;

public class Report {

    private int id;
    private int day;
    private int week;
    private int month;
    private int year;
    private Transaction transaction;
    private Category category;
    private User user;

    public Report() {
    }

    public Report(int id, int day, int week, int month, int year, Transaction transaction, Category category, User user) {
        this.id = id;
        this.day = day;
        this.week = week;
        this.month = month;
        this.year = year;
        this.transaction = transaction;
        this.category = category;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
