package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //以下所有的*都代表变量

    @Query("select e from Employee e where e.name =?1")
        //1.查询名字是*的第一个employee
    Employee findFirstByName(@Param("name") String name);

    //@Query(value = "select * from Employee where name like %?1% and salary > ?2 limit 1", nativeQuery = true)
    @Query("select e from Employee e where name like %?1% and salary >?2")
    //2.找出Employee表中第一个姓名包含`*`字符并且薪资大于*的雇员个人信息
    Employee findByNameLikeAndSalaryGreaterThan(@Param("name") String name,@Param("salary") int salary);

    @Query(value = "select e.name from Employee e where e.companyId=?1 order by e.salary desc limit 1",nativeQuery = true)
        //3.找出一个薪资最高且公司ID是*的雇员以及该雇员的姓名
    String getTopByCompanyIdOrderBySalaryDesc(@Param("companyId") int companyId);

    //4.实现对Employee的分页查询，每页两个数据
    Page<Employee> findAll(Pageable pageable);

    @Query( "select c.companyName from Employee e inner join Company c on e.companyId=c.id where e.name=?1")
        //5.查找**的所在的公司的公司名称
    String findCompanyNameByName(@Param("name") String name);

    @Modifying
    @Query("update Employee e set e.name = ?1 where e.name = ?2")
        //6.将*的名字改成*,输出这次修改影响的行数
    int updateName(@Param("old_name") String old_name,@Param("new_name") String new_name);

    @Modifying
    @Query("delete from Employee where name = ?1")
        //7.删除姓名是*的employee
    void deleteByName(@Param("name") String name);
}
