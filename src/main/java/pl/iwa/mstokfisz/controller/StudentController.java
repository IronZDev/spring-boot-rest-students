package pl.iwa.mstokfisz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.iwa.mstokfisz.AlreadyExistsException;
import pl.iwa.mstokfisz.NotFoundException;
import pl.iwa.mstokfisz.model.Mark;
import pl.iwa.mstokfisz.model.Student;
import pl.iwa.mstokfisz.model.request.AddMarkRequest;
import pl.iwa.mstokfisz.model.request.AddStudentRequest;
import pl.iwa.mstokfisz.repository.MarkRepository;
import pl.iwa.mstokfisz.repository.StudentRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/students")
public class StudentController {
    private StudentRepository studentRepository;
    private MarkRepository markRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository, MarkRepository markRepository){
        this.studentRepository = studentRepository;
        this.markRepository = markRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Student addStudent(@RequestBody AddStudentRequest addStudentRequest)
    {
        for(Student stud : studentRepository.findAll()) {
            if(stud.getStudentID().equals(addStudentRequest.getStudentID())) {
                System.out.println("Already exists!");
                throw new AlreadyExistsException("Student with this student ID already exists!");
            }
        }
        Student student = new Student();
        student.setFirstname(addStudentRequest.getFirstname());
        student.setLastname(addStudentRequest.getLastname());
        student.setStudentID(addStudentRequest.getStudentID());
        return studentRepository.save(student);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteStudent (@PathVariable("id") long id) {
        System.out.println(id);
        if (!studentRepository.existsById(id)) {
            System.out.println("Student not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CleanUpDatabase(studentRepository.getOne(id));
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}/marks", method = RequestMethod.POST)
    public Mark addMark(@PathVariable("id") long id, @RequestBody AddMarkRequest addMarkRequest) {
        System.out.println("test1");
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Student not found!");
        }
        Mark mark = new Mark();
        mark.setValue(addMarkRequest.getValue());
        mark.setSubject(addMarkRequest.getSubject());
        mark.setWeight(addMarkRequest.getWeight());
        markRepository.save(mark);
        System.out.println(mark.getId());
        Student student = studentRepository.getOne(id);
        student.getMarks().add(mark);
        studentRepository.save(student);
        return mark;
    }

    @RequestMapping(value = "/{id}/marks/{markID}", method = RequestMethod.PUT)
    public Mark editMark(@PathVariable("id") long id, @PathVariable("markID") long markID, @RequestBody AddMarkRequest addMarkRequest) {
        if (!studentRepository.existsById(id) || !markRepository.existsById(markID)) {
            throw new NotFoundException("Student or mark not found!");
        }
        Mark mark = markRepository.getOne(markID);
        mark.setValue(addMarkRequest.getValue());
        mark.setWeight(addMarkRequest.getWeight());
        mark.setSubject(addMarkRequest.getSubject());
        return markRepository.save(mark);
    }

    @RequestMapping(value = "/{id}/marks/{markID}", method = RequestMethod.DELETE)
    public ResponseEntity<Mark> deleteMark(@PathVariable("id") long id, @PathVariable("markID") long markID) {
        if (!studentRepository.existsById(id) || !markRepository.existsById(markID)) {
            System.out.println("Student or mark not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Student student = studentRepository.getOne(id);
        Mark mark = markRepository.getOne(markID);
        if (!student.getMarks().contains(mark)) {
            System.out.println("This student does not contain such mark!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        student.getMarks().remove(mark);
        markRepository.delete(mark);
        studentRepository.save(student);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void CleanUpDatabase(Student student) {
        for (Mark mark : student.getMarks()) {
            markRepository.delete(mark);
        }
    }
}
