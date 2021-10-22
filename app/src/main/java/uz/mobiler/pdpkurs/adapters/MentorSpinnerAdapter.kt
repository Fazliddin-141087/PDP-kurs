package uz.mobiler.pdpkurs.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.mobiler.pdpkurs.databinding.ItemMentorSpinnerBinding
import uz.mobiler.pdpkurs.models.Mentor

class MentorSpinnerAdapter(var list: ArrayList<Mentor>):BaseAdapter(){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Mentor {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
           return position.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
         var mentorViewHolder:MentorViewHolder
         if (convertView==null){
            var binding= ItemMentorSpinnerBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
             mentorViewHolder=MentorViewHolder(binding)
         }else{
             mentorViewHolder=MentorViewHolder(ItemMentorSpinnerBinding.bind(convertView))
         }
        mentorViewHolder.itemMentorSpinnerBinding.mentorNameSpinnerTv.text="${list[position].surname} ${list[position].name}"
        return  mentorViewHolder.itemView
    }

    inner class MentorViewHolder{
        var itemView:View
        var itemMentorSpinnerBinding:ItemMentorSpinnerBinding
        constructor(itemMentorSpinnerBinding: ItemMentorSpinnerBinding){
            itemView=itemMentorSpinnerBinding.root
            this.itemMentorSpinnerBinding=itemMentorSpinnerBinding
        }
    }
}