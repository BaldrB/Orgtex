package by.papko.orgtex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder>  {

    interface OnRepairPartsClickListener{
        void onRepairPartsClick(RepairParts repairParts, int position);
    }

    private final RepairAdapter.OnRepairPartsClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<RepairParts> RepairPartse;

    RepairAdapter(Context context, List<RepairParts> repairParts, RepairAdapter.OnRepairPartsClickListener onRepairPartsClick) {
        this.RepairPartse = repairParts;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onRepairPartsClick;
    }

    @Override
    public RepairAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_repair, parent, false);
        return new RepairAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepairAdapter.ViewHolder holder, int position) {
        RepairParts repairParts = RepairPartse.get(position);
        holder.inent.setText(repairParts.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onRepairPartsClick(repairParts, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return RepairPartse.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView inent;
        ViewHolder(View view){
            super(view);
            inent = view.findViewById(R.id.textRepitItemId);
        }
    }
}
