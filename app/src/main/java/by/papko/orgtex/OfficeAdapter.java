package by.papko.orgtex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OfficeAdapter extends RecyclerView.Adapter<OfficeAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<OfficeEquip> officeEquips;

    OfficeAdapter(Context context, List<OfficeEquip> officeEquip) {
        this.officeEquips = officeEquip;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public OfficeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfficeAdapter.ViewHolder holder, int position) {
        OfficeEquip officeEquip = officeEquips.get(position);
        holder.inent.setText(officeEquip.getInv());
        holder.serial.setText(officeEquip.getSerial());
        holder.name.setText(officeEquip.getNameequio());
    }

    @Override
    public int getItemCount() {
        return officeEquips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView inent, serial, name;
        ViewHolder(View view){
            super(view);
            inent = view.findViewById(R.id.textInvent);
            serial = view.findViewById(R.id.textSerial);
            name = view.findViewById(R.id.textName);
        }
    }

}
