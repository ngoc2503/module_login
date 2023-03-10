package backend;

public class User {
	private String userid;
	private String fistname;
	private String lastname;
	private String salt;
	private String password;
	private boolean islooked = false;
	private int faillogin;
	private String optsecret;
	private int label;
	public User() {
		
	}
	
	public User(String userid, String fistname, String lastname, String salt, String password, String optsecret) {
		this.userid = userid;
		this.fistname = fistname;
		this.lastname = lastname;
		this.salt = salt;
		this.password = password;
		this.islooked = false;
		this.faillogin = 0;
		this.optsecret = optsecret;
		this.label = 4;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFistname() {
		return fistname;
	}
	public void setFistname(String fistname) {
		this.fistname = fistname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isIslooked() {
		return islooked;
	}
	public void setIslooked(boolean islooked) {
		this.islooked = islooked;
	}
	public int getFaillogin() {
		return faillogin;
	}
	public void setFaillogin(int faillogin) {
		this.faillogin = faillogin;
	}
	public String getOptsecret() {
		return optsecret;
	}
	public void setOptsecret(String optsecret) {
		this.optsecret = optsecret;
	}
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	
	
}
