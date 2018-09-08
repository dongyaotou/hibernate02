package cn.nb;

import cn.nb.bean.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeacherTest {
    Transaction transaction=null;//注意这里导入的是hibernate的jar包
    Session session=null;
    @Before
    public void before() {
        //01.读取核心配置文件 configure()底层就是加载了/hibernate.cfg.xml
        Configuration configuration = new Configuration().configure();//注意这里导入的是hibernate的包，不是javax的包
        //02.创建会话工厂 sessionFactory
        SessionFactory factory = configuration.buildSessionFactory();
        //03创建session会话
        session = factory.openSession();
        //04开启事务
         transaction = session.beginTransaction();



    }

    @After
    public void after(){
        //07,提交事务
        transaction.commit();
        //08.关闭会话
        session.close();

    }
    //05   06    这两个步骤是增删改查的基本操作
    @Test
    public  void  addTeacher(){

    //05创建新增的对象
        Teacher teacher=new Teacher();
        teacher.setId(8);
        teacher.setName("赵永强");

        //06.持久话操作
        System.out.println("*************");
      session.save(teacher);
        System.out.println("*********************");



    }


    /**
     *  数据库有对应的数据产生2条sql
     *  一条是查询的sql语句
     *  一条是执行删除的sql语句
     *
     *  01.先根据对象的id 去数据库中查询 看有没有数据
     *  02.如果存在根据id删除指定的信息
     *     如果不存在 只做查询操作
     */
    @Test
    public void deleteTeacher(){
        //创建需要删除的对象
        Teacher teacher=new Teacher();
        teacher.setId(9);//进行测试
        System.out.println("****************");
        //进行删除操作
        session.delete(teacher);
        System.out.println("******************");

    }


    /**
     * 修改
     *  只生成一条update语句，不会进行查询操作
     */
    @Test
    public void updateTeacher(){
        //创建要修改的对象
        Teacher teacher=new Teacher();
        teacher.setId(10);
        teacher.setName("李连杰1");
        System.out.println("***********************");
        session.update(teacher);
        System.out.println("*********************");
    }

    /**
     *
     * 从数据库中查询指定id的信息
     *   get和load的区别
     *
     *   get：
     *     01.在get()立即产生sql语句
     *     02.首先会去hibernate的1级缓存（session）中查询有没有对应的数据，
     *        如果用，直接返回,不去访问数据库，
     *        如果没有，而且我们配置了2级缓存，那么会去2级缓存中查询，
     *        如果2级缓存没有数据，就去访问数据库。
     *     03.如果数据库中有对应的数据 直接返回
     *        数据库中没有对应的数据，则返回null
     */
    @Test
    public void getTeacher(){
        System.out.println("**********************");
        Teacher o = (Teacher) session.get(Teacher.class, 3);
        System.out.println("******************8");
        System.out.println(o);
    }


    /**
     * 验证get会把查询到的数据放进session缓存中
     * 从运行结果中我们会看到只有一条sql语句
     */
    @Test
    public   void getTeacher2(){
        System.out.println("*****************");
        Teacher o = (Teacher) session.get(Teacher.class, 3);
        Teacher o1 = (Teacher) session.get(Teacher.class, 3);
        System.out.println("****************************");
        System.out.println(o);
        System.out.println(o1);



    }
    /**
     * evict  从session中清除指定的对象 单词的意思表示的是清除、收回的意思
     */
    @Test
    public  void  evictTeacher(){
        Teacher teacher1= (Teacher) session.get(Teacher.class,1);
        Teacher teacher2= (Teacher) session.get(Teacher.class,3);
        //清除teacher1
        session.evict(teacher1);
        session.evict(teacher2);
        teacher1 = (Teacher) session.get(Teacher.class, 1);
        teacher1 = (Teacher) session.get(Teacher.class, 3);
       // System.out.println(teacher1);


    }

    /**
     * clear  从session中清空所有对象
     */
    @Test
    public void  clearTeacher(){
        Teacher teacher1= (Teacher) session.get(Teacher.class,1);
        Teacher teacher3= (Teacher) session.get(Teacher.class,3);
        //清空保存在session中的所有对象
        session.clear();
        //在此查询
        teacher1 = (Teacher) session.get(Teacher.class, 1);
        teacher3 = (Teacher) session.get(Teacher.class, 3);
    }
    /**
     * load： 懒加载
     *   01.不会在load的时候 产生sql语句，
     *      用户需要的时候产生
     *  02.首先会去hibernate的1级缓存（session）中查询有没有对应的数据，
     *        如果用，直接返回,不去访问数据库，
     *        如果没有，而且我们配置了2级缓存，那么会去2级缓存中查询，
     *        如果2级缓存没有数据，就去访问数据库。
     *  03.如果数据库中有对应的数据 直接返回
     *        数据库中没有对应的数据，则抛出ObjectNotFoundException
     */

@Test
    public void loadTeacher(){
        Teacher o = (Teacher) session.get(Teacher.class, 8);
        System.out.println("*************************888");
        System.out.println(o);
    }
    /**
     * load也可以使用立即加载
     *  去对应类所在的hbm.xml文件中的class节点上 增加  lazy="false"  立即加载
     */
@Test
    public void loadTeacher2(){
        Teacher o = (Teacher) session.get(Teacher.class, 1);
        System.out.println("********************");
        System.out.println(o);
    }


    }






