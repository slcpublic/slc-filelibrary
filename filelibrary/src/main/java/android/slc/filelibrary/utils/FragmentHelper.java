package android.slc.filelibrary.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

public class FragmentHelper {

    /**
     * 此方法在onBackPressed被重写时使用
     * 回退到上一层fragment
     * 如果已经是最后一层，隐藏界面
     *
     * @param activity 当前activity，仅支持AppCompatActivity
     *                 在fragment中请使用(AppCompatActivity)getActivity()作为参数传入
     */
    public static boolean back(AppCompatActivity activity) {
        if (activity.getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            return false;
        } else {
            activity.getSupportFragmentManager().popBackStack();
            return true;
        }
    }

    /**
     * 切换Fragment为传入参数
     * @param fragmentManager
     * @param fragment
     * @param contentId
     */
    public static void switchFragment(FragmentManager fragmentManager, Fragment fragment, int contentId) {
        fragmentManager.beginTransaction()
                .replace(contentId, fragment)
                .addToBackStack(String.valueOf(fragment.hashCode()))
                .commitAllowingStateLoss();
    }
    /**
     * 切换Fragment为传入参数
     * @param fragmentManager
     * @param fragment
     * @param contentId
     */
    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int contentId) {
        fragmentManager.beginTransaction()
                .add(contentId, fragment)
                .addToBackStack(String.valueOf(fragment.hashCode()))
                .commitAllowingStateLoss();
    }
}