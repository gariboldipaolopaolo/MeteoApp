package ch.supsi.dti.isin.meteoapp.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {
    private List<Location> locations = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new LocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        Location currentLocation = locations.get(position);

        holder.name.setText(currentLocation.getName());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setLocations(List<Location> locations){
        this.locations = locations;
        notifyDataSetChanged();
    }

    public List<Location> getLocations(){
        return this.locations;
    }

    public  Location getLocationAt(int position){
        return locations.get(position);
    }

    class LocationHolder extends RecyclerView.ViewHolder{
        private TextView name;

        public LocationHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(locations.get(position));
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Location location);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
