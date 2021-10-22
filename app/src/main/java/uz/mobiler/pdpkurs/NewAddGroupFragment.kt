package uz.mobiler.pdpkurs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.mobiler.pdpkurs.adapters.MentorSpinnerAdapter
import uz.mobiler.pdpkurs.adapters.TimeSpinnerAdapter
import uz.mobiler.pdpkurs.databinding.FragmentNewAddGroupBinding
import uz.mobiler.pdpkurs.db.DbHelper
import uz.mobiler.pdpkurs.models.Course
import uz.mobiler.pdpkurs.models.Group
import uz.mobiler.pdpkurs.models.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewAddGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewAddGroupFragment : Fragment() {
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
    lateinit var mentorList: ArrayList<Mentor>
    lateinit var dayList: ArrayList<String>
    lateinit var timeList: ArrayList<String>
    lateinit var groupList: ArrayList<Group>
    lateinit var mentorSpinnerAdapter: MentorSpinnerAdapter
    lateinit var timeSpinnerAdapter: TimeSpinnerAdapter
    lateinit var daySpinnerAdapter: TimeSpinnerAdapter
    lateinit var mList:ArrayList<Mentor>
    lateinit var binding: FragmentNewAddGroupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewAddGroupBinding.inflate(layoutInflater, container, false)
        dbHelper = DbHelper(binding.root.context)

        val kurs = arguments?.getSerializable("aaa") as Course

        val positions = arguments?.getInt("posit")

        binding.toolNewAddGroup.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        groupList=dbHelper.getAllGroups()
        mentorList = dbHelper.getAllMentors()
        mList=ArrayList()

        if (mentorList.isNotEmpty()){
            for (mentor in mentorList) {
                if (mentor.course?.name==kurs.name){
                    mList.add(mentor)
                }
            }
        }else{
            Toast.makeText(binding.root.context, "Iltimos Mentor qo'shing", Toast.LENGTH_SHORT).show()
        }

        mentorSpinnerAdapter = MentorSpinnerAdapter(mList)
        binding.groupMentorSpinner.adapter = mentorSpinnerAdapter

        timeList = ArrayList()
        dayList = ArrayList()

        timeList.add("16:30 - 18:30")
        timeList.add("19:00 - 21:00")

        timeSpinnerAdapter = TimeSpinnerAdapter(timeList)
        binding.groupTimeSpinner.adapter = timeSpinnerAdapter

        dayList.add("Toq kunlari")
        dayList.add("Juft kunlari")

        daySpinnerAdapter = TimeSpinnerAdapter(dayList)
        binding.groupDaysSpinner.adapter = daySpinnerAdapter

        var isHas=false

        binding.saveBtnGroup.setOnClickListener {
            val groupName = binding.groupNameEt.text.toString().trim()
            if (mList.isNotEmpty()){
                val mentorName = mList[binding.groupMentorSpinner.selectedItemPosition]
                val time = timeList[binding.groupTimeSpinner.selectedItemPosition]
                val days = dayList[binding.groupDaysSpinner.selectedItemPosition]
                if (groupName.isNotEmpty() && mentorName.name!="" && mentorName.surname!="" && time.isNotEmpty() && days.isNotEmpty()){

                    for (i in 0 until groupList.size){
                      if (groupList[i].name==groupName){
                            isHas=true
                          break
                      }
                    }
                    if (!isHas){
                        var group = Group(groupName,kurs,mentorName,time,days,"close")
                        dbHelper.insertGroup(group)
                        groupList.add(group)
                        findNavController().popBackStack()
                        Toast.makeText(binding.root.context, "Saqlandi", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(binding.root.context, "Bunday $groupName guruh mavjud boshqa nom bering guruhga!!!", Toast.LENGTH_SHORT).show()
                        isHas=false
                    }
                }else{
                    Toast.makeText(binding.root.context, "Iltimos bo'sh maydonlarni to'ldiring", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(binding.root.context, "Iltimos Mentor qo'shing ${kurs.name} kursga", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment NewAddGroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewAddGroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}