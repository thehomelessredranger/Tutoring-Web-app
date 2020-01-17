package ca.mcgill.ecse321.backend.dto;

public abstract class UserRoleDto {
  private int idNumber;
  private int userId;

  public UserRoleDto(int id, int userId) {
    this.idNumber = id;
    this.userId = userId;
  }

  public int getIdNumber() {
    return idNumber;
  }
  public void setIdNumber(int id) {
    this.idNumber = id;
  }

  public int getUserId() {
    return userId;
  }
  public void setUserId(int id) {
    this.userId = id;
  }

}
