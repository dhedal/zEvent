package com.ecf.zevent.dto;

public class CheckIsEmailAndPseudoUniqueResponse {

    private boolean emailUnique;
    private boolean pseudoUnique;

    public CheckIsEmailAndPseudoUniqueResponse() {}

    public CheckIsEmailAndPseudoUniqueResponse(boolean emailUnique, boolean pseudoUnique) {
        this.emailUnique = emailUnique;
        this.pseudoUnique = pseudoUnique;
    }

    public boolean isEmailUnique() {
        return emailUnique;
    }

    public boolean isPseudoUnique() {
        return pseudoUnique;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CheckIsEmailAndPseudoUniqueResponse{");
        sb.append("emailUnique=").append(emailUnique);
        sb.append(", pseudoUnique=").append(pseudoUnique);
        sb.append('}');
        return sb.toString();
    }
}
