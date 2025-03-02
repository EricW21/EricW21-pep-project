package Service;
import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAO.AccountDao;
public class AccountService {
    private AccountDao accountDao;
    public AccountService() {
        this.accountDao = new AccountDao();
    }
    public AccountService(AccountDao account) {
        this.accountDao= account;
    }
    public Account newUser(Account account) {
        if (account.password.length()<4) {
            return null;
        }
        if (account.username.length()==0) {
            return null;
        }
        return this.accountDao.newUser(account);
    }
    public Account processLogin(Account account) {
        
        return this.accountDao.processLogin(account);
    }

}
