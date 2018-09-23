package com.example.alex.npcdirectory;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.npcdirectory.data.NPC;

import java.util.List;

public class NPCListAdapter extends RecyclerView.Adapter<NPCListAdapter.NPCViewHolder> {

    class NPCViewHolder extends RecyclerView.ViewHolder {
        private final TextView npcItemView;

        private NPCViewHolder(View itemView) {
            super(itemView);
            npcItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<NPC> mNPCs; // Cached copy of words

    NPCListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public NPCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new NPCViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NPCViewHolder holder, int position) {
        if (mNPCs != null) {
            NPC current = mNPCs.get(position);
            holder.npcItemView.setText(current.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.npcItemView.setText("No NPC");
        }
    }

    void setNPCs(List<NPC> npcs){
        mNPCs = npcs;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mNPCs has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNPCs != null)
            return mNPCs.size();
        else return 0;
    }
}