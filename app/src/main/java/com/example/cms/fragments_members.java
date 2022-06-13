package com.example.cms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class fragments_members extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragments_members, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);

        MemberAdapter memberAdapter = new MemberAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        memberAdapter.addFragment(new com.example.cms.fragment_mentor_board(),"Mentor Board");
        memberAdapter.addFragment(new com.example.cms.fragment_main_board(),"Main Board");
        memberAdapter.addFragment(new com.example.cms.fragment_assistant_board(),"Assistant Board");

        viewPager.setAdapter(memberAdapter);
        return view;
    }
}