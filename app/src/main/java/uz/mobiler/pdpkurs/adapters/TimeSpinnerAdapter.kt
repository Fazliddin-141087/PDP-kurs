package uz.mobiler.pdpkurs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.mobiler.pdpkurs.databinding.ItemTimeSpinnerBinding

class TimeSpinnerAdapter(var list: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): String {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
      return  position.toLong()
    }

    override fun getView(position: Int, convertBiew: View?, parent: ViewGroup?): View {
        var timeViewHolder: TimeViewHolder
        if (convertBiew == null) {
            var binding =
                ItemTimeSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            timeViewHolder = TimeViewHolder(binding)
        } else {
            timeViewHolder = TimeViewHolder(ItemTimeSpinnerBinding.bind(convertBiew))
        }
        timeViewHolder.itemTimeSpinnerBinding.timeTv.text = list[position]
        return timeViewHolder.itemView
    }

    inner class TimeViewHolder {
        var itemView: View
        var itemTimeSpinnerBinding: ItemTimeSpinnerBinding

        constructor(itemTimeSpinnerBinding: ItemTimeSpinnerBinding) {
            itemView = itemTimeSpinnerBinding.root
            this.itemTimeSpinnerBinding = itemTimeSpinnerBinding
        }
    }

}