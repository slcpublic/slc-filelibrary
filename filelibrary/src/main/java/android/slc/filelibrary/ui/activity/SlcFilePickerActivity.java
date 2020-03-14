package android.slc.filelibrary.ui.activity;

import android.slc.filelibrary.ui.fragment.SlcFilePickerFragment;
import android.slc.filelibrary.ui.fragment.SlcSimpleFileFragment;

public class SlcFilePickerActivity extends SlcSimpleFileActivity {
    @Override
    protected SlcSimpleFileFragment getFileFragment() {
        return new SlcFilePickerFragment();
    }
}
