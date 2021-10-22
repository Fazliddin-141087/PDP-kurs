package uz.mobiler.pdpkurs.utils

object Constant {

    const val DATABASE_NAME = "pdp_db"
    const val DATABASE_VERSION = 1

    const val STUDENT_TABLE = "students"
    const val STUDENT_ID = "id"
    const val STUDENT_SURNAME = "surname"
    const val STUDENT_NAME = "name"
    const val STUDENT_FATHER_NAME = "father_name"
    const val STUDENT_DATE = "date"
    const val STUDENT_GROUP_ID = "group_id"

    const val GROUP_TABLE = "groups"
    const val GROUP_ID = "id"
    const val GROUP_NAME = "name"
    const val GROUP_COURSE_ID = "course_id"
    const val GROUP_MENTOR_ID = "mentor_id"
    const val GROUP_TIME = "time"
    const val GROUP_DAYS = "days"
    const val GROUP_IS_OPENED = "is_opened"

    const val MENTOR_TABLE = "mentors"
    const val MENTOR_ID = "id"
    const val MENTOR_SURNAME = "surname"
    const val MENTOR_NAME = "name"
    const val MENTOR_FATHER_NAME = "father_name"
    const val MENTOR_COURSE_ID = "course_id"

    const val COURSE_TABLE = "courses"
    const val COURSE_ID = "id"
    const val COURSE_NAME = "name"
    const val COURSE_DESCRIPTION = "description"

}