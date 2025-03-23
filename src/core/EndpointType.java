package core;

import visual.VisualPanelMode;

public enum EndpointType {
    None,
    Association,
    Generalization,
    Composition;

    public static EndpointType ParseFromVisualPanelMode(VisualPanelMode mode){
        if     (mode == VisualPanelMode.Association   ) return EndpointType.Association   ;
        else if(mode == VisualPanelMode.Generalization) return EndpointType.Generalization;
        else if(mode == VisualPanelMode.Composition   ) return EndpointType.Composition   ;
        return EndpointType.None;
    }
}
