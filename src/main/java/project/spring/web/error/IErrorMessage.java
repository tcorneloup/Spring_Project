package project.spring.web.error;

public interface IErrorMessage extends Comparable<IErrorMessage>{

    String getCode();
    String getLabel();
}
