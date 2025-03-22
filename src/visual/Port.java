package visual;

import core.Vector2;

public class Port {
    public Port(VisualElement owner, Vector2 position){
        this.owner    = owner;
        this.position = position;
    }

    public final VisualElement owner;
    public Vector2 position;

    public Vector2 GetWorldPosition(){
        return  owner.GetWorldPosition().Add(position);
    }
}
