package uz.mobiler.pdpkurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.pdpkurs.databinding.ItemMentorRvBinding
import uz.mobiler.pdpkurs.models.Mentor

class MentorRvAdapter(var mentorList: ArrayList<Mentor>) :
    RecyclerView.Adapter<MentorRvAdapter.Vh>() {

    var editItemOnClickListener: EditItemOnClickListener? = null
    fun setEditOnClick(editItemOnClickListener: EditItemOnClickListener) {
        this.editItemOnClickListener = editItemOnClickListener
    }

    var deleteItemOnClickListener: DeleteItemOnClickListener? = null
    fun setDeleteOnClick(deleteItemOnClickListener: DeleteItemOnClickListener) {
        this.deleteItemOnClickListener = deleteItemOnClickListener
    }

    inner class Vh(var itemMentorRvBinding: ItemMentorRvBinding) :
        RecyclerView.ViewHolder(itemMentorRvBinding.root) {
        fun onBind(mentor: Mentor, position: Int) {
            itemMentorRvBinding.mentorNameTv.text = "${mentor.name} ${mentor.surname}"
            itemMentorRvBinding.mentorFatherNameTv.text = mentor.fatherName

            itemMentorRvBinding.editItem.setOnClickListener {
                editItemOnClickListener!!.editOnClick(mentor, position)
            }

            itemMentorRvBinding.deleteItem.setOnClickListener {
                deleteItemOnClickListener!!.deleteOnClik(mentor, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMentorRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(mentorList[position], position)
    }

    override fun getItemCount(): Int {
        return mentorList.size
    }

    interface DeleteItemOnClickListener {
        fun deleteOnClik(mentor: Mentor, position: Int)
    }

    interface EditItemOnClickListener {
        fun editOnClick(mentor: Mentor, position: Int)
    }
}