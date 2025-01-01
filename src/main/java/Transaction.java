import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TRAN_ID")
	private Long id;
	
	@Column(name = "TRAN_AMOUNT")
	private double amount;
	

	@Column(name="TRAN_DESC")
	private String desc;
	
	@Column(name="TRAN_TYPE")
	private String type;
	
	//Getter setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {						//Created this method to print whole object
		return id+" "+amount+" "+desc+" "+type;
	}
	
}
