package sr.unasat.financialapp.dto;

/**
 * Created by abhoendie on 2/28/2017.
 */

public class Budget {

    private String budgetAmount;
    private String budgetDescreption;

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(String budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getBudgetDescreption() {
        return budgetDescreption;
    }

    public void setBudgetDescreption(String budgetDescreption) {
        this.budgetDescreption = budgetDescreption;
    }
}
