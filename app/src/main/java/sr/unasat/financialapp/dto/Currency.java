package sr.unasat.financialapp.dto;

public class Currency {

    private int id;
    private String country;
    private String currency;
    private String logo;

    public Currency() {
    }

    public Currency(int id, String country, String currency, String logo) {
        this.id = id;
        this.country = country;
        this.currency = currency;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
