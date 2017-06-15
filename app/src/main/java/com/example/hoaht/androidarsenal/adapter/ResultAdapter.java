package com.example.hoaht.androidarsenal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hoaht.androidarsenal.R;
import com.example.hoaht.androidarsenal.model.Person;

import java.util.List;

/**
 * ResultAdapter.
 *
 * @author HoaHT
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final List<Person> mListPerson;
    private OnItemClickListener mListener;

    public ResultAdapter(List<Person> listPerson, OnItemClickListener listener) {
        this.mListPerson = listPerson;
        this.mListener = listener;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        Person person = mListPerson.get(position);
        holder.mTvName.setText(person.getName());
        holder.mTvAge.setText(person.getAge());
    }

    @Override
    public int getItemCount() {
        return mListPerson.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final TextView mTvAge;

        ResultViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvAge = (TextView) itemView.findViewById(R.id.tvAge);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(getLayoutPosition());
                    }
                    return true;
                }
            });
        }
    }
}
