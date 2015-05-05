package defs;

public class UnrecoverableException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * @param type {@link defs.UnrecovableType Type} of the exception. 
     */
    public UnrecoverableException(UnrecoverableType type)
    {
        mType = type;
    }
    
    /** 
     * Shows error message for given type.
     * @return Verbose string of error type.
     */
    @Override
    public String toString()
    {
        switch (mType)
        {
            case CANNOT_CREATE_STATS_DIR:
                return "Stats dir could not be created, probably because there exists Stats file";
                        
            default:
                return "Undefined error message";
        }
    }
    private UnrecoverableType mType;
}
