package uz.mobiler.pdpkurs.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.mobiler.pdpkurs.DropDownGroupsFragment
import uz.mobiler.pdpkurs.DropGroupsFragment
import uz.mobiler.pdpkurs.models.Course

class GroupViewPagerAdapter(var list:ArrayList<String>,var course: Course,fragmentManager: FragmentManager) :FragmentStatePagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0->{ return  DropGroupsFragment.newInstance(course)}
            1->{ return  DropDownGroupsFragment.newInstance(course)}
        }
        return DropGroupsFragment.newInstance(course)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position]
    }

}