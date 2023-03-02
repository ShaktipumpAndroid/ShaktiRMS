package com.shaktipumps.shakti_rms.aryabata.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.classes.BluetoothDetails;

import java.util.ArrayList;
import java.util.List;



public class BluetoothDetailsAdapter extends ArrayAdapter<BluetoothDetails> {

    private List<BluetoothDetails> bluetoothDetails;
    private Context C_Context;

    public BluetoothDetailsAdapter(Context context, ArrayList<BluetoothDetails> p_bluetoothDetails) {
        super(context, R.layout.bluetooth_item_view, p_bluetoothDetails);
        this.bluetoothDetails = p_bluetoothDetails;
        C_Context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) C_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.bluetooth_item_view, parent, false);
        }
        BluetoothDetails details = bluetoothDetails.get(position);

        TextView btname = (TextView) itemView.findViewById(R.id.btname);
        btname.setText(details.getBluetoothName());

        TextView btmac = (TextView) itemView.findViewById(R.id.btmac);
        btmac.setText(details.getBluetoothAddress());

        return itemView;
    }
}