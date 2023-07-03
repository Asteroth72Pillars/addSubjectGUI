
package za.ac.cput.domain;

/**
 *
 * @author zaihd
 */
public class Subject 
{
    private String subjectCode;
    private String subjectDescription;

    public Subject() 
    {
        
    }

    public Subject(String subjectCode, String subjectDescription) 
    {
        this.subjectCode = subjectCode;
        this.subjectDescription = subjectDescription;
    }

    public String getSubjectCode() 
    {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) 
    {
        this.subjectCode = subjectCode;
    }

    public String getSubjectDescription() 
    {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) 
    {
        this.subjectDescription = subjectDescription;
    }
    
    
    
}
