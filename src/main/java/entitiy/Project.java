package entitiy;

import javax.persistence.*;

@Entity
public class Project {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "dept_id", referencedColumnName = "id")
  private Department department;

  public Project() {
  }

  public Project(String name) {
    this.name = name;
  }


  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
}
