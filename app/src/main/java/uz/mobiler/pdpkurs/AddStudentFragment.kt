package uz.mobiler.pdpkurs

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.mobiler.pdpkurs.adapters.GroupSpinnerAdapter
import uz.mobiler.pdpkurs.adapters.MentorSpinnerAdapter
import uz.mobiler.pdpkurs.databinding.FragmentAddStudentBinding
import uz.mobiler.pdpkurs.db.DbHelper
import uz.mobiler.pdpkurs.models.Course
import uz.mobiler.pdpkurs.models.Group
import uz.mobiler.pdpkurs.models.Mentor
import uz.mobiler.pdpkurs.models.Student

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddStudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddStudentFragment : Fragment() {
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

    lateinit var mentorList: ArrayList<Mentor>
    lateinit var groupList: ArrayList<Group>
    lateinit var studentList: ArrayList<Student>
    lateinit var mList: ArrayList<Mentor>
    lateinit var gList: ArrayList<Group>
    lateinit var mentorSpinnerAdapter: MentorSpinnerAdapter
    lateinit var groupSpinnerAdapter: GroupSpinnerAdapter
    lateinit var dbHelper: DbHelper
    lateinit var binding: FragmentAddStudentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStudentBinding.inflate(layoutInflater, container, false)

        dbHelper = DbHelper(binding.root.context)

        val course = arguments?.getSerializable("kurslar") as Course
        val index = arguments?.getInt("indexis")

        mentorList = dbHelper.getAllMentors()

        groupList = dbHelper.getAllGroups()

        mList = ArrayList()
        gList = ArrayList()

        if (mentorList.isNotEmpty()) {
            for (mentor in mentorList) {
                if (mentor.course?.name == course.name) {
                    mList.add(mentor)
                }
            }
        } else {
            Toast.makeText(
                binding.root.context,
                "Iltimos shu ${course.name} ga  mentor qo'shing",
                Toast.LENGTH_SHORT
            ).show()
        }


        mentorSpinnerAdapter = MentorSpinnerAdapter(mList)
        binding.studentMentorsSpinner.adapter = mentorSpinnerAdapter

        if (groupList.isNotEmpty()) {
            for (group in groupList) {
                if (group.course?.name == course.name && group.isOpened == "close") {
                    gList.add(group)
                }
            }
        } else {
            Toast.makeText(
                binding.root.context,
                "Iltimos shu ${course.name} ga  guruh qo'shing",
                Toast.LENGTH_SHORT
            ).show()
        }


        groupSpinnerAdapter = GroupSpinnerAdapter(gList)
        binding.studentGroupsSpinner.adapter = groupSpinnerAdapter

        binding.studentDate.setOnClickListener {
            var datePickerDialog = DatePickerDialog(binding.root.context)
            datePickerDialog.setOnDateSetListener { p0, p1, p2, p3 ->
                binding.studentDate.text = "$p3.${p2 + 1}.$p1"
            }
            datePickerDialog.show()
        }


        studentList = dbHelper.getAllStudents()


        var noHas = false

        binding.studentSaveBtn.setOnClickListener {
            val surname = binding.studentSurname.text.toString().trim()
            val name = binding.studentName.text.toString().trim()
            val fatherName = binding.studentFatherName.text.toString().trim()
            val date = binding.studentDate.text.toString().trim()

            if (mList.isNotEmpty() && gList.isNotEmpty()) {
                val mentor = mList[binding.studentMentorsSpinner.selectedItemPosition]
                val group = gList[binding.studentGroupsSpinner.selectedItemPosition]
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
                            group.mentor = mentor
                            group.course = course
                            dbHelper.updateGroups(group)
                            dbHelper.insertStudent(newStudent)
                            studentList.add(newStudent)
                            findNavController().popBackStack()
                            Toast.makeText(
                                binding.root.context,
                                "Student saqlandi!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                binding.root.context,
                                "Guruhda bunday o'quvchi mavjud",
                                Toast.LENGTH_SHORT
                            ).show()
                            noHas = false
                        }
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
            } else {
                Toast.makeText(
                    binding.root.context,
                    "Iltimos shu  ${course.name} kursga  mentor va guruh  qo'shing",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }




        binding.toolAddStudent.setNavigationOnClickListener {
            findNavController().popBackStack()
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
         * @return A new instance of fragment AddStudentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddStudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}