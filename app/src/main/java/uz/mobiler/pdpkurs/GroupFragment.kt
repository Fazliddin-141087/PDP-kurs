package uz.mobiler.pdpkurs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import uz.mobiler.pdpkurs.adapters.GroupViewPagerAdapter
import uz.mobiler.pdpkurs.databinding.FragmentGroupBinding
import uz.mobiler.pdpkurs.models.Course

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentGroupBinding
    lateinit var groupViewPagerAdapter: GroupViewPagerAdapter
    lateinit var list: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupBinding.inflate(layoutInflater, container, false)

        val course = arguments?.getSerializable("course") as Course

        val position = arguments?.getInt("positionGroup")

        binding.toolBar.title=course.name

        list = ArrayList()
        list.add("Ochilgan guruhlar")
        list.add("Ochilayotgan guruhlar")

        groupViewPagerAdapter = GroupViewPagerAdapter(list,course, childFragmentManager)
        binding.viewPagerGroup.adapter = groupViewPagerAdapter
        binding.tabLayoutGroup.setupWithViewPager(binding.viewPagerGroup)
        groupViewPagerAdapter.notifyDataSetChanged()

        binding.tabLayoutGroup.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 1) {
                    binding.toolBar.inflateMenu(R.menu.add_menu)
                }else{
                    binding.toolBar.menu.clear()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add) {
                var bundle=Bundle()
                bundle.putSerializable("aaa",course)
                bundle.putInt("posit",position!!)
                findNavController().navigate(R.id.newAddGroupFragment,bundle)
            }
            true
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
         * @return A new instance of fragment GroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}