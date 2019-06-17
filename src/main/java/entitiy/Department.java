package entitiy;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
  private List<Employee> employees = new ArrayList<>();

  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
  private List<Project> projects = new ArrayList<>();

  @Formula("(select count(*) from employee e where e.dept_id = id)")
  private int countOfEmployees;

  @Formula("(select count(*) from project p where p.dept_id = id)")
  private int countOfProjects;

  public Department() {
  }

  public Long getId() {
    return id;
  }

  public Department(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void addEmployee(Employee employee) {
    this.employees.add(employee);

    if(employee.getDepartment() != this) {
      employee.setDepartment(this);
    }
  }

  public void addProject(Project project) {
    this.projects.add(project);

    if(project.getDepartment() != this) {
      project.setDepartment(this);
    }
  }

  public int getCountOfEmployees() {
//    return this.employees.size();
    return this.countOfEmployees;
  }

  public int getCountOfProjects() {
//    return this.projects.size();
    return this.countOfProjects;
  }
}
