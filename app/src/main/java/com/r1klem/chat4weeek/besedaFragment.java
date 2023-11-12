package com.r1klem.chat4weeek;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class besedaFragment extends Fragment {

    private static final String TITLE = "catname";
    private static final String DESCRIPTION = "description";
    private static final String ICON = "icon";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        RelativeLayout relativeLayout = view.findViewById(R.id.relaout);

        ListView listView = view.findViewById(R.id.listview);

        final String[] catNames = new String[] {
                "Василий", "Гена", "Артур", "Надежда", "Катя",
                "Лена", "Кристина", "Виталий", "Анатолий", "Кузя",
                "Дмитрий", "Олег", "Антон"
        };

// используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, catNames);

        listView.setAdapter(adapter);

        /*
        int listheight = listView.getLayoutParams().height;
        int layoutheight = relativeLayout.getHeight();
        int neededhght = layoutheight + listheight;

        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, neededhght));
        */

        List<String> dataname = new ArrayList<>();
        List<String> datamsg = new ArrayList<>();

        return view;
    }
}