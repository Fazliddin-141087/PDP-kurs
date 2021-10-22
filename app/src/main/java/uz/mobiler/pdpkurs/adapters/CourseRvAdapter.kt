package uz.mobiler.pdpkurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.pdpkurs.databinding.ItemCourseRvBinding
import uz.mobiler.pdpkurs.models.Course
import uz.mobiler.pdpkurs.models.Group
import uz.mobiler.pdpkurs.models.Mentor
import uz.mobiler.pdpkurs.models.Student

class CourseRvAdapter(var list: ArrayList<Course>,var myItemOnClickListener: MyItemOnClickListener) : RecyclerView.Adapter<CourseRvAdapter.Vh>() {

    var groupList: ArrayList<Group>? = null
    fun setGroup(groupList: ArrayList<Group>) {
        this.groupList = groupList
    }

    var mentorList: ArrayList<Mentor>? = null
    fun setMentor(mentorList: ArrayList<Mentor>) {
        this.mentorList = mentorList
    }

    var studentList: ArrayList<Student>? = null
    fun setStudent(studentList: ArrayList<Student>) {
        this.studentList = studentList
    }


    inner class Vh(var itemCourseRvBinding: ItemCourseRvBinding) :
        RecyclerView.ViewHolder(itemCourseRvBinding.root) {
        fun onBind(course: Course, position: Int) {
            itemCourseRvBinding.courseNameTv.text = course.name

            itemCourseRvBinding.root.setOnClickListener {
                myItemOnClickListener.myOnItemClick(course, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCourseRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface MyItemOnClickListener{
        fun myOnItemClick(course: Course,position: Int)
    }

}