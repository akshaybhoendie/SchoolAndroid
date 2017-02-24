package sr.unasat.financialapp.dto;


public class User {
    private int id;
    private String password;
    private String email;
    private String created;
    private double opening;
    private double transactions;
    private double closing;
    private Currency currency;

    //additional info
    private String nameUser;
    private String Surname;
    private String birthDate;
    private String gender;

    //when deleted
    private String deleted;

    public User() {
        //empty constructor
    }

    public User(int id, String password, String email, String created, double opening, Currency currency) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.created = created;
        this.opening = opening;
        this.currency = currency;
    }

    public User(int id, String password, String email, String created, double opening, double transactions, double closing, Currency currency, String nameUser, String surname, String birthDate, String gender) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.created = created;
        this.opening = opening;
        this.transactions = transactions;
        this.closing = closing;
        this.currency = currency;
        this.nameUser = nameUser;
        Surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public double getOpening() {
        return opening;
    }

    public void setOpening(double opening) {
        this.opening = opening;
    }

    public double getTransactions() {
        return transactions;
    }

    public void setTransactions(double transactions) {
        this.transactions = transactions;
    }

    public double getClosing() {
        return closing;
    }

    public void setClosing(double closing) {
        this.closing = closing;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
