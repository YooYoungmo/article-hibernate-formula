import entitiy.Department;
import entitiy.Employee;
import entitiy.Project;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

public class CountTest {

  EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

  public void setUpTestFixture() {
    Department departmentA = new Department("연구개발부");
    departmentA.addEmployee(new Employee("B 직원"));
    departmentA.addEmployee(new Employee("C 직원"));
    departmentA.addProject(new Project("D 프로젝트"));
    departmentA.addProject(new Project("E 프로젝트"));
    departmentA.addProject(new Project("F 프로젝트"));

    Department departmentB = new Department("구매부");
    departmentB.addEmployee(new Employee("H 직원"));
    departmentB.addProject(new Project("I 프로젝트"));
    departmentB.addProject(new Project("J 프로젝트"));

    EntityManager em;
    em = emf.createEntityManager();
    EntityTransaction tx = null;
    try {
      tx = em.getTransaction();
      tx.begin();

      em.persist(departmentA);
      em.persist(departmentB);

      tx.commit();


    } catch (Exception e) {
      e.printStackTrace();
      if (tx != null) {
        tx.rollback();
      }
      throw new RuntimeException(e);
    } finally {
      em.close();
    }
  }

  @Test
  public void test() {
    setUpTestFixture();

    EntityManager em = emf.createEntityManager();
    List<Department> departments = em.createQuery("select d from Department as d", Department.class).getResultList();

    departments.forEach(department -> {
      System.out.println("Department : " + department.getName());
      System.out.println("CountOfEmployees : " + department.getCountOfEmployees() + "," + "CountOfProjects : " + department.getCountOfProjects());
    });

    em.close();
  }

  @Test
  public void test_count_by_jpql() {
    setUpTestFixture();

    EntityManager em = emf.createEntityManager();
    List<Department> departments = em.createQuery("select d from Department as d", Department.class).getResultList();

    for (Department department : departments) {
      System.out.println("Department : " + department.getName());
      Query countQuery = em.createQuery("select " +
          "(select count(e) from Employee e where e.department.id = d.id) as emp_cnt, " +
          "(select count(p) from Project p where p.department.id = d.id) as proj_cnt " +
          "from Department d where d.id = :departmentId")
          .setParameter("departmentId", department.getId());

      Object[] singleResult = (Object[])countQuery.getSingleResult();
      System.out.println("CountOfEmployees : " + singleResult[0] + "," + "CountOfProjects : " + singleResult[1]);
    }

    em.close();
  }
}
