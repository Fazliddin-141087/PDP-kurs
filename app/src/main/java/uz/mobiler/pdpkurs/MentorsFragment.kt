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
import uz.mobiler.pdpkurs.adapters.MentorRvAdapter
import uz.mobiler.pdpkurs.databinding.FragmentMentorsBinding
import uz.mobiler.pdpkurs.databinding.ItemEditDialogBinding
import uz.mobiler.pdpkurs.db.DbHelper
import uz.mobiler.pdpkurs.models.Course
import uz.mobiler.pdpkurs.models.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorsFragment : Fragment() {
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
    lateinit var mentorList:ArrayList<Mentor>
    lateinit var mentorRvAdapter:MentorRvAdapter
    lateinit var list: ArrayList<Mentor>
    lateinit var binding:FragmentMentorsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMentorsBinding.inflate(layoutInflater,container,false)

        val course = arguments?.getSerializable("course") as Course
        val positionCourse = arguments?.getInt("positionMentor")


        dbHelper= DbHelper(binding.root.context)

        binding.toolMentor.title=course.name

        mentorList=dbHelper.getAllMentors()

        list=ArrayList()
        if (mentorList.isNotEmpty()){
            for (mentor in mentorList) {
                if (mentor.course?.name == course.name) {
                    list.add(mentor)
                }
            }
        }

        binding.toolMentor.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolMentor.setOnMenuItemClickListener {
            if (it.itemId==R.id.add){
                var bundle=Bundle()
                bundle.putSerializable("course",course)
                bundle.putInt("positionMentor", positionCourse!!)
                findNavController().navigate(R.id.addMentorFragment,bundle)
            }
            true
        }

        mentorRvAdapter= MentorRvAdapter(list)
        binding.mentorRv.adapter=mentorRvAdapter
        mentorRvAdapter.notifyItemChanged(list.size)
        mentorRvAdapter.notifyItemInserted(list.size)

        mentorRvAdapter.setEditOnClick(object :MentorRvAdapter.EditItemOnClickListener{
            override fun editOnClick(mentor: Mentor, position: Int) {
                var alertDialog=AlertDialog.Builder(binding.root.context,R.style.SheetDialog)
                val dialog = alertDialog.create()
                val dialogView = ItemEditDialogBinding.inflate(LayoutInflater.from(binding.root.context), null, false)

                dialogView.mentorSurnameUpdate.setText(mentor.surname)
                dialogView.mentorNameUpdate.setText(mentor.name)
                dialogView.mentorFatherNameUpdate.setText(mentor.fatherName)

                alertDialog.setPositiveButton("O'zgartirish"
                ) { p0, p1 ->
                    val surName = dialogView.mentorSurnameUpdate.text.toString().trim()
                    val name = dialogView.mentorNameUpdate.text.toString().trim()
                    val fatherName = dialogView.mentorFatherNameUpdate.text.toString().trim()

                    if (surName.isNotEmpty() && name.isNotEmpty() && fatherName.isNotEmpty()) {
                        mentor.surname = dialogView.mentorSurnameUpdate.text.toString().trim()
                        mentor.name = dialogView.mentorNameUpdate.text.toString().trim()
                        mentor.fatherName = dialogView.mentorFatherNameUpdate.text.toString().trim()
                        dbHelper.updateMentors(mentor)
                        list[position] = mentor
                        dialog.dismiss()
                        mentorRvAdapter.notifyItemRangeChanged(position, list.size)
                        Toast.makeText(
                            binding.root.context,
                            "Muvaffaqiyatli o'zgartirildi",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            "Iltimos bo'sh maydonlarni to'ldiring",
                            Toast.LENGTH_SHORT
                        ).show()
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
        })

        mentorRvAdapter.setDeleteOnClick(object :MentorRvAdapter.DeleteItemOnClickListener{
            override fun deleteOnClik(mentor: Mentor, position: Int) {
                dbHelper.deleteMentors(mentor)
                list.remove(mentor)
                mentorRvAdapter.notifyItemRemoved(position)
                mentorRvAdapter.notifyItemRangeChanged(position,list.size)
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
         * @return A new instance of fragment MentorsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentorsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}