package uz.mobiler.pdpkurs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.mobiler.pdpkurs.databinding.FragmentCourseResultBinding
import uz.mobiler.pdpkurs.models.Course

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CourseResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CourseResultFragment : Fragment() {
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

    lateinit var binding: FragmentCourseResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCourseResultBinding.inflate(layoutInflater,container,false)

        val course = arguments?.getSerializable("course") as Course
        val positions = arguments?.getInt("posKurs")

        binding.toolCourseResult.title=course.name
        binding.descreptionResultCours.text=course.descreption

        binding.addStudent.setOnClickListener {
            var bundle=Bundle()
            bundle.putSerializable("kurslar",course)
            bundle.putInt("indexis",positions!!)
            findNavController().navigate(R.id.addStudentFragment,bundle)
        }

        binding.toolCourseResult.setNavigationOnClickListener {
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
         * @return A new instance of fragment CourseResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CourseResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}