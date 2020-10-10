package madison.trial;

public class DialogResult {
    
    private final boolean ok;

    DialogResult(boolean ok) {
        this.ok = ok;
    }
    
    public boolean isOk() {
        return ok;
    }
    
    public boolean isCancel() {
        return !isOk();
    }
}
