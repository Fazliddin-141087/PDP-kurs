package uz.mobiler.pdpkurs

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.mobiler.pdpkurs.databinding.FragmentAddGroupStudentsBinding
import uz.mobiler.pdpkurs.db.DbHelper
import uz.mobiler.pdpkurs.models.Group
import uz.mobiler.pdpkurs.models.Student

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddGroupStudentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddGroupStudentsFragment : Fragment() {
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
    lateinit var dbHelper: DbHelper
    lateinit var studentList: ArrayList<Student>
    lateinit var binding: FragmentAddGroupStudentsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddGroupStudentsBinding.inflate(layoutInflater, container, false)

        dbHelper = DbHelper(binding.root.context)

        var add = arguments?.getString("add")

        val edit = arguments?.getString("edit")

        val pasa = arguments?.getInt("pasa")


        studentList = dbHelper.getAllStudents()


        binding.toolNewAddGroupStudent.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.studentDateEt.setOnClickListener {
            var dataPickerDialog = DatePickerDialog(binding.root.context)
            dataPickerDialog.setOnDateSetListener { p0, p1, p2, p3 ->
                binding.studentDateEt.text = "$p3.${p2 + 1}.$p1"
            }
            dataPickerDialog.show()
        }

        if (edit != null) {

            val student = arguments?.getSerializable("student") as Student

            if (student != null) {

                binding.addSaveBtn.setText("O'zgartirish")

                binding.studentSurnameEt.setText(student.surname)
                binding.studentNameEt.setText(student.name)
                binding.studentFatherNameEt.setText(student.fatherName)
                binding.studentDateEt.setText(student.date)

                binding.addSaveBtn.setOnClickListener {
                    val surname = binding.studentSurnameEt.text.toString().trim()
                    val name = binding.studentNameEt.text.toString().trim()
                    val fatherName = binding.studentFatherNameEt.text.toString().trim()
                    val date = binding.studentDateEt.text.toString()

                    if (surname.isNotEmpty() && name.isNotEmpty() && fatherName.isNotEmpty()) {
                        if (date.isNotEmpty() && date != "18/10/2021") {
                            student.surname = binding.studentSurnameEt.text.toString().trim()
                            student.name = binding.studentNameEt.text.toString().trim()
                            student.fatherName = binding.studentFatherNameEt.text.toString().trim()
                            student.date = binding.studentDateEt.text.toString()
                            dbHelper.updateStudent(student)
                            studentList[pasa!!] = student
                            findNavController().popBackStack()
                            Toast.makeText(
                                binding.root.context,
                                "Student o'zgartirildi!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                binding.root.context,
                                "Iltimos kalendarda sanani ko'rsating!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            "Iltimos bo'sh maydonlarni to'ldiring",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        if (add != null) {
            var noHas = false
            val group = arguments?.getSerializable("addGroup") as Group
            if (group != null) {
                Toast.makeText(binding.root.context, "${group.name}", Toast.LENGTH_SHORT).show()

                binding.addSaveBtn.setOnClickListener {
                    val surname = binding.studentSurnameEt.text.toString().trim()
                    val name = binding.studentNameEt.text.toString().trim()
                    val fatherName = binding.studentFatherNameEt.text.toString().trim()
                    val date = binding.studentDateEt.text.toString()

                    if (surname.isNotEmpty() && name.isNotEmpty() && fatherName.isNotEmpty()) {
                        if (date.isNotEmpty() && date != "18/10/2021") {

                            for (i in 0 until studentList.size) {
                                if (studentList[i].name == name && studentList[i].surname == surname && studentList[i].fatherName == fatherName) {
                                    noHas = true
                                    break
                                }
                            }
                            if (!noHas) {
                                var newStudent = Student(surname, name, fatherName, date, group)
                                dbHelper.insertStudent(newStudent)
                                studentList.add(newStudent)
                                findNavController().popBackStack()
                                Toast.makeText(binding.root.context, "Student saqlandi!!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(binding.root.context, "Guruhda bunday o'quvchi mavjud", Toast.LENGTH_SHORT).show()
                                noHas = false
                            }
                        } else {
                            Toast.makeText(binding.root.context, "Iltimos kalendarda sanani ko'rsating!!!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(binding.root.context, "Iltimos bo'sh maydonlarni to'ldiring", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddGroupStudentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddGroupStudentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}