package com.slc.filelibrary.ui.activity;

import com.slc.filelibrary.ui.fragment.SlcFilePickerFragment;
import com.slc.filelibrary.ui.fragment.SlcSimpleFileFragment;

public class SlcFilePickerActivity extends SlcSimpleFileActivity {
    @Override
    protected SlcSimpleFileFragment getFileFragment() {
        return new SlcFilePickerFragment();
    }
}
