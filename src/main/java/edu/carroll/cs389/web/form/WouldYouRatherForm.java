package edu.carroll.cs389.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WouldYouRatherForm {

    @NotNull
    @Size(min = 2, message = "Please place a scenario here, at least 2 characters long")

    private String optionA;
    @NotNull
    @Size(min = 2, message = "Please place a scenario here, at least 2 characters long")
    private String optionB;

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String newOptionA) {
        this.optionA = newOptionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String newOptionB) {
        this.optionB = newOptionB;
    }

}