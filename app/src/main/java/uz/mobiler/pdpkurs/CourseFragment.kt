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
import uz.mobiler.pdpkurs.adapters.CourseRvAdapter
import uz.mobiler.pdpkurs.databinding.FragmentCourseBinding
import uz.mobiler.pdpkurs.databinding.ItemAddCourseBinding
import uz.mobiler.pdpkurs.db.DbHelper
import uz.mobiler.pdpkurs.models.Course
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CourseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CourseFragment : Fragment() {
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

    lateinit var binding: FragmentCourseBinding
    lateinit var courseRvAdapter: CourseRvAdapter
    lateinit var coursList:ArrayList<Course>
    lateinit var dbHelper: DbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCourseBinding.inflate(layoutInflater,container,false)

        dbHelper= DbHelper(binding.root.context)

        val kurs = arguments?.getString("kurslar")
        val grups = arguments?.getString("gruppa")
        val mentors = arguments?.getString("mentorlar")

        coursList=dbHelper.getAllCourses()

        binding.toolCourse.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        if (kurs!=null){
            binding.toolCourse.inflateMenu(R.menu.add_menu)
                courseRvAdapter= CourseRvAdapter(coursList,object :CourseRvAdapter.MyItemOnClickListener{
                override fun myOnItemClick(course: Course, position: Int) {
                    var bundle=Bundle()
                    bundle.putSerializable("course",course)
                    bundle.putInt("posKurs",position)
                    findNavController().navigate(R.id.courseResultFragment,bundle)
                }
            })

            binding.rvCourse.adapter=courseRvAdapter
            courseRvAdapter.notifyItemInserted(coursList.size)


            var isHas=false
            binding.toolCourse.setOnMenuItemClickListener {
                if (it.itemId==R.id.add){
                    val alertDialog=AlertDialog.Builder(binding.root.context,R.style.SheetDialog)
                    alertDialog.setTitle("")
                    val dialogView = ItemAddCourseBinding.inflate(LayoutInflater.from(binding.root.context), null, false)

                    alertDialog.setPositiveButton("Qo'shish"
                    ) { p0, p1 ->
                        val name = dialogView.courseNameEt.text.toString().trim()
                        val description = dialogView.courseDescriptionEt.text.toString().trim()

                        if (name.isNotEmpty() && description.isNotEmpty()) {
                            for (i in 0 until coursList.size) {
                                if (coursList[i].name == name) {
                                    isHas = true
                                    break
                                }
                            }
                            if (!isHas) {
                                var course = Course(name, description)
                                dbHelper.insertCourse(course)
                                coursList.add(course)
                                courseRvAdapter.notifyItemChanged(coursList.size)
                                alertDialog.create().dismiss()
                                Toast.makeText(binding.root.context, "Saqlandi", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(binding.root.context, "Bunday kurs mavjud boshqa kusr nomini kiriting???", Toast.LENGTH_SHORT).show()
                                isHas = false
                            }
                        } else {
                            Toast.makeText(binding.root.context, "Iltimos bo'sh qolgan o'rinlarni to'ldiring!!!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    alertDialog.setNegativeButton("Yopish",object :DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            alertDialog.create().dismiss()
                        }
                    })
                    alertDialog.setView(dialogView.root)
                    alertDialog.show()
                }
                true
            }
        }

        if (grups!=null){
            binding.toolCourse.title="Barcha kurslar"

            courseRvAdapter= CourseRvAdapter(coursList,object :CourseRvAdapter.MyItemOnClickListener{
                override fun myOnItemClick(course: Course, position: Int) {
                    var bundle=Bundle()
                    bundle.putInt("positionGroup",position)
                    bundle.putSerializable("course",course)
                    findNavController().navigate(R.id.groupFragment,bundle)
                }
            })

            binding.rvCourse.adapter=courseRvAdapter
            courseRvAdapter.notifyItemInserted(coursList.size)

        }


        if (mentors!=null){
            setHasOptionsMenu(false)
            binding.toolCourse.title="Barcha kurslar"

            courseRvAdapter= CourseRvAdapter(coursList,object :CourseRvAdapter.MyItemOnClickListener{
                override fun myOnItemClick(course: Course, position: Int) {
                    var bundle=Bundle()
                    bundle.putSerializable("course",course)
                    bundle.putInt("positionMentor",position)
                    findNavController().navigate(R.id.mentorsFragment,bundle)
                }
            })

            binding.rvCourse.adapter=courseRvAdapter
            courseRvAdapter.notifyItemInserted(coursList.size)

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
         * @return A new instance of fragment CourseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CourseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}