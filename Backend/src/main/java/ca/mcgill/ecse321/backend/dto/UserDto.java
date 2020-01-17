package ca.mcgill.ecse321.backend.dto;

public class UserDto {


	private String name;
	private Integer idNumber;
//	private String password;
	private UserRoleDto userRole;

	public UserDto() {

	}

//	public UserDto(String Name, String Password, UserRoleDto Role, int id) {
//		this.idNumber = id;
//		this.name = Name;
////		this.password = Password;
//		this.userRole = Role;
//	}
	public UserDto(String Name, UserRoleDto Role, int id) {
		this.idNumber = id;
		this.name = Name;
//		this.password = Password;
		this.userRole = Role;
	}

	public String getName() {
		return name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public Integer getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(Integer ID) {
		this.idNumber = ID;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String Password) {
//		this.password = Password;
//	}

	public void setUserRole(UserRoleDto Role) {
		this.userRole = Role;
	}
	public UserRoleDto getUserRole() {
		return this.userRole;
	}
}
