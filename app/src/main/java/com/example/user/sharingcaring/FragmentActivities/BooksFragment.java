package com.example.user.sharingcaring.FragmentActivities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.sharingcaring.Adapters.BookPostAdapter;
import com.example.user.sharingcaring.PostActivities.BookPostActivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {

    TextView textView, textView1, textView2, textView3, textView4;
    RecyclerView postList;
    FloatingActionButton addPostButton;
    DatabaseReference postRef;
    View view;

    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_books, container, false);

        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        postList = view.findViewById(R.id.bookPost_recyclerView);
        postList.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        // The new post will display at first, the old one will go on 2nd
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);

        addPostButton = view.findViewById(R.id.float_button);

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotoBookPostActivity();
            }
        });

        //displayAlluserPosts();

        return view;
    }

    /*private void displayAlluserPosts() {

            FirebaseRecyclerAdapter<BookPostAdapter,PostViewHolder> firebaseRecyclerAdapter=new
                    FirebaseRecyclerAdapter<BookPostAdapter, PostViewHolder>(BookPostAdapter.class,R.layout.book_post_layout,PostViewHolder.class,postRef) {
                @Override
                protected void populateViewHolder(PostViewHolder viewHolder, BookPostAdapter model, int position) {

                }
            };

            postList.setAdapter(firebaseRecyclerAdapter);

    }
*/

   /* public static class PostViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }



    }
*/
    private void gotoBookPostActivity () {
            Intent bookPostIntent = new Intent(getContext(), BookPostActivity.class);
            startActivity(bookPostIntent);
        }


        private void gotoUserProfile () {
            Intent userIntent = new Intent(getContext(), UserProfile.class);
            startActivity(userIntent);

        }

    }

