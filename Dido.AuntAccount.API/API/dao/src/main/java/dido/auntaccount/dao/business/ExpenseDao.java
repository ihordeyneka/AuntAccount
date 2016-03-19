package dido.auntaccount.dao.business;

import dido.auntaccount.entities.Expense;
import dido.auntaccount.entities.User;

import java.util.List;

/**
 * Created by orysiadeyneka on 19.03.16.
 */
public interface ExpenseDao {

    public Expense saveExpense(User user);

    public Expense getExpenseById(Long id);

    public List<Expense> getExpensesByUserId(Long userId);

    public void deleteExpenseById(Long id);

    public void deleteExpensesByUserId(Long id);

}
