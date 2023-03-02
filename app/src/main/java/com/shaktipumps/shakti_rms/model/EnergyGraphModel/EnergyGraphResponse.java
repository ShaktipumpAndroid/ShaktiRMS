
package com.shaktipumps.shakti_rms.model.EnergyGraphModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EnergyGraphResponse {

    @SerializedName("Energy")
    private List<EnergyGraphEnergy> mEnergy;
    @SerializedName("Flow")
    private List<EnergyGraphFlow> mFlow;

    public List<EnergyGraphEnergy> getEnergy() {
        return mEnergy;
    }

    public void setEnergy(List<EnergyGraphEnergy> energy) {
        mEnergy = energy;
    }

    public List<EnergyGraphFlow> getFlow() {
        return mFlow;
    }

    public void setFlow(List<EnergyGraphFlow> flow) {
        mFlow = flow;
    }

}
