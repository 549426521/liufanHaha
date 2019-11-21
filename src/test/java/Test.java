
import com.lyf.po.StudentEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;

public class Test {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    //开始
    @org.junit.Before
    public void Before(){
        //1.获取SessionFactiory 会话工厂
        sessionFactory=new Configuration().configure().buildSessionFactory();
        //2.生产会话  通过openSession获取session对象
        session=sessionFactory.openSession();
        //3.开启事务
        transaction = session.beginTransaction();
    }

    @org.junit.Test
    public void textgetSelect() {
        //执行查询 get(要查询的实体类-类型,参数)
        //执行查询(查询单条数据)
        StudentEntity emp1 = session.get(StudentEntity.class, 1);
        System.out.println(emp1);
    }

    @org.junit.Test
    public void textloadSelect() {
        //执行查询(查询单条数据)
        StudentEntity emp2 = session.load(StudentEntity.class, 1);
        System.out.println(emp2);
    }

    @org.junit.Test
    public void textcreateQuerySelect() {
        //执行查询(查询单条数据)
        Query query = session.createQuery("from StudentEntity where id=?");//实体类的名字
        query.setParameter(0,2); //占位符的形式 id=?
        //Query query = session.createQuery("from Emp where id:ids");
        //query.setParameter("ids",4); //自定义占位符
        Object o = query.uniqueResult(); //只针对于只有一条语句时才能使用
        System.out.println(o);
    }

    @org.junit.Test
    public void textcreateQuerySelectAll() {
        //查询全部         Emp实体类的名字 也可以写com.lry.po.Emp
        Query from_emp = session.createQuery("from StudentEntity");
        List list = from_emp.list();
        for (Object o1 : list) {
            System.out.println(o1);
        }
    }

    @org.junit.Test
    public void textcreateSQLQuerySelectAll() {
        //查询全部
        SQLQuery sqlQuery=session.createSQLQuery("select * from StudentEntity");
        List list1 = sqlQuery.list();
        for (Object o1 : list1) {
            System.out.println(o1);
        }
    }

    //增加
    @org.junit.Test
    public void textinsert() {
        StudentEntity emp=new StudentEntity(1,"sa");
        Serializable save = session.save(emp);
        System.out.println(save);
    }

    //删除
    @org.junit.Test
    public void textdelete() {
        //Hibernaye中的删除 是先查询是否存在对象然后删除
        StudentEntity emp=session.get(StudentEntity.class,1);
        if(emp!=null){
            session.delete(emp);
        }
    }

    //修改
    @org.junit.Test
    public void textupdate() {
        StudentEntity Emp=session.get(StudentEntity.class,3);
        Emp.setId(7);
        Emp.setName("aa");
        Transaction transaction = session.beginTransaction();
        session.update(Emp);
    }
    @org.junit.Test
    public void textSaveOrUpdate() {
        StudentEntity stu=new StudentEntity();
        stu.setName("ha");
        session.saveOrUpdate(stu);
    }
    //结束
    @org.junit.After
    public void After(){
        transaction.commit();//关闭
        session.close();//关闭
        sessionFactory.close();//关闭
    }
}