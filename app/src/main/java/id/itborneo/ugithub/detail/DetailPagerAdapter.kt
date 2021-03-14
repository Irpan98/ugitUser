package id.itborneo.ugithub.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.itborneo.ugithub.core.enums.UserType
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.detail.list.ListInDetailFragment
import java.util.*

class DetailPagerAdapter(activity: AppCompatActivity, private val user: UserModel) :
    FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ListInDetailFragment.newInstance(
                user, UserType.FOLLOWERS.name.toLowerCase(
                    Locale.ROOT
                )
            )
            1 -> fragment = ListInDetailFragment.newInstance(
                user, UserType.FOLLOWING.name.toLowerCase(
                    Locale.ROOT
                )
            )
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}