package android.slc.filelibrary.filter;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimorinny on 31.05.16.
 */
public class CompositeFilter implements FileFilter, Serializable {

    private List<FileFilter> mFilters;

    public CompositeFilter(List<FileFilter> filters) {
        mFilters = filters;
    }

    @Override
    public boolean accept(File f) {
        for (FileFilter filter : mFilters) {
            if (!filter.accept(f)) {
                return false;
            }
        }
        return true;
    }
}
