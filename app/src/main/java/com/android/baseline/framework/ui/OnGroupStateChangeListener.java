package com.android.baseline.framework.ui;

import android.widget.ExpandableListView;

/**
 * ����ListViewչ��ĳһ���Զ��ر�����
 * @author hiphonezhu@gmail.com
 * @version [Android-BaseLine, 2014-12-16]
 */
public class OnGroupStateChangeListener implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener {
    ExpandableListView expandableListView;
    ExpandableListView.OnGroupExpandListener onGroupExpandListener;
    ExpandableListView.OnGroupCollapseListener onGroupCollapseListener;
    int lastExpandedGroup = -1;

    public OnGroupStateChangeListener(ExpandableListView expandableListView) {
        this.expandableListView = expandableListView;
    }

    /**
     * ����չ���ص�
     * @param onGroupExpandListener
     */
    public OnGroupStateChangeListener setOnGroupExpandListener(ExpandableListView.OnGroupExpandListener onGroupExpandListener)
    {
        this.onGroupExpandListener = onGroupExpandListener;
        return this;
    }

    /**
     * ���úϲ��ص�
     * @param onGroupCollapseListener
     */
    public OnGroupStateChangeListener setOnGroupCollapseListener(ExpandableListView.OnGroupCollapseListener onGroupCollapseListener)
    {
        this.onGroupCollapseListener = onGroupCollapseListener;
        return this;
    }

    /**
     * ���¼�
     */
    public void bind()
    {
        expandableListView.setOnGroupExpandListener(this);
        expandableListView.setOnGroupCollapseListener(this);
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (lastExpandedGroup != groupPosition) {
            // �ر���һ��
            expandableListView.collapseGroup(lastExpandedGroup);
        }
        lastExpandedGroup = groupPosition;
        if (onGroupExpandListener != null)
        {
            onGroupExpandListener.onGroupExpand(groupPosition);
        }
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        if (lastExpandedGroup == groupPosition) {
            lastExpandedGroup = -1;
        }
        if (onGroupCollapseListener != null)
        {
            onGroupCollapseListener.onGroupCollapse(groupPosition);
        }
    }
}
