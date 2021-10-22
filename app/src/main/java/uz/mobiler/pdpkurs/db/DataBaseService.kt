package uz.mobiler.pdpkurs.db

import uz.mobiler.pdpkurs.models.Course
import uz.mobiler.pdpkurs.models.Group
import uz.mobiler.pdpkurs.models.Mentor
import uz.mobiler.pdpkurs.models.Student

interface DataBaseService {

    fun insertStudent(student: Student)
    fun insertGroup(group: Group)
    fun insertMentor(mentor: Mentor)
    fun insertCourse(course: Course)

    fun getAllStudents():ArrayList<Student>
    fun getAllGroups():ArrayList<Group>
    fun getAllMentors():ArrayList<Mentor>
    fun getAllCourses():ArrayList<Course>

    fun getGroupByType(isOpened:String):ArrayList<Group>

    fun getCourseById(id:Int):Course
    fun getMentorById(id:Int):Mentor
    fun getGroupObjectById(id: Int):Group

    fun deleteStudent(student: Student)
    fun deleteGroups(group: Group)
    fun deleteMentors(mentor: Mentor)
    fun deleteCourses(course: Course)

    fun updateStudent(student: Student):Int
    fun updateGroups(group: Group):Int
    fun updateMentors(mentor: Mentor):Int
    fun updateCourses(course: Course):Int
}