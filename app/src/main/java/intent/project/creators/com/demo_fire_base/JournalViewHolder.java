package intent.project.creators.com.demo_fire_base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ngohaihue on 10/14/17.
 */

public class JournalViewHolder  extends RecyclerView.ViewHolder{
    ImageView journalIcon;
    TextView title, journalDate;
    ImageButton delete;
    public JournalViewHolder(View itemView) {
        super(itemView);
        journalIcon = (ImageView)itemView.findViewById(R.id.img_icon);
        title = (TextView)itemView.findViewById(R.id.txt_title);
        journalDate = (TextView)itemView.findViewById(R.id.txt_date);
        delete = (ImageButton)itemView.findViewById(R.id.delete);

    }
}
