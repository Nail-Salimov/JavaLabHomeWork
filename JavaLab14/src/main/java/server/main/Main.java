package server.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import server.configs.JpaContext;
import server.entities.child.Child;
import server.entities.human.Human;
import server.entities.students.CollegeStudent;
import server.repositories.ChildRepository;
import server.repositories.CollegeStudentRepository;
import server.repositories.HumanRepository;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaContext.class);
        HumanRepository humanRepository = context.getBean(HumanRepository.class);
        CollegeStudentRepository collegeStudentRepository = context.getBean(CollegeStudentRepository.class);
        ChildRepository childRepository = context.getBean(ChildRepository.class);

        CollegeStudent managerCollegeStudent = CollegeStudent.builder()
                .college("Manage College")
                .name("Alex")
                .age(20)
                .read(true)
                .build();

        CollegeStudent electricianCollegeStudent = CollegeStudent.builder()
                .college("Electrician College")
                .name("Edger")
                .age(19)
                .read(true)
                .build();

        Human human = Human.builder()
                .name("Ostolf")
                .age(34)
                .build();

        Child child = Child.builder()
                .age(7)
                .name("Bart")
                .read(false)
                .build();

        humanRepository.save(human);
        humanRepository.save(child);
        humanRepository.save(managerCollegeStudent);

        collegeStudentRepository.save(electricianCollegeStudent);

        System.out.println("---Humans----");
        List<Human> humans = humanRepository.findAll();
        for (Human h : humans){
            System.out.println(h.getId() + " " + h.getName() + " " + h.getAge());
        }

        System.out.println("---Children---");
        List<Child> children = childRepository.findAll();
        for (Child c : children){
            System.out.println(c.getId() + " " + c.getName() + " " + c.getRead());
        }

        System.out.println("---CollegeStudents---");
        List<CollegeStudent> collegeStudents = collegeStudentRepository.findAll();
        for (CollegeStudent student : collegeStudents){
            System.out.println(student.getId() + " " + student.getName() + " " + student.getCollege());
        }
    }
}
