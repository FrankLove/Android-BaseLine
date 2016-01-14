package com.android.baseline.framework.ui.adapter;

import android.content.Context;
import com.android.baseline.framework.ui.BasicAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ��ѡ������
 * ע�⣺ѡ����ȡ��ѡ�еķ���T������ͬһ�����������дequals����
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 2016/01/14 13:45]
 * @copyright Copyright 2010 RD information technology Co.,ltd.. All Rights Reserved.
 */
public abstract class SingleChoiceAdapter<T> extends BasicAdapter<T> {
    final protected List<T> selectedItems = new ArrayList<>();

    public SingleChoiceAdapter(Context context, List<T> data, int resourceId) {
        super(context, data, resourceId);
    }

    public SingleChoiceAdapter(Context context, List<T> data, Map<Integer, Integer> itemTypeResourceMap) {
        super(context, data, itemTypeResourceMap);
    }

    /**
     * ѡ��ĳһ��
     * @param position
     */
    public void selectItem(int position)
    {
        selectItem(getItem(position));
    }

    /**
     * ѡ��ĳһ��
     * @param choice
     */
    public void selectItem(T choice)
    {
        // ��֤��������ֻ��һ��Ԫ��
        selectedItems.clear();
        if (choice != null)
        {
            selectedItems.add(choice);
            notifyDataSetChanged();
        }
    }

    /**
     * ȡ��ѡ��ĳһ��
     * @param position
     */
    public void disselectItem(int position)
    {
        disselectItem(getItem(position));
    }

    /**
     * ȡ��ѡ��ĳһ��
     * @param choice
     */
    public void disselectItem(T choice)
    {
        if (choice != null && selectedItems.contains(choice))
        {
            selectedItems.remove(choice);
            notifyDataSetChanged();
        }
    }

    /**
     * �Ƿ�ѡ��ĳһ��
     * @param position
     * @return
     */
    public boolean isItemSelected(int position)
    {
        return isItemSelected(getItem(position));
    }

    /**
     * �Ƿ�ѡ��ĳһ��
     * @param choice
     * @return
     */
    public boolean isItemSelected(T choice)
    {
        return selectedItems.contains(choice);
    }

    /**
     * ��������ѡ�е���Ŀ
     * @return
     */
    public T getSelectedItem()
    {
        return selectedItems.size() > 0? selectedItems.get(0) : null;
    }
}
