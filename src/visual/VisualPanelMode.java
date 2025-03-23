package visual;

public enum VisualPanelMode {
    None(0),
    IsLink          ( 1 << 0),
    IsAddElement    ( 1 << 1),

    Select          ( 1 << 3),
    Association     ((1 << 3) | IsLink.value),
    Generalization  ((1 << 4) | IsLink.value),
    Composition     ((1 << 5) | IsLink.value),
    Rect            ((1 << 4) | IsAddElement.value),
    Oval            ((1 << 4) | IsAddElement.value);

    public final int value;

    VisualPanelMode(int value) {
        this.value = value;
    }

    public boolean IsLink(){
        return (this.value & VisualPanelMode.IsLink.value) == VisualPanelMode.IsLink.value;
    }
    public boolean IsAddElement(){
        return (this.value & VisualPanelMode.IsAddElement.value) == VisualPanelMode.IsAddElement.value;
    }
}
