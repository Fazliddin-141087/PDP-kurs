package uz.mobiler.pdpkurs

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.mobiler.pdpkurs.adapters.StudentRvAdapter
import uz.mobiler.pdpkurs.databinding.FragmentGroupResultBinding
import uz.mobiler.pdpkurs.db.DbHelper
import uz.mobiler.pdpkurs.models.Group
import uz.mobiler.pdpkurs.models.Student

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var studentList: ArrayList<Student>
    lateinit var sList: ArrayList<Student>
    lateinit var dbHelper: DbHelper
    lateinit var studentRvAdapter: StudentRvAdapter
    lateinit var binding: FragmentGroupResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupResultBinding.inflate(layoutInflater, container, false)
        dbHelper = DbHelper(binding.root.context)

        val group = arguments?.getSerializable("group") as Group
        val post = arguments?.getInt("post")


        if (group.isOpened == "close") {
            binding.toolGroupResult.inflateMenu(R.menu.add_menu)

            binding.toolGroupResult.setOnMenuItemClickListener {
                if (it.itemId == R.id.add) {
                    var bundle = Bundle()
                    bundle.putString("add", "add")
                    bundle.putSerializable("addGroup", group)
                    findNavController().navigate(R.id.addGroupStudentsFragment, bundle)
                }
                true
            }
        }else{
            binding.lesson.visibility=View.GONE
        }

        binding.toolGroupResult.title = group.name

        binding.toolGroupResult.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.groupNameTv.text = group.name


        studentList = dbHelper.getAllStudents()


        sList = ArrayList()

        if (studentList.isNotEmpty()){
            for (student in studentList) {
                if (student.group?.name == group.name) {
                    sList.add(student)
                }
            }
        }

        studentRvAdapter = StudentRvAdapter(sList)
        binding.studentRv.adapter = studentRvAdapter
        studentRvAdapter.notifyItemInserted(sList.size)

        binding.sumGroupResultTv.text = "O'quvchilar soni: ${group.name} ta"

        binding.groupTime1Tv.text = "Vaqti: ${group.time}"

        studentRvAdapter.setEdit(object : StudentRvAdapter.EditItemOnClickListener {
            override fun editOnClick(student: Student, position: Int) {
                var bundle = Bundle()
                bundle.putString("edit", "edit")
                bundle.putSerializable("student", student)
                bundle.putInt("pasa", position)
                findNavController().navigate(R.id.addGroupStudentsFragment, bundle)
            }
        })

        studentRvAdapter.setDelete(object : StudentRvAdapter.DeleteItemOnClickListener {
            override fun deleteOnClick(student: Student, position: Int) {
                var alertDialog = AlertDialog.Builder(binding.root.context)
                alertDialog.setMessage("Ushbu o'quvchini\n o'chirmoqchimisiz?")

                alertDialog.setNegativeButton("Yo'q"
                ) { p0, p1 -> alertDialog.create().dismiss() }

                alertDialog.setPositiveButton("Ha"
                ) { p0, p1 ->
                    dbHelper.deleteStudent(student)
                    sList.remove(student)
                    studentRvAdapter.notifyItemRemoved(position)
                    studentRvAdapter.notifyItemRangeChanged(position, sList.size)
                    if (group.isOpened=="open" && sList.isEmpty()){
                        dbHelper.deleteGroups(group)
                    }
                    alertDialog.create().dismiss()
                }
                alertDialog.show()
            }
        })

        binding.lesson.setOnClickListener {
            if (sList.isNotEmpty()) {
                Toast.makeText(binding.root.context, "Dars boshlandi!!!", Toast.LENGTH_SHORT).show()
                group.isOpened = "open"
                dbHelper.updateGroups(group)
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    binding.root.context,
                    "Student qo'shing keyin dars boshlanadi!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
            for (student in sList) {
                if (student.group?.isOpened == "open") {
                    binding.lesson.visibility = View.GONE
                }
            }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupReultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}