package uz.mobiler.pdpkurs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.mobiler.pdpkurs.databinding.ItemGroupSpinnerBinding
import uz.mobiler.pdpkurs.models.Group

class GroupSpinnerAdapter(var list: ArrayList<Group>):BaseAdapter(){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Group {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
           return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
         var groupViewHolder:GroupViewHolder
         if (convertView==null){
            var binding= ItemGroupSpinnerBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
             groupViewHolder=GroupViewHolder(binding)
         }else{
             groupViewHolder=GroupViewHolder(ItemGroupSpinnerBinding.bind(convertView))
         }
        groupViewHolder.itemGroupSpinnerBinding.groupNameSpinnerTv.text=list[position].name
        return  groupViewHolder.itemView
    }

    inner class GroupViewHolder{
        var itemView:View
        var itemGroupSpinnerBinding: ItemGroupSpinnerBinding
        constructor(itemGroupSpinnerBinding: ItemGroupSpinnerBinding){
            itemView=itemGroupSpinnerBinding.root
            this.itemGroupSpinnerBinding=itemGroupSpinnerBinding
        }
    }
}