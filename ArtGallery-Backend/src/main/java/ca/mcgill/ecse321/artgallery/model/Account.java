package ca.mcgill.ecse321.artgallery.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class Account
{

	public enum AccountStatus { New, Blocked, Active, Inactive }


	//Account Attributes
	@Column(name="account_status")
	private AccountStatus status;
	@Column(name="name")
	private String name;
	@Column(name="password")
	private String password;

	@Id
	@Column(name="email", unique=true)
	private String email;


	public boolean setStatus(AccountStatus aStatus)
	{
		boolean wasSet = false;
		status = aStatus;
		wasSet = true;
		return wasSet;
	}

	public boolean setName(String aName)
	{
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	public boolean setPassword(String aPassword)
	{
		boolean wasSet = false;
		password = aPassword;
		wasSet = true;
		return wasSet;
	}

	public boolean setEmail(String aEmail)
	{
		boolean wasSet = false;
		String anOldEmail = getEmail();
		if (anOldEmail != null && anOldEmail.equals(aEmail)) {
			return true;
		}
		email = aEmail;
		wasSet = true;
		return wasSet;
	}

	public AccountStatus getStatus()
	{
		return status;
	}

	public String getName()
	{
		return name;
	}

	public String getPassword()
	{
		return password;
	}

	public String getEmail()
	{
		return email;
	}
}