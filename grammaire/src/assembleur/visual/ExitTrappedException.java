package assembleur.visual;

public class ExitTrappedException extends SecurityException {
    private final int code;
    public ExitTrappedException(int code) {
        super();
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}