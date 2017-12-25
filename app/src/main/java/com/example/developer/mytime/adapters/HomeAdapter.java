package com.example.developer.mytime.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.developer.mytime.R;
import com.example.developer.mytime.activities.DetailTaskActivity;
import com.example.developer.mytime.models.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by developer on 13/12/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<Task> tasks;
    private Context context;


    /**
     * Two arguments are received,
     * the list of data and the context of where you are
     *
     * @param tasks
     * @param context
     */
    public HomeAdapter(ArrayList<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }


    /**
     * To create and infleate new views
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_task, parent, false);
        return new ViewHolder(view);
    }


    /**
     * To set the content of a view (in this case a CardView)
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(tasks.get(position).getTitle());

        Log.i("Title", "" + tasks.get(position).getTitle());


        //TODO: Repair this
        //holder.description.setText(tasks.get(position).getDescription());

        //If the card is clicked the DetailTaskActivity is opened
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDetailTaskActivity();
//            }
//        });
    }


    /**
     * Return the size of the task list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return tasks.size();
    }


    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

    /**
     * To open DetailTaskActivity
     */
    private void openDetailTaskActivity(){
        Intent intent = new Intent(context, DetailTaskActivity.class);
        context.startActivity(intent);
    }
}
