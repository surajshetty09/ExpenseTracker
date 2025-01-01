import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "ExpenseTrackerBean")
//@ViewScoped
//@RequestScoped
@SessionScoped
public class ExpenseTrackerBean implements Serializable {
	private List<Transaction> transactions;
	private TransactionDAO txnDAO;
	private Transaction transaction;

	private List<String> options;

	@PostConstruct
	public void init() {
		System.out.println("PostConstruct Invoked");
		txnDAO = new TransactionDAO();

		transaction = new Transaction();

		transactions = txnDAO.getTransactions();

		System.out.println("PostConstruct Ended");

		options = new ArrayList<>();
		
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public double getTotalIncome() {
		System.out.println("getTotalIncome Triggered");
		double totalIncome = 0.0;
		for (Transaction txn : transactions) {
			if ("I".equals(txn.getType())) {
				totalIncome += txn.getAmount();
			}
		}
		return totalIncome;
	}

	public double getTotalExpense() {
		System.out.println("getTotalExpense Triggered");
		double totalExpense = 0.0;
		for (Transaction txn : transactions) {
			if ("E".equals(txn.getType())) {
				totalExpense += txn.getAmount();
			}
		}
		return totalExpense;
	}

	public double getTotalBalance() {
		double totalCalc = 0.0;
		for (Transaction txn : transactions) {
			totalCalc += txn.getAmount();
		}
		return totalCalc;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	// Reset the transaction for transactionDialog
	public void prepareNewTransaction() {
		System.out.println("Preparing a new transaction");
		this.transaction = new Transaction();
		// this.transaction.setAmount(1);
	}

	// to save a new transaction
	public String saveTransaction() {
		System.out.println("In saveTransaction of ExpenseTrackerBean");

		/*
		 * if (transaction.getAmount() == 0) {
		 * FacesContext.getCurrentInstance().addMessage("transactionDialog:amount", new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Amount Cannot be Zero", null));
		 * return null; }
		 */

		System.out.println("saveTransaction: " + transaction);
		txnDAO.saveTransaction(transaction);
		transactions = txnDAO.getTransactions(); // Refresh the tabview
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Record Added!", "Record Added!"));
		return null; // to Stay on the same page after saving
		// return "mainpage?faces-redirect=true";
	}

	// Delete tran based on tran ID
	public void deleteTransaction(Long transactionId) {
		System.out.println("Deleting record Bean Transaction:" + transactionId);

		if (transactionId != null) {
			txnDAO.deleteTransaction(transactionId);
			transactions = txnDAO.getTransactions(); // Refresh the list after deletion
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Record Deleted!", "Record Deleted!"));
		}
	}

	public List<String> completeText(String query) {
		System.out.println("completeText triggered");
		System.out.println("Get Amount: " + transaction.getAmount());

		char type;
		if (transaction.getAmount() >= 0) {
			type = 'I';
		} else {
			type = 'E';
		}

		return txnDAO.completeText(type, query);
	}
}
