package com.example.alex.npcdirectory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.alex.npcdirectory.data.NPC;
import com.example.alex.npcdirectory.data.NPCRepository;

import java.util.List;

public class NPCViewModel extends AndroidViewModel {

    private NPCRepository mRepository;
    private LiveData<List<NPC>> mAllNPCs;

    public NPCViewModel(Application application)
    {
        super(application);
        mRepository = new NPCRepository(application);
        mAllNPCs = mRepository.getAllNPCs();
    }

    LiveData<List<NPC>> getAllNPCs() {return mAllNPCs;}

    public void insert(NPC npc) {mRepository.insert(npc);}

    public void update(NPC npc) {mRepository.update(npc);}
}
