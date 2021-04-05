package ca.mcgill.ecse321.artgallery.dto;

//import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;

public abstract class AccountDto {
	
	public enum AccountStatus { New, Blocked, Active, Inactive }
	
	protected AccountStatus status;
	protected String name;
	protected String password;
	protected String email;
	
	// Default Constructor
	public AccountDto() {
		
	}
	
	public AccountDto(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.status = AccountStatus.New;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public AccountStatus getAccountStatus() {
		return status;
	}
	

	public void setStatus(AccountStatus aStatus)
	{
		this.status = aStatus;
	}

	public void setName(String aName)
	{
		this.name = aName;
	}

	public void setPassword(String aPassword)
	{
		this.password = aPassword;
	}

	public void setEmail(String aEmail)
	{
		this.email = aEmail;
	}

}
