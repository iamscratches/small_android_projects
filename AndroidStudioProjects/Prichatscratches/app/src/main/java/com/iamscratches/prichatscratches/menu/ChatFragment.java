package com.iamscratches.prichatscratches.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamscratches.prichatscratches.R;
import com.iamscratches.prichatscratches.adapter.ChatListAdapter;
import com.iamscratches.prichatscratches.model.ChatList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private List<ChatList> list;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getChatList();
        return view;
    }

    private void getChatList(){
        list = new ArrayList<>();
        list.add(new ChatList(
                "11",
                "LyMin",
                "hello frie",
                "15/04/2020",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRvvG5HfspXoo-ZLVd_dVgq6hdko9LnA-rQ&usqp=CAU"));
        list.add(new ChatList(
                "12",
                "MinLY",
                "hello frie",
                "15/04/2020",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRvvG5HfspXoo-ZLVd_dVgq6hdko9LnA-rQ&usqp=CAU"));
        list.add(new ChatList(
                "13",
                "MLiny",
                "hello frie",
                "15/04/2020",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRvvG5HfspXoo-ZLVd_dVgq6hdko9LnA-rQ&usqp=CAU"));
        list.add(new ChatList(
                "14",
                "MinyL",
                "hello frie",
                "15/04/2020",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRvvG5HfspXoo-ZLVd_dVgq6hdko9LnA-rQ&usqp=CAU"));
        list.add(new ChatList(
                "15",
                "LyinM",
                "hello frie",
                "15/04/2020",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwRvvG5HfspXoo-ZLVd_dVgq6hdko9LnA-rQ&usqp=CAU"));
        list.add(new ChatList(
                "15",
                "LyinM",
                "hello frie",
                "15/04/2020",
                "https://media.istockphoto.com/photos/matryoshka-doll-picture-id172803096?b=1&k=20&m=172803096&s=170667a&w=0&h=UjhcTzah8Vc_8Waz9-3hqnF3FkK3mMzAe3asbdJK0Cc="));

        recyclerView.setAdapter(new ChatListAdapter(list, getContext()));
    }
}