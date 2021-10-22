package uz.mobiler.pdpkurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.pdpkurs.databinding.ItemGroupRvBinding
import uz.mobiler.pdpkurs.models.Group

class GroupRvAdapter(var list: ArrayList<Group>):RecyclerView.Adapter<GroupRvAdapter.Vh>()  {


    var deleteItemOnClickListener:DeleteItemOnClickListener?=null
    fun setDeleteOnClick(deleteItemOnClickListener: DeleteItemOnClickListener){
        this.deleteItemOnClickListener=deleteItemOnClickListener
    }

    var editItemOnClickListener:EditItemOnClickListener?=null
    fun setEditOnClick(editItemOnClickListener: EditItemOnClickListener){
        this.editItemOnClickListener=editItemOnClickListener
    }

    var myOnItemClickListener:MyOnItemClickListener?=null
    fun setMyOnItemClick(myOnItemClickListener: MyOnItemClickListener){
        this.myOnItemClickListener=myOnItemClickListener
    }

    inner class Vh(var itemGroupRvBinding: ItemGroupRvBinding):RecyclerView.ViewHolder(itemGroupRvBinding.root){

        fun onBind(group: Group,position: Int){
            itemGroupRvBinding.groupNameItem.text=group.name
            itemGroupRvBinding.sumItem.text="${list.size}"

            itemGroupRvBinding.deleteItem.setOnClickListener {
                deleteItemOnClickListener!!.deleteOnItemClick(group, position)
            }

            itemGroupRvBinding.editItem.setOnClickListener {
                editItemOnClickListener!!.editOnItemClick(group, position)
            }

            itemGroupRvBinding.root.setOnClickListener {
                myOnItemClickListener!!.myOnItemClick(group, position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemGroupRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface DeleteItemOnClickListener{
        fun deleteOnItemClick(group: Group,position: Int)
    }

    interface EditItemOnClickListener{
        fun editOnItemClick(group: Group,position: Int)
    }

    interface MyOnItemClickListener{
        fun myOnItemClick(group: Group,position: Int)
    }

}