package peaksoft.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static jakarta.persistence.CascadeType.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instructor_seq")
    @SequenceGenerator(name = "instructor_seq", sequenceName = "instructor_seq", allocationSize = 1)
    private Long id;

    @Column(length = 100000, name = "first_name")
    private String firstName;

    @Column(length = 100000, name = "last_name")
    private String lastName;

    @Column(length = 100000, name = "phone_number")
    private String phoneNumber;

    @Column(length = 100000, name = "email")
    private String email;

    @Column(length = 100000, name = "specialization")
    private String specialization;

    private int students = 0;

    public void plusStudent(Course course1){
        for (Groups group : course1.getGroups()) {
            for (Student student: group.getStudents()) {
                students++;
            }
        }
    }

    public void plus(){
        students++;
    }

    public void minus(){
        students--;
    }

    @ManyToOne(cascade = {MERGE, DETACH, REFRESH}, fetch = FetchType.EAGER)
    private Course course;
}