package com.example.yummyzone.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yummyzone.R;
import com.example.yummyzone.adapter.Menu_Adapter;
import com.example.yummyzone.classes.Menu_tab;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class MenuFragment extends Fragment {
    RecyclerView RecyclerViewFoodList;
    ImageView image_back;
    Menu_Adapter menu_adapter;
    DatabaseReference databaseReference;
    TextView restaurant_name;
    public MenuFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        RecyclerViewFoodList = view.findViewById(R.id.RecyclerViewFoodList);
        image_back=view.findViewById(R.id.image_back);
        restaurant_name=view.findViewById(R.id.restaurant_name);
        Bundle args = getArguments();
        String restaurant_id=args.getString("restaurant_id");
        restaurant_name.setText(restaurant_id);

        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        RecyclerViewFoodList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        FirebaseRecyclerOptions<Menu_tab> MenuFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Menu_tab>().setQuery(databaseReference.orderByChild("restaurantid").equalTo(restaurant_id), Menu_tab.class).build();
        menu_adapter = new Menu_Adapter(MenuFirebaseRecyclerOptions);
        RecyclerViewFoodList.setAdapter(menu_adapter);
        menu_adapter.startListening();

            image_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment=new homeFragment(restaurant_id);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
                }});

        return view;}}


