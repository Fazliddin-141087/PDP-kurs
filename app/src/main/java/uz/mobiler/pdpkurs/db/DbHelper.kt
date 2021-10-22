package uz.mobiler.pdpkurs.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.mobiler.pdpkurs.models.Course
import uz.mobiler.pdpkurs.models.Group
import uz.mobiler.pdpkurs.models.Mentor
import uz.mobiler.pdpkurs.models.Student
import uz.mobiler.pdpkurs.utils.Constant

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION),
    DataBaseService {

    override fun onCreate(db: SQLiteDatabase?) {
        val studentTableQuery =
            "create table ${Constant.STUDENT_TABLE} (${Constant.STUDENT_ID} integer not null primary key autoincrement unique, ${Constant.STUDENT_SURNAME} text not null,${Constant.STUDENT_NAME} text not null,${Constant.STUDENT_FATHER_NAME} text not null, ${Constant.STUDENT_DATE} numeric not null, ${Constant.STUDENT_GROUP_ID} integer not null ,foreign key(${Constant.STUDENT_GROUP_ID}) references ${Constant.GROUP_TABLE}(${Constant.GROUP_ID}))"
        val groupTableQuery =
            "create table ${Constant.GROUP_TABLE} (${Constant.GROUP_ID} integer not null primary key autoincrement unique, ${Constant.GROUP_NAME} text not null,${Constant.GROUP_COURSE_ID} integer not null,${Constant.GROUP_MENTOR_ID} integer not null, ${Constant.GROUP_TIME} numeric not null, ${Constant.GROUP_DAYS} numeric not null,${Constant.GROUP_IS_OPENED} text not null, foreign key(${Constant.GROUP_COURSE_ID}) references ${Constant.COURSE_TABLE}(${Constant.COURSE_ID}), foreign key (${Constant.GROUP_MENTOR_ID}) references ${Constant.MENTOR_TABLE}(${Constant.MENTOR_ID}))"
        val mentorTableQuery =
            "create table ${Constant.MENTOR_TABLE} (${Constant.MENTOR_ID} integer not null primary key autoincrement unique, ${Constant.MENTOR_SURNAME} text not null, ${Constant.MENTOR_NAME} text not null, ${Constant.MENTOR_FATHER_NAME} text not null, ${Constant.MENTOR_COURSE_ID} integer not null, foreign key(${Constant.MENTOR_COURSE_ID}) references ${Constant.COURSE_TABLE}(${Constant.COURSE_ID}))"
        val courseTableQuery =
            "create table ${Constant.COURSE_TABLE} (${Constant.COURSE_ID} integer not null primary key autoincrement unique, ${Constant.COURSE_NAME} text not null,${Constant.COURSE_DESCRIPTION} text not null)"

        db?.execSQL(studentTableQuery)
        db?.execSQL(groupTableQuery)
        db?.execSQL(mentorTableQuery)
        db?.execSQL(courseTableQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun insertStudent(student: Student) {
        val database = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(Constant.STUDENT_SURNAME, student.surname)
        contentValues.put(Constant.STUDENT_NAME, student.name)
        contentValues.put(Constant.STUDENT_FATHER_NAME, student.fatherName)
        contentValues.put(Constant.STUDENT_DATE, student.date)
        contentValues.put(Constant.STUDENT_GROUP_ID, student.group?.id)
        database.insert(Constant.STUDENT_TABLE, null, contentValues)
    }

    override fun insertGroup(group: Group) {
        val database = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(Constant.GROUP_NAME, group.name)
        contentValues.put(Constant.GROUP_COURSE_ID, group.course?.id)
        contentValues.put(Constant.GROUP_MENTOR_ID, group.mentor?.id)
        contentValues.put(Constant.GROUP_TIME, group.time)
        contentValues.put(Constant.GROUP_DAYS, group.days)
        contentValues.put(Constant.GROUP_IS_OPENED, group.isOpened)
        database.insert(Constant.GROUP_TABLE, null, contentValues)
    }

    override fun insertMentor(mentor: Mentor) {
        val database = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(Constant.MENTOR_SURNAME, mentor.surname)
        contentValues.put(Constant.MENTOR_NAME, mentor.name)
        contentValues.put(Constant.MENTOR_FATHER_NAME, mentor.fatherName)
        contentValues.put(Constant.MENTOR_COURSE_ID, mentor.course?.id)
        database.insert(Constant.MENTOR_TABLE, null, contentValues)
    }

    override fun insertCourse(course: Course) {
        val database = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(Constant.COURSE_NAME, course.name)
        contentValues.put(Constant.COURSE_DESCRIPTION, course.descreption)
        database.insert(Constant.COURSE_TABLE, null, contentValues)
    }

    override fun getAllStudents(): ArrayList<Student> {
        var list = ArrayList<Student>()
        val query = "select * from ${Constant.STUDENT_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor!=null){
            if (cursor.moveToFirst()) {
                do {
                    val student = Student()
                    student.id = cursor.getInt(0)
                    student.surname = cursor.getString(1)
                    student.name = cursor.getString(2)
                    student.fatherName = cursor.getString(3)
                    student.date = cursor.getString(4)
                    student.group = getGroupObjectById(cursor.getInt(5))
                    list.add(student)
                } while (cursor.moveToNext())
            }
        }
        return list
    }

    override fun getAllGroups(): ArrayList<Group> {
        var list = ArrayList<Group>()
        val query = "select * from ${Constant.GROUP_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val group = Group()
                group.id = cursor.getInt(0)
                group.name = cursor.getString(1)
                group.course = getCourseById(cursor.getInt(2))
                group.mentor = getMentorById(cursor.getInt(3))
                group.time = cursor.getString(4)
                group.days = cursor.getString(5)
                group.isOpened = cursor.getString(6)
                list.add(group)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllMentors(): ArrayList<Mentor> {
        var list = ArrayList<Mentor>()
        val query = "select * from ${Constant.MENTOR_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentor = Mentor()
                mentor.id = cursor.getInt(0)
                mentor.surname = cursor.getString(1)
                mentor.name = cursor.getString(2)
                mentor.fatherName = cursor.getString(3)
                mentor.course = getCourseById(cursor.getInt(4))
                list.add(mentor)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllCourses(): ArrayList<Course> {
        var list = ArrayList<Course>()
        val query = "select * from ${Constant.COURSE_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val course = Course()
                course.id = cursor.getInt(0)
                course.name = cursor.getString(1)
                course.descreption = cursor.getString(2)
                list.add(course)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getGroupByType(isOpened: String): ArrayList<Group> {
        var list = ArrayList<Group>()
        val query =
            "select * from  ${Constant.GROUP_TABLE} where ${Constant.GROUP_IS_OPENED}=$isOpened"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val group = Group()
                group.id = cursor.getInt(0)
                group.name = cursor.getString(1)
                group.course = getCourseById(cursor.getInt(2))
                group.mentor = getMentorById(cursor.getInt(3))
                group.time = cursor.getString(4)
                group.days = cursor.getString(5)
                group.isOpened = cursor.getString(6)
                list.add(group)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getCourseById(id: Int): Course {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.COURSE_TABLE,
            arrayOf(
                Constant.COURSE_ID,
                Constant.COURSE_NAME,
                Constant.COURSE_DESCRIPTION
            ),
            "${Constant.COURSE_ID}=?",
            arrayOf("$id"),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        val course = Course()
        course.id = cursor.getInt(0)
        course.name = cursor.getString(1)
        course.descreption = cursor.getString(2)
        return course
    }

    override fun getMentorById(id: Int): Mentor {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.MENTOR_TABLE,
            arrayOf(
                Constant.MENTOR_ID,
                Constant.MENTOR_SURNAME,
                Constant.MENTOR_NAME,
                Constant.MENTOR_FATHER_NAME,
                Constant.MENTOR_COURSE_ID
            ),
            "${Constant.MENTOR_ID}=?",
            arrayOf("$id"),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        val mentor = Mentor()
        mentor.id = cursor.getInt(0)
        mentor.surname = cursor.getString(1)
        mentor.name = cursor.getString(2)
        mentor.fatherName = cursor.getString(3)
        mentor.course = getCourseById(cursor.getInt(4))
        return mentor
    }

    override fun getGroupObjectById(id: Int): Group {
        var group=Group()
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.GROUP_TABLE,
            arrayOf(
                Constant.GROUP_ID,
                Constant.GROUP_NAME,
                Constant.GROUP_COURSE_ID,
                Constant.GROUP_MENTOR_ID,
                Constant.GROUP_TIME,
                Constant.GROUP_DAYS,
                Constant.GROUP_IS_OPENED
            ),
            "${Constant.GROUP_ID}=?",
            arrayOf("$id"),
            null,
            null,
            null,
            null
        )
        if (cursor!=null && cursor.count>0){
            cursor.moveToFirst()
            group.id = cursor.getInt(0)
            group.name = cursor.getString(1)
            group.course = getCourseById(cursor.getInt(2))
            group.mentor = getMentorById(cursor.getInt(3))
            group.time = cursor.getString(4)
            group.days = cursor.getString(5)
            group.isOpened = cursor.getString(6)
        }
        return group
    }

    override fun deleteStudent(student: Student) {
        val database = this.writableDatabase
        database.delete(
            Constant.STUDENT_TABLE,
            "${Constant.STUDENT_ID}=?",
            arrayOf("${student.id}")
        )
    }

    override fun deleteGroups(group: Group) {
        val database = this.writableDatabase
        database.delete(Constant.GROUP_TABLE, "${Constant.GROUP_ID}=?", arrayOf("${group.id}"))
    }

    override fun deleteMentors(mentor: Mentor) {
        val database = this.writableDatabase
        database.delete(Constant.MENTOR_TABLE, "${Constant.MENTOR_ID}=?", arrayOf("${mentor.id}"))
    }

    override fun deleteCourses(course: Course) {
        val database = this.writableDatabase
        database.delete(Constant.COURSE_TABLE, "${Constant.COURSE_ID}=?", arrayOf("${course.id}"))
    }

    override fun updateStudent(student: Student): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.STUDENT_ID, student.id)
        contentValues.put(Constant.STUDENT_SURNAME, student.surname)
        contentValues.put(Constant.STUDENT_NAME, student.name)
        contentValues.put(Constant.STUDENT_FATHER_NAME, student.fatherName)
        contentValues.put(Constant.STUDENT_DATE, student.date)
        contentValues.put(Constant.STUDENT_GROUP_ID, student.group?.id)
        return database.update(
            Constant.STUDENT_TABLE,
            contentValues,
            "${Constant.STUDENT_ID}=?",
            arrayOf("${student.id}")
        )
    }

    override fun updateGroups(group: Group): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.GROUP_ID, group.id)
        contentValues.put(Constant.GROUP_NAME, group.name)
        contentValues.put(Constant.GROUP_COURSE_ID, group.course?.id)
        contentValues.put(Constant.GROUP_MENTOR_ID, group.mentor?.id)
        contentValues.put(Constant.GROUP_TIME, group.time)
        contentValues.put(Constant.GROUP_DAYS, group.days)
        contentValues.put(Constant.GROUP_IS_OPENED, group.isOpened)
        return database.update(
            Constant.GROUP_TABLE,
            contentValues,
            "${Constant.GROUP_ID}=?",
            arrayOf("${group.id}")
        )
    }

    override fun updateMentors(mentor: Mentor): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.MENTOR_ID, mentor.id)
        contentValues.put(Constant.MENTOR_SURNAME, mentor.surname)
        contentValues.put(Constant.MENTOR_NAME, mentor.name)
        contentValues.put(Constant.MENTOR_FATHER_NAME, mentor.fatherName)
        contentValues.put(Constant.MENTOR_COURSE_ID, mentor.course?.id)
        return database.update(
            Constant.MENTOR_TABLE,
            contentValues,
            "${Constant.MENTOR_ID}=?",
            arrayOf("${mentor.id}")
        )
    }

    override fun updateCourses(course: Course): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.COURSE_ID, course.id)
        contentValues.put(Constant.STUDENT_NAME, course.name)
        contentValues.put(Constant.COURSE_DESCRIPTION, course.descreption)
        return database.update(
            Constant.COURSE_TABLE,
            contentValues,
            "${Constant.COURSE_ID}=?",
            arrayOf("${course.id}")
        )
    }
}