package by.papko.orgtex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class SecurityUserAdapter extends ArrayAdapter<SecurityUser> {

    private LayoutInflater inflater;
    private int layout;
    private List<SecurityUser> securityUsers;

    public SecurityUserAdapter(Context context, int resource,  List<SecurityUser> securityUsers) {
        super(context, resource, securityUsers);
        this.securityUsers = securityUsers;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView nameView = (TextView) view.findViewById(R.id.testFullNameUserAdmin);
        TextView emallView = (TextView) view.findViewById(R.id.testEmailUserAdmin);

        SecurityUser securityUserse = securityUsers.get(position);

        nameView.setText(securityUserse.getFullName());
        emallView.setText(securityUserse.geteMail());

        return view;
    }
}
