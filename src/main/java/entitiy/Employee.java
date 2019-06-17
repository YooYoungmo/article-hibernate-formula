package entitiy;

import javax.persistence.*;

@Entity
public class Employee {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "dept_id", referencedColumnName = "id")
  private Department department;

  public Employee() {
  }

  public Employee(String name) {
    this.name = name;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
}
