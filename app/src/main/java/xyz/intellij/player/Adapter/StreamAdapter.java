package xyz.intellij.player.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import xyz.intellij.player.R;
import xyz.intellij.player.util.entity.StreamEntity;

public class StreamAdapter extends ArrayAdapter<StreamEntity> {
    private  int resourceId;
    public StreamAdapter(@NonNull Context context, int resource, List<StreamEntity> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int postion, View covView, ViewGroup parent){
        StreamEntity streamEntity = getItem(postion);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ((TextView)view.findViewById(R.id.item_name)).setText(""+streamEntity.getStreamName());
        ((TextView)view.findViewById(R.id.item_qrate)).setText("\t"+streamEntity.getStreamQrate());
        ((TextView)view.findViewById(R.id.item_crate)).setText("\t"+streamEntity.getStreamCrate());
        Log.w("StreamAdapter:",""+streamEntity.getStreamId());
        return view;
    }
}