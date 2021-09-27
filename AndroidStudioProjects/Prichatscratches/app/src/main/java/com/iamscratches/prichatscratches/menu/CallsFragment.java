package com.iamscratches.prichatscratches.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamscratches.prichatscratches.R;
import com.iamscratches.prichatscratches.adapter.CallListAdapter;
import com.iamscratches.prichatscratches.adapter.ChatListAdapter;
import com.iamscratches.prichatscratches.model.CallList;
import com.iamscratches.prichatscratches.model.ChatList;

import java.util.ArrayList;
import java.util.List;


public class CallsFragment extends Fragment {

    public CallsFragment() {
        // Required empty public constructor
    }

    private List<CallList> list;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_calls, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getCallList();
        return view;
    }

    private void getCallList() {
        list = new ArrayList<>();
//        list.add(new CallList(
//                "11",
//                "LyMin",
//                "15/04/2020",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRvvG5HfspXoo-ZLVd_dVgq6hdko9LnA-rQ&usqp=CAU",
//                "missed"));
//        list.add(new CallList(
//                "11",
//                "LyMin",
//                "15/04/2020",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRvvG5HfspXoo-ZLVd_dVgq6hdko9LnA-rQ&usqp=CAU",
//                "outgoing"));
//        list.add(new CallList(
//                "11",
//                "LyMin",
//                "15/04/2020",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRvvG5HfspXoo-ZLVd_dVgq6hdko9LnA-rQ&usqp=CAU",
//                "incoming"));
//       list.add(new CallList(
//               "15",
//               "LyinM",
//               "15/04/2020",
//               "https://media.istockphoto.com/photos/matryoshka-doll-picture-id172803096?b=1&k=20&m=172803096&s=170667a&w=0&h=UjhcTzah8Vc_8Waz9-3hqnF3FkK3mMzAe3asbdJK0Cc=",
//               "missed"));

//        recyclerView.setAdapter(new CallListAdapter(list, getContext()));
    }
}