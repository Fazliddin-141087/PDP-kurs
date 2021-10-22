package uz.mobiler.pdpkurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.pdpkurs.databinding.ItemStudentRvBinding
import uz.mobiler.pdpkurs.models.Student

class StudentRvAdapter(var sutdentList: ArrayList<Student>) :RecyclerView.Adapter<StudentRvAdapter.Vh>() {

    var deleteItemOnClickListener:DeleteItemOnClickListener?=null
    fun setDelete(deleteItemOnClickListener: DeleteItemOnClickListener){
       this.deleteItemOnClickListener=deleteItemOnClickListener
   }

    var editItemOnClickListener:EditItemOnClickListener?=null
    fun setEdit(editItemOnClickListener: EditItemOnClickListener){
        this.editItemOnClickListener=editItemOnClickListener
    }

    inner class Vh(var itemStudentRvBinding: ItemStudentRvBinding) :RecyclerView.ViewHolder(itemStudentRvBinding.root) {
        fun onBind(student: Student,position: Int){
            itemStudentRvBinding.studentNameTv.text="${student.name} ${student.surname}"
            itemStudentRvBinding.studentFatherNameTv.text=student.fatherName
            itemStudentRvBinding.studentDateTv.text = student.date

            itemStudentRvBinding.editItem.setOnClickListener {
                editItemOnClickListener!!.editOnClick(student, position)
            }
            itemStudentRvBinding.deleteItem.setOnClickListener {
                deleteItemOnClickListener!!.deleteOnClick(student, position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemStudentRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(sutdentList[position],position)
    }

    override fun getItemCount(): Int {
        return sutdentList.size
    }

  interface  DeleteItemOnClickListener{
        fun deleteOnClick(student: Student,position: Int)
    }
    interface EditItemOnClickListener{
        fun editOnClick(student: Student,position: Int)
    }
}