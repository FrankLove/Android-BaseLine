package com.android.baseline.framework.ui.adapter;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * ��ѡ������
 * ע�⣺ѡ����ȡ��ѡ�еķ���T������ͬһ�����������дequals����
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 2016/01/14 11:52]
 * @copyright Copyright 2010 RD information technology Co.,ltd.. All Rights Reserved.
 */
public abstract class MultiChoiceAdapter<T> extends SingleChoiceAdapter<T> {
    public MultiChoiceAdapter(Context context, List<T> data, int resourceId) {
        super(context, data, resourceId);
    }

    public MultiChoiceAdapter(Context context, List<T> data, Map<Integer, Integer> itemTypeResourceMap) {
        super(context, data, itemTypeResourceMap);
    }

    /**
     * ѡ��ĳһ��
     * @param choice
     */
    @Override
    public void selectItem(T choice)
    {
        if (choice != null && !selectedItems.contains(choice))
        {
            selectedItems.add(choice);
            notifyDataSetChanged();
        }
    }

    /**
     * ѡ��
     */
    public void selectAll(List<T> items)
    {
        selectedItems.removeAll(items);
        selectedItems.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * ѡ������
     */
    public void selectAll()
    {
        selectedItems.clear();
        selectedItems.addAll(getDataSource());
        notifyDataSetChanged();
    }

    /**
     * ȡ��ѡ��
     */
    public void disselectAll(List<T> items)
    {
        selectedItems.removeAll(items);
        notifyDataSetChanged();
    }

    /**
     * ȡ��ѡ������
     */
    public void disselectAll()
    {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * ��������ѡ�е���Ŀ
     * @return
     */
    public List<T> getSelectedItems()
    {
        return selectedItems;
    }
}
