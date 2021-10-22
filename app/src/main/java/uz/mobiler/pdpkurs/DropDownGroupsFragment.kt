package uz.mobiler.pdpkurs

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.mobiler.pdpkurs.adapters.GroupRvAdapter
import uz.mobiler.pdpkurs.adapters.MentorSpinnerAdapter
import uz.mobiler.pdpkurs.adapters.TimeSpinnerAdapter
import uz.mobiler.pdpkurs.databinding.FragmentDropDownGroupsBinding
import uz.mobiler.pdpkurs.databinding.ItemEditGroupBinding
import uz.mobiler.pdpkurs.db.DbHelper
import uz.mobiler.pdpkurs.models.Course
import uz.mobiler.pdpkurs.models.Group
import uz.mobiler.pdpkurs.models.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [DropDownGroupsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DropDownGroupsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Course? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Course
        }
    }

    lateinit var binding: FragmentDropDownGroupsBinding
    lateinit var grouoRvAdapter: GroupRvAdapter
    lateinit var dbHelper: DbHelper
    lateinit var groupList: ArrayList<Group>
    lateinit var gList: ArrayList<Group>
    lateinit var mentorSpinnerAdapter: MentorSpinnerAdapter
    lateinit var timeSpinnerAdapter: TimeSpinnerAdapter
    lateinit var mentorList: ArrayList<Mentor>
    lateinit var mList: ArrayList<Mentor>
    lateinit var timeList: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDropDownGroupsBinding.inflate(layoutInflater, container, false)

        dbHelper = DbHelper(binding.root.context)

        groupList = dbHelper.getAllGroups()

        gList = ArrayList()

        mentorList = dbHelper.getAllMentors()

        mList = ArrayList()

        timeList = ArrayList()

        timeList.add("16:30 - 18:30")
        timeList.add("19:00 - 21:00")

        if (param1 != null) {
            for (group in groupList) {
                if (group.course?.name == param1?.name && group.isOpened == "close") {
                    gList.add(group)
                }
            }
        }

        grouoRvAdapter = GroupRvAdapter(gList)
        binding.group2Rv.adapter = grouoRvAdapter


        grouoRvAdapter.setMyOnItemClick(object : GroupRvAdapter.MyOnItemClickListener {
            override fun myOnItemClick(group: Group, position: Int) {
                var bundle = Bundle()
                bundle.putSerializable("group", group)
                bundle.putInt("post", position)
                findNavController().navigate(R.id.groupReultFragment, bundle)
            }
        })

        grouoRvAdapter.setEditOnClick(object : GroupRvAdapter.EditItemOnClickListener {
            override fun editOnItemClick(group: Group, position: Int) {

                var alertDialog = AlertDialog.Builder(binding.root.context)
                var dialogView = ItemEditGroupBinding.inflate(
                    LayoutInflater.from(binding.root.context),
                    null,
                    false
                )

                if (mentorList.isNotEmpty()) {
                    for (mentor in mentorList) {
                        if (mentor.course?.name == param1?.name) {
                            mList.add(mentor)
                        }
                    }
                }else{
                    Toast.makeText(binding.root.context, "Bu ${param1?.name} kursga Mentor qo'shing!!!", Toast.LENGTH_SHORT).show()
                }

                mentorSpinnerAdapter = MentorSpinnerAdapter(mList)
                dialogView.mentorNameSpinner.adapter = mentorSpinnerAdapter

                timeSpinnerAdapter = TimeSpinnerAdapter(timeList)
                dialogView.groupTimeSpinner.adapter = timeSpinnerAdapter

                dialogView.groupNameEt.setText(group.name)

                var indexMentor = -1

                for (i in 0 until mList.size) {
                    if (mList[i].name==group.mentor?.name) {
                        indexMentor = i
                        break
                    }
                }

                dialogView.mentorNameSpinner.setSelection(indexMentor)
                var indexTime = -1
                for (i in 0 until timeList.size) {
                    if (timeList[i] == group.time) {
                        indexTime = i
                        break
                    }
                }
                dialogView.groupTimeSpinner.setSelection(indexTime)
                alertDialog.setNegativeButton(
                    "Yopish"
                ) { p0, p1 ->
                    alertDialog.create().dismiss()
                }
                alertDialog.setPositiveButton(
                    "O'zgartirish"
                ) { p0, p1 ->
                    val name = dialogView.groupNameEt.text.toString().trim()
                    val mentor = mList[dialogView.mentorNameSpinner.selectedItemPosition]
                    val time = timeList[dialogView.groupTimeSpinner.selectedItemPosition]
                    if (name.isNotEmpty() && mentor != null && time.isNotEmpty()) {
                        group.name = dialogView.groupNameEt.text.toString().trim()
                        group.mentor = mList[dialogView.mentorNameSpinner.selectedItemPosition]
                        group.time = timeList[dialogView.groupTimeSpinner.selectedItemPosition]
                        dbHelper.updateGroups(group)
                        gList[position] = group
                        grouoRvAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            "Iltimos bo'sh maydonlarni to'ldiring",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    alertDialog.create().dismiss()
                }

                alertDialog.setView(dialogView.root)
                alertDialog.show()
            }
        })

        grouoRvAdapter.setDeleteOnClick(object : GroupRvAdapter.DeleteItemOnClickListener {
            override fun deleteOnItemClick(group: Group, position: Int) {
                var alertDialog = AlertDialog.Builder(binding.root.context)
                alertDialog.setTitle("")
                alertDialog.setMessage("Ushbu guruhni\n o'chirmoqchimisiz?")

                alertDialog.setPositiveButton(
                    "Ha"
                ) { p0, p1 ->
                    dbHelper.deleteGroups(group)
                    gList.remove(group)
                    grouoRvAdapter.notifyItemRemoved(position)
                    grouoRvAdapter.notifyItemRangeChanged(position, gList.size)
                    alertDialog.create().dismiss()
                }

                alertDialog.setNegativeButton(
                    "Yo'q"
                ) { p0, p1 -> alertDialog.create().dismiss() }
                alertDialog.show()
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DropDownGroupsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Course) =
            DropDownGroupsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}