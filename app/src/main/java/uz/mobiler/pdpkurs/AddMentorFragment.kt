package uz.mobiler.pdpkurs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.mobiler.pdpkurs.databinding.FragmentAddMentorBinding
import uz.mobiler.pdpkurs.db.DbHelper
import uz.mobiler.pdpkurs.models.Course
import uz.mobiler.pdpkurs.models.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddMentorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMentorFragment : Fragment() {
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
    lateinit var dbHelper: DbHelper
    lateinit var binding: FragmentAddMentorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMentorBinding.inflate(layoutInflater, container, false)
       dbHelper = DbHelper(binding.root.context)

        var course = arguments?.getSerializable("course") as Course

        var positionCourse = arguments?.getInt("positionMentor")

        mentorList = dbHelper.getAllMentors()

        var isHas=false
        binding.addMentor.setOnClickListener {
            val surname = binding.mentorSurnameEt.text.toString().trim()
            val name = binding.mentorNameEt.text.toString().trim()
            val fatherName = binding.mentorFatherNameEt.text.toString().trim()
            if (surname.isNotEmpty() && name.isNotEmpty() && fatherName.isNotEmpty()) {
                for (i in 0 until mentorList.size){
                    if (mentorList[i].surname==surname && mentorList[i].name==name && mentorList[i].fatherName==fatherName){
                        isHas=true
                        break
                    }
                }
                if (!isHas){
                    var mentorNew = Mentor(surname, name, fatherName, course)
                    dbHelper.insertMentor(mentorNew)
                    mentorList.add(mentorNew)
                    findNavController().popBackStack()
                    Toast.makeText(binding.root.context, "Ma'lumot saqlandi", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(binding.root.context, "Bunday ismli mentor mavjud!!", Toast.LENGTH_SHORT).show()
                    isHas=false
                }
            } else {
                Toast.makeText(binding.root.context, "Iltimos bo'sh maydonlarni to'ldiring", Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolAddMentor.setNavigationOnClickListener {
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
         * @return A new instance of fragment AddMentorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMentorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}