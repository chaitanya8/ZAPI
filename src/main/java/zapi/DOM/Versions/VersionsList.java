package zapi.DOM.Versions;

import java.util.List;

/**
 * Created by Chaitanya on 4-Jul-15.
 */
public class VersionsList {
    List<VersionsDOM> unreleasedVersions;
    List<VersionsDOM> releasedVersions;

    public List<VersionsDOM> getUnreleasedVersions() {
        return unreleasedVersions;
    }

    public List<VersionsDOM> getReleasedVersions() {
        return releasedVersions;
    }

    public VersionsDOM findUnreleasedProjectVersion(String version){
        VersionsDOM returningDOM = null;
        for(VersionsDOM x : this.unreleasedVersions){
            if(x.getLabel().equalsIgnoreCase(version)){
                returningDOM = x;
            }
        }
        return returningDOM;
    }

    public long findReleasedProjectVersion(long version){
        for(VersionsDOM x : this.releasedVersions){
            if(x.getValue()==version){
                return x.getValue();
            }
            else{
                return -1;
            }
        }
        return -2;
    }
}
