package sr.unasat.financialapp.dto;

public class Transaction {

    private int tran_id;
    private String tran_name;
    private double tran_amount;
    private String tran_date;
    private User user;
    private Category category;

    public Transaction(){

    }

    public Transaction(int tran_id, String tran_name, double tran_amount, String tran_date, User user, Category category) {
        this.tran_id = tran_id;
        this.tran_name = tran_name;
        this.tran_amount = tran_amount;
        this.tran_date = tran_date;
        this.user = user;
        this.category = category;
    }

    public int getTran_id() {
        return tran_id;
    }

    public void setTran_id(int tran_id) {
        this.tran_id = tran_id;
    }

    public String getTran_name() {
        return tran_name;
    }

    public void setTran_name(String tran_name) {
        this.tran_name = tran_name;
    }

    public double getTran_amount() {
        return tran_amount;
    }

    public void setTran_amount(double tran_amount) {
        this.tran_amount = tran_amount;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
