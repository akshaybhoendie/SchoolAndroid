package sr.unasat.financialapp.dto;

public class Category {
    private int id;
    private String name;
    private String descr;
    private double budget;
    private User user;

    public Category(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Category(int id, String name, String descr, double budget, User user) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.budget = budget;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
