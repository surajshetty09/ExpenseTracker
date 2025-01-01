import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public void saveTransaction(Transaction txn) {
		System.out.println("In saveTransaction of TransactionDAO");

		Transaction tran = txn;

		if (tran.getAmount() >= 0) {
			tran.setType("I");
		} else {
			tran.setType("E");
		}

		Session session = sessionFactory.openSession();
		org.hibernate.Transaction hibernateTransaction = null;

		try {
			hibernateTransaction = session.beginTransaction();

			// Save or update the transaction
			session.saveOrUpdate(tran);

			// Commit the transaction
			hibernateTransaction.commit();
		} catch (Exception e) {
			if (hibernateTransaction != null) {
				hibernateTransaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> getTransactions() {
		List<Transaction> transactions = null;
		Session session = sessionFactory.openSession();

		try {
			Query query = session.createQuery("FROM Transaction");
			transactions = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return transactions;
	}

	public void deleteTransaction(Long transactionId) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction hibernateTransaction = null;

		try {
			hibernateTransaction = session.beginTransaction();

			Transaction txn = (Transaction) session.get(Transaction.class, transactionId);
			if (txn != null) {
				session.delete(txn);
			}

			// Commit the transaction
			hibernateTransaction.commit();
		} catch (Exception e) {
			if (hibernateTransaction != null) {
				hibernateTransaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> completeText(char type, String value) {
		List<String> results = new ArrayList<>();
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction hibernateTransaction = null;

		try {
			hibernateTransaction = session.beginTransaction();

			Query query = session.createQuery("FROM CompleteOption WHERE type=:type and name LIKE :value");
			query.setParameter("value", "%" + value + "%");
			query.setParameter("type", type);
			List<CompleteOption> completeOptions = query.list();

			for (CompleteOption option : completeOptions) {
				results.add(option.getName());
			}

			hibernateTransaction.commit();
		} catch (Exception e) {
			if (hibernateTransaction != null) {
				hibernateTransaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}

	public static void shutdown() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}
