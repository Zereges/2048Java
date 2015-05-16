package defs;

/**
 * Represents error on data in the program, which cannot be recovered from.
 */
public class UnrecoverableException extends Exception
{
    /** Auto-generated for {@code Serializable} interface. */
    private static final long serialVersionUID = 1L;

    /** Type of the error. */
    private UnrecoverableType mType;
    
    /**
     * @param type {@link UnrecoverableType Type} of the exception. 
     */
    public UnrecoverableException(UnrecoverableType type) { mType = type; }
    
    /** 
     * Shows error message for given type.
     * @return Verbose string of error type.
     */
    @Override
    public String toString()
    {
        switch (mType)
        {
            case CANNOT_CREATE_SAVES_DIR:
                return "Saves dir could not be created, probably because there exists Stats file.";
                        
            default:
                return "Undefined error message";
        }
    }
}
