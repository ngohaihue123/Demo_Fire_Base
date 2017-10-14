package intent.project.creators.com.demo_fire_base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import intent.project.creators.com.demo_fire_base.models.Constants;
import intent.project.creators.com.demo_fire_base.models.JournalEntry;
import intent.project.creators.com.demo_fire_base.models.Tags;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference journalCloudEndPoint,tagCloudEndPoint;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<JournalEntry, JournalViewHolder> mJournalFirebaseAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        recyclerView=(RecyclerView) findViewById(R.id.recycle_view);
        if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
            addInitialDataToFirebase();;
            editor.putBoolean(Constants.FIRST_RUN, false).commit();
        }
        addInitialDataToFirebase();
        getListJounalEntry();
        getListTag();
        showdata();


    }


    private void addInitialDataToFirebase() {
        journalCloudEndPoint= FirebaseDatabase.getInstance().getReference("journalEntrys");
        tagCloudEndPoint=FirebaseDatabase.getInstance().getReference("Tags");
        List<String> tagNames = MainActivity.getSampleTags();
        for (String name: tagNames){
            String tagKey = tagCloudEndPoint.push().getKey();
            Tags tag = new Tags();
            tag.setTagName(name);
            tag.setTagId(tagKey);
            tagCloudEndPoint.child(tag.getTagId()).setValue(tag);
        }
        List<JournalEntry> sampleJournalEntries = MainActivity.getSampleJournalEntries();
        for (JournalEntry journalEntry: sampleJournalEntries){
            String key = journalCloudEndPoint.push().getKey();
            journalEntry.setJournalId(key);
            journalCloudEndPoint.child(key).setValue(journalEntry);
        }



    }
    public static List<JournalEntry> getSampleJournalEntries() {

        List<JournalEntry> journalEnrties = new ArrayList<>();
        //create the dummy journal
        JournalEntry journalEntry1 = new JournalEntry();
        journalEntry1.setTitle("DisneyLand Trip");
        journalEntry1.setContent
                ("We went to Disneyland today and the kids had lots of fun!");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar1= Calendar.getInstance();

        journalEntry1.setDateCreated((calendar1.getTimeInMillis()));
        journalEntry1.setDateModified(calendar1.getTimeInMillis());
        journalEnrties.add(journalEntry1);
        return  journalEnrties;
    }
    public static List<String> getSampleTags(){
        ArrayList<String> tagEnrties=new ArrayList<>();
       String tagName1="Do some thing";
        String tagName2="Do some thing";
        tagEnrties.add(tagName1);
        tagEnrties.add(tagName2);
        return  tagEnrties;
    }
    public void getListJounalEntry(){
        journalCloudEndPoint= FirebaseDatabase.getInstance().getReference("journalEntrys");
        final ArrayList<JournalEntry> mJournalEntries=new ArrayList<JournalEntry>();
        journalCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    JournalEntry note = noteSnapshot.getValue(JournalEntry.class);
                    mJournalEntries.add(note);
                }
                System.out.println(mJournalEntries.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("System in", databaseError.getMessage());
            }
        });


    }
    public  void getListTag(){
        tagCloudEndPoint=FirebaseDatabase.getInstance().getReference("Tags");
        final ArrayList<Tags> mTags=new ArrayList<Tags>();
        tagCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()){
                    Tags tag = categorySnapshot.getValue(Tags.class);
                    mTags.add(tag);
                }
                System.out.println(mTags.size());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public  void showdata(){
        mJournalFirebaseAdapter = new FirebaseRecyclerAdapter<JournalEntry, JournalViewHolder>(
                JournalEntry.class,
                R.layout.journal_custom_row,
                JournalViewHolder.class,
                journalCloudEndPoint) {
            @Override
            protected void populateViewHolder
                    (JournalViewHolder holder, final JournalEntry journalEntry, int position) {
                holder.title.setText(journalEntry.getTitle());
                holder.journalDate.setText(getDueDate(journalEntry.getDateModified()));
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(journalEntry.getJournalId())) {
                            journalCloudEndPoint.child(journalEntry.getJournalId()).
                                    removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (mJournalFirebaseAdapter.getItemCount() < 1) {
                                        // showEmptyText();
                                    }
                                }
                            });
                        }
                    }
                });
                String firstLetter = journalEntry.getTitle().substring(0, 1);
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color = generator.getRandomColor();
                TextDrawable drawable = TextDrawable.builder()
                        .buildRound(firstLetter, color);
                holder.journalIcon.setImageDrawable(drawable);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mJournalFirebaseAdapter);

    }

    public String getDueDate(long miliSecond) {
        Date date = new Date(miliSecond);
        DateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
        return formatter.format(date);
    }


}
