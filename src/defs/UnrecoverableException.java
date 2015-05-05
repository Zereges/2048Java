package defs;

public class UnrecoverableException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * @param type {@link defs.UnrecovableType Type} of the exception. 
     */
    public UnrecoverableException(UnrecovableType type)
    {
        m_type = type;
    }
    UnrecovableType m_type;
}
